package com.lombardinternational.casemanagementconnector.domain.businesstransaction.builder.addition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.lombardinternational.casemanagementconnector.domain.businesstransaction.api.WebFormService;
import com.lombardinternational.casemanagementconnector.domain.businesstransaction.entity.additiontransaction.AdditionTransaction;
import com.lombardinternational.casemanagementconnector.domain.businesstransaction.entity.additiontransaction.register.AdditionRegisterExistingPc;
import com.lombardinternational.casemanagementconnector.domain.businesstransaction.exception.TransactionBuilderException;
import com.lombardinternational.casemanagementconnector.domain.common.entity.webform.WebForm;
import com.lombardinternational.casemanagementconnector.domain.common.entity.webform.WebFormGroup;

import lombok.AllArgsConstructor;

import static com.lombardinternational.casemanagementconnector.domain.businesstransaction.constants.TransactionsConstants.*;

@Component
@AllArgsConstructor
public class AdditionExistingPcBuilder {

    private final WebFormService webFormService;

    public List<AdditionRegisterExistingPc> fromWebForm(final WebForm webForm) throws TransactionBuilderException {

        if (webForm == null || CollectionUtils.isEmpty(webForm.getGroups())) {
            throw new TransactionBuilderException(AdditionTransaction.class, "WebForm is empty");
        }
        List<AdditionRegisterExistingPc> additionRegisterExistingPcs = new ArrayList<>();

        final WebFormGroup safs = webFormService.findWebFormGroup(webForm, GROUP_PARENT_INVESTMENTS + GROUP_SAF);
        if (safs != null && safs.getGroups() != null) {
            additionRegisterExistingPcs.addAll(fromFundWebForm(safs.getGroups()));
        }
        final WebFormGroup idfs = webFormService.findWebFormGroup(webForm, GROUP_PARENT_INVESTMENTS + GROUP_IDF);
        if (idfs != null && idfs.getGroups() != null) {
            additionRegisterExistingPcs.addAll(fromFundWebForm(idfs.getGroups()));
        }
        final WebFormGroup fdfs = webFormService.findWebFormGroup(webForm, GROUP_PARENT_INVESTMENTS + GROUP_FDF);
        if (fdfs != null && fdfs.getGroups() != null) {
            additionRegisterExistingPcs.addAll(fromFundWebForm(fdfs.getGroups()));
        }

        final WebFormGroup icf = webFormService.findWebFormGroup(webForm, GROUP_PARENT_INVESTMENTS + GROUP_ICF);

        if ( icf != null) {
            final BigDecimal icfAmount = webFormService.findBigDecimalFieldValue(icf,GROUP_BASICS + FIELD_AMOUNT);
            final String currency = webFormService.findTextFieldValue(icf,GROUP_BASICS + FIELD_CURRENCY);
            if (icfAmount !=null && icfAmount.compareTo(BigDecimal.ZERO) > 0){
                additionRegisterExistingPcs.addAll(fromIcfWebForm(icf,icfAmount,currency));
            }

            }

      


        final WebFormGroup ecf = webFormService.findWebFormGroup(webForm, GROUP_PARENT_INVESTMENTS + GROUP_ECF);
        final BigDecimal ecfAmount = webFormService.findBigDecimalFieldValue(ecf, GROUP_BASICS + FIELD_AMOUNT);
        final String pcNumber = webFormService.findTextFieldValue(ecf, FIELD_PC_NUMBER);
        if (StringUtils.isNotBlank(pcNumber) && ecfAmount != null && ecfAmount.compareTo(BigDecimal.ZERO) > 0) {
            final WebFormGroup ecfs = webFormService.findWebFormGroup(ecf, GROUP_EXTERNAL_FUNDS);
            if (ecfs != null) {
                additionRegisterExistingPcs.add(fromEcfWebForm(ecfs.getGroups(), pcNumber));
            }
        }
        return additionRegisterExistingPcs;
    }

