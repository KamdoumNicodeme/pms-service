   private List<AdditionRegisterExistingPc> fromIcfWebForm(final WebFormGroup icfGroup) {

        if (icfGroup == null) {
            return Collections.emptyList();
        }
        final WebFormGroup internalFundsGroup = webFormService.findWebFormGroup(icfGroup, GROUP_INTERNAL_FUNDS);
        if (internalFundsGroup == null || CollectionUtils.isEmpty(internalFundsGroup.getGroups())) {
            return Collections.emptyList();
        }

        final List<AdditionRegisterExistingPc> additionRegisterExistingPcs = new ArrayList<>();
        internalFundsGroup.getGroups().forEach(fundGroup -> {
            final String pcNumber = webFormService.findTextFieldValue(fundGroup, FIELD_PC_NUMBER);
            if (StringUtils.isBlank(pcNumber)) {
                return;
            }
            // percent est porté par le fonds, pas par internalFundPosition
            final BigDecimal percent = webFormService.findBigDecimalFieldValue(fundGroup, FIELD_PERCENT);
            if (percent == null || percent.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            // estimatedFundEUR et currency restent dans internalFundPosition
            final WebFormGroup positionGroup = webFormService.findWebFormGroup(fundGroup, GROUP_INTERNAL_FUND_POSITION);
            final BigDecimal estimatedFundEur = webFormService.findBigDecimalFieldValue(positionGroup, FIELD_ESTIMATED_FUND_EUR);
            if (estimatedFundEur == null || estimatedFundEur.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            final String currency = webFormService.findTextFieldValue(positionGroup, FIELD_CURRENCY);
            additionRegisterExistingPcs.add(buildFund(pcNumber, estimatedFundEur, currency));
        });
        return additionRegisterExistingPcs;
    }
