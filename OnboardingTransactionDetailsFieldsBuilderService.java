private List<AdditionProductComponentCharges> handleICFGroup(final WebForm webForm, final List<String> pcKeyIds) {

        final WebFormGroup icfGroup = webFormService.findWebFormGroup(webForm, GROUP_PARENT_INVESTMENTS + GROUP_ICF);
        if (icfGroup == null) {
            return List.of();
        }
        final WebFormGroup internalFundsGroup = webFormService.findWebFormGroup(icfGroup, GROUP_INTERNAL_FUNDS);
        if (internalFundsGroup == null || CollectionUtils.isEmpty(internalFundsGroup.getGroups())) {
            return List.of();
        }

        // La structure ICF est aplatie : chaque fonds porte son PC. On regroupe donc par PC
        // pour reconstituer un AdditionProductComponentCharges par PC (avec ses fundSplits).
        // LinkedHashMap pour conserver l'ordre d'apparition des PC.
        final Map<String, AdditionProductComponentCharges> chargesByPc = new LinkedHashMap<>();
        for (WebFormGroup fundGroup : internalFundsGroup.getGroups()) {
            final String pcNumber = webFormService.findTextFieldValue(fundGroup, FIELD_PC_NUMBER);
            if (StringUtils.isBlank(pcNumber) || !pcKeyIds.contains(pcNumber)) {
                continue;
            }
            final AdditionProductComponentCharges pcCharges = chargesByPc.computeIfAbsent(pcNumber, pc -> {
                final AdditionProductComponentCharges charges = new AdditionProductComponentCharges();
                charges.setKeyId(pc);
                charges.setFundSplits(new ArrayList<>());
                return charges;
            });
            pcCharges.getFundSplits().add(buildFundSplit(fundGroup));
        }
        return List.copyOf(chargesByPc.values());
    }

    private AdditionFundSplit buildFundSplit(final WebFormGroup fundGroup) {

        final WebFormGroup positionGroup = webFormService.findWebFormGroup(fundGroup, GROUP_INTERNAL_FUND_POSITION);

        final AdditionFundSplit fundSplit = new AdditionFundSplit();
        fundSplit.setFundIsin(webFormService.findTextFieldValue(positionGroup, ISIN_FIELD_PATH));
        fundSplit.setFundNumber(webFormService.findTextFieldValue(positionGroup, FUND_NUMBER));
        // percent est desormais porte par le fonds, pas par la position
        fundSplit.setSplitRate(webFormService.findBigDecimalFieldValue(fundGroup, PERCENT_FIELD));
        return fundSplit;
    }
