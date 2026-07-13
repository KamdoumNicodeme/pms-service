    private List<AdditionRegisterExistingPc> fromIcfWebForm(final WebFormGroup icfGroup){
        if (icfGroup == null){
            return Collections.emptyList();
        }
        final WebFormGroup internalFundsGroup = webFormService.findWebFormGroup(icfGroup,GROUP_INTERNAL_FUNDS);
        if (internalFundsGroup == null || CollectionUtils.isEmpty(internalFundsGroup.getGroups())){
            return Collections.emptyList();
        }
        final List<AdditionRegisterExistingPc> additionRegisterExistingPcs = new ArrayList<>();
        internalFundsGroup.getGroups().forEach(fundGroup -> {
            final String pcNumber = webFormService.findTextFieldValue(fundGroup, FIELD_PC_NUMBER);
            if (StringUtils.isBlank(pcNumber)) {
                return;
            }
            final WebFormGroup positionGroup = webFormService.findWebFormGroup(fundGroup, GROUP_INTERNAL_FUND_POSITION);
            final BigDecimal percent = webFormService.findBigDecimalFieldValue(positionGroup, FIELD_PERCENT);
            if (percent == null || percent.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            final BigDecimal estimatedFundEur = webFormService.findBigDecimalFieldValue(positionGroup, FIELD_ESTIMATED_FUND_EUR);
            if (estimatedFundEur == null || estimatedFundEur.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            final String currency = webFormService.findTextFieldValue(positionGroup, FIELD_CURRENCY);
            additionRegisterExistingPcs.add(buildFund(pcNumber, estimatedFundEur, currency));
        });
        return additionRegisterExistingPcs;
    }
    private AdditionRegisterExistingPc buildFund(final String pcNumber, BigDecimal total, final String currency){
        AdditionRegisterExistingPc fund = new AdditionRegisterExistingPc();
        fund.setKeyId(pcNumber);
        fund.setProductComponentNumber(pcNumber);
        fund.setPaymentMode(PAYMENT_MODE);
        fund.setExpectedPremiumCurrency(StringUtils.isNotBlank(currency) ? currency: DEFAULT_CURRENCY);
        fund.setExpectedPremiumQuantity(total);
        return fund;
    }
