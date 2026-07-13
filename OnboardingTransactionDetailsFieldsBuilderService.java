 private List<AdditionProductComponentCharges> handleICFGroup(final WebForm webForm, final List<String> pcKeyIds) {

        final List<AdditionProductComponentCharges> productComponentCharges = new ArrayList<>();
        final WebFormGroup investmentGroup = webFormService.findWebFormGroup(webForm, GROUP_PARENT_INVESTMENTS);
        if (investmentGroup == null || CollectionUtils.isEmpty(investmentGroup.getGroups())) {
            return productComponentCharges;
        }
        investmentGroup.getGroups().stream().filter(group -> GROUP_ICF.equals(group.getGroupId()))
                .filter(group -> CollectionUtils.isNotEmpty(group.getGroups())).flatMap(group -> group.getGroups().stream())
                .forEach(icfGroup -> addIcfCharges(icfGroup, pcKeyIds, productComponentCharges));
        return List.copyOf(productComponentCharges);
    }

    private void addIcfCharges(final WebFormGroup icfGroup, final List<String> pcKeyIds,
            final List<AdditionProductComponentCharges> productComponentCharges) {

        final String pcNumber = webFormService.findTextFieldValue(icfGroup, FIELD_PC_NUMBER);
        if (StringUtils.isBlank(pcNumber) || !pcKeyIds.contains(pcNumber)) {
            return;
        }
        final WebFormGroup internalFundsGroup = webFormService.findWebFormGroup(icfGroup, GROUP_INTERNAL_FUNDS);
        if (internalFundsGroup == null || CollectionUtils.isEmpty(internalFundsGroup.getGroups())) {
            return;
        }

        final AdditionProductComponentCharges pcCharges = new AdditionProductComponentCharges();
        pcCharges.setKeyId(pcNumber);
        pcCharges.setFundSplits(new ArrayList<>());
        internalFundsGroup.getGroups().forEach(group -> {
            final AdditionFundSplit fundSplit = new AdditionFundSplit();
            fundSplit.setFundIsin(webFormService.findTextFieldValue(group, ISIN_FIELD_PATH));
            fundSplit.setFundNumber(webFormService.findTextFieldValue(group, FUND_NUMBER));
            fundSplit.setSplitRate(webFormService.findBigDecimalFieldValue(group, PERCENT_FIELD));
            pcCharges.getFundSplits().add(fundSplit);
        });
        productComponentCharges.add(pcCharges);
    }