    private List<AdditionRegisterExistingPc> fromFundWebForm(final List<WebFormGroup> webFormGroups) {

        List<AdditionRegisterExistingPc> funds = new ArrayList<>();
        if (!webFormGroups.isEmpty()) {
            for (WebFormGroup webFormGroup : webFormGroups) {
                final Boolean isNew = webFormService.findBooleanFieldValue(webFormGroup, FIELD_IS_NEW);
                final WebFormGroup basicsGroup = webFormService.findWebFormGroup(webFormGroup, GROUP_BASICS);
                final BigDecimal expectedPremiumQty = webFormService.findBigDecimalFieldValue(basicsGroup, FIELD_AMOUNT);
                if (isNew != null && !isNew && expectedPremiumQty.compareTo(BigDecimal.ZERO) > 0) {
                    AdditionRegisterExistingPc fund = new AdditionRegisterExistingPc();
                    funds.add(fund);

                    final String pcNumber = webFormService.findTextFieldValue(webFormGroup, FIELD_PC_NUMBER);
                    // final String fundId = webFormService.findTextFieldValue(webFormGroup, FUND_ID_FIELD);

                    fund.setKeyId(pcNumber);
                    fund.setProductComponentNumber(pcNumber);
                    fund.setPaymentMode(PAYMENT_MODE);

                    fund.setExpectedPremiumQuantity(expectedPremiumQty);

                    final String expectedPremiumCcy = webFormService.findTextFieldValue(basicsGroup, FIELD_CURRENCY);
                    fund.setExpectedPremiumCurrency(expectedPremiumCcy);
                }
            }
        }
        return funds;
    }

    private AdditionRegisterExistingPc fromEcfWebForm(final List<WebFormGroup> webFormGroups, final String pcNumber) {

        AdditionRegisterExistingPc fund = null;
        if (!webFormGroups.isEmpty()) {
            for (WebFormGroup webFormGroup : webFormGroups) {
                final BigDecimal percent = webFormService.findBigDecimalFieldValue(webFormGroup, FIELD_PERCENT);
                if (StringUtils.isNotBlank(pcNumber) && percent.compareTo(BigDecimal.ZERO) > 0) {
                    final BigDecimal estimatedFundEur = webFormService.findBigDecimalFieldValue(webFormGroup, FIELD_ESTIMATED_FUND_EUR);

                    if (fund == null) {
                        fund = new AdditionRegisterExistingPc();
                        fund.setKeyId(pcNumber);
                        fund.setProductComponentNumber(pcNumber);
                        fund.setPaymentMode(PAYMENT_MODE);
                        fund.setExpectedPremiumCurrency(DEFAULT_CURRENCY);
                        fund.setExpectedPremiumQuantity(estimatedFundEur);
                    } else {
                        fund.setExpectedPremiumQuantity(estimatedFundEur.add(fund.getExpectedPremiumQuantity()));
                    }
                }
            }
        }
        return fund;
    }

    private List<AdditionRegisterExistingPc> fromIcfWebForm(final WebFormGroup icfGroup,BigDecimal amount, String currency) {

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
            final BigDecimal percent = webFormService.findBigDecimalFieldValue(fundGroup, FIELD_PERCENT);
            if (percent == null || percent.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }

            additionRegisterExistingPcs.add(buildFund(pcNumber, amount, currency));
        });
        return additionRegisterExistingPcs;
    }

    private AdditionRegisterExistingPc buildFund(final String pcNumber, BigDecimal total, final String currency) {

        AdditionRegisterExistingPc fund = new AdditionRegisterExistingPc();
        fund.setKeyId(pcNumber);
        fund.setProductComponentNumber(pcNumber);
        fund.setPaymentMode(PAYMENT_MODE);
        fund.setExpectedPremiumCurrency(currency);
        fund.setExpectedPremiumQuantity(total);
        return fund;
    }

}
