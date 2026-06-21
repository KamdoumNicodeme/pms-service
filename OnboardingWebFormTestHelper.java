package com.lombardinternational.casemanagement.service.decision.domain.service.checklist.onboarding;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import com.lombardinternational.casemanagement.service.decision.domain.model.webform.BigDecimalWebFormField;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.BooleanWebFormField;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.CalendarWebFormField;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.TextWebFormField;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.WebForm;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.WebFormGroup;

public final class OnboardingWebFormTestHelper {

    private OnboardingWebFormTestHelper() {
    }

    public static WebForm createWebForm() {
        return WebForm.builder()
                .webFormId("ONBOARDING")
                .groups(Arrays.asList(
                            WebFormGroup.builder()
                            .groupId("CONTEXT")
                            .textFields(Arrays.asList(
                                        TextWebFormField.builder().fieldId("TRANSACTION_TYPE").value("NEW_BUSINESS").build(),
                                        TextWebFormField.builder().fieldId("CASE_ID").value("NBD_68219_20260619").build(),
                                        TextWebFormField.builder().fieldId("POLICY_NUMBER").value("2606-162701").build()
                                ))
                            .build(),
                            WebFormGroup.builder()
                            .groupId("WIZARD_DATA")
                            .textFields(Arrays.asList(
                                        TextWebFormField.builder().fieldId("TARGET_MARKET").value("FR").build(),
                                        TextWebFormField.builder().fieldId("BRANCH").value("FOS").build(),
                                        TextWebFormField.builder().fieldId("PRE_CONTRACTUAL_PACKAGE").value("GENERIC").build(),
                                        TextWebFormField.builder().fieldId("POLICY_NUMBER").value("2606-162701").build()
                                ))
                            .build(),
                            WebFormGroup.builder()
                            .groupId("FACTFIND")
                            .groups(Arrays.asList(
                                        WebFormGroup.builder()
                                        .groupId("KYC")
                                        .groups(Arrays.asList(
                                                    WebFormGroup.builder()
                                                    .groupId("DISTRIBUTORS")
                                                    .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("INTERMEDIATE_IS_APPLICATION_OWNER").value(true).build()))
                                                    .groups(Arrays.asList(
                                                                WebFormGroup.builder()
                                                                .groupId("INTERMEDIATE")
                                                                .textFields(Arrays.asList(
                                                                            TextWebFormField.builder().fieldId("COMPANY_NAME").value("(Internal) - Amaury de Potter d'Indoye").build(),
                                                                            TextWebFormField.builder().fieldId("LASTNAME").value("d").build(),
                                                                            TextWebFormField.builder().fieldId("FIRSTNAME").value("d").build(),
                                                                            TextWebFormField.builder().fieldId("REGISTRATION_NUMBER").value("d").build(),
                                                                            TextWebFormField.builder().fieldId("MOBILE_PHONE").value("+2222").build(),
                                                                            TextWebFormField.builder().fieldId("EMAIL").value("t@t.com").build(),
                                                                            TextWebFormField.builder().fieldId("AUTHORITY_CONTROL_OF").value("CAA").build(),
                                                                            TextWebFormField.builder().fieldId("AUTHORITY_ADDRESS").value("11, rue Robert Stumper, L-2557 Luxembourg, Grand-Duché de Luxembourg").build(),
                                                                            TextWebFormField.builder().fieldId("BROKER_NUMBER").value("0140009838").build(),
                                                                            TextWebFormField.builder().fieldId("BROKER_IDENTIFIER").value("B/2763").build(),
                                                                            TextWebFormField.builder().fieldId("BROKER_PARTNER_TYPE").value("Agent - Employee (Agent & Direct Sales)").build()
                                                                    ))
                                                                .groups(Collections.singletonList(WebFormGroup.builder()
                                                                            .groupId("ADDRESS")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("STREET_NUMBER").value("326").build(),
                                                                                        TextWebFormField.builder().fieldId("STREET_NAME").value("Rue des Campanules").build(),
                                                                                        TextWebFormField.builder().fieldId("TOWN").value("Tavier").build(),
                                                                                        TextWebFormField.builder().fieldId("POST_CODE").value("4163").build(),
                                                                                        TextWebFormField.builder().fieldId("COUNTRY").value("LU").build()
                                                                                ))
                                                                            .build()))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("SIGNATURE_PLACE")
                                                                .textFields(Arrays.asList(
                                                                            TextWebFormField.builder().fieldId("COUNTRY").value("FR").build(),
                                                                            TextWebFormField.builder().fieldId("CITY").value("weerwe").build()
                                                                    ))
                                                                .build()
                                                        ))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("POLICY_HOLDER")
                                                    .textFields(Arrays.asList(
                                                                TextWebFormField.builder().fieldId("TYPE").value("physical").build(),
                                                                TextWebFormField.builder().fieldId("RIGHTS_TRANSFER").value("NOT_APPLICABLE").build()
                                                        ))
                                                    .groups(Collections.singletonList(WebFormGroup.builder()
                                                                .groupId("PHYSICAL_PERSONS")
                                                                .groups(Collections.singletonList(WebFormGroup.builder()
                                                                            .groupId("0")
                                                                            .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("UUID").value("7be1794d-8e75-4d78-9940-e251f745074d").build()))
                                                                            .groups(Arrays.asList(
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("GENERAL")
                                                                                        .textFields(Arrays.asList(
                                                                                                    TextWebFormField.builder().fieldId("NEW_PROPERTY").value("FULL_PROPERTY").build(),
                                                                                                    TextWebFormField.builder().fieldId("TITLE").value("MAD").build(),
                                                                                                    TextWebFormField.builder().fieldId("LASTNAME").value("fsdfsdf").build(),
                                                                                                    TextWebFormField.builder().fieldId("FIRSTNAME").value("sdfsd").build(),
                                                                                                    TextWebFormField.builder().fieldId("BIRTH_NAME").value("sdfsdfs").build(),
                                                                                                    TextWebFormField.builder().fieldId("BIRTH_COUNTRY").value("FR").build(),
                                                                                                    TextWebFormField.builder().fieldId("BIRTH_TOWN").value("sdfsdfs").build(),
                                                                                                    TextWebFormField.builder().fieldId("MARITAL_STATUS").value("SING").build()
                                                                                            ))
                                                                                        .calendarFields(Collections.singletonList(CalendarWebFormField.builder().fieldId("BIRTH_DATE").value("2003-01-06T23:00:00.000+00:00").build()))
                                                                                        .groups(Arrays.asList(
                                                                                                    WebFormGroup.builder()
                                                                                                    .groupId("PROFESSION_DETAILS")
                                                                                                    .textFields(Arrays.asList(
                                                                                                                TextWebFormField.builder().fieldId("OCCUPATION").value("mngt_dire").build(),
                                                                                                                TextWebFormField.builder().fieldId("EMPLOYER_NAME").value("sdfsdf").build(),
                                                                                                                TextWebFormField.builder().fieldId("BUSINESS_SECTOR").value("casp_vasp").build(),
                                                                                                                TextWebFormField.builder().fieldId("EMPLOYER_COUNTRY").value("FR").build()
                                                                                                        ))
                                                                                                    .build(),
                                                                                                    WebFormGroup.builder()
                                                                                                    .groupId("NATIONALITIES")
                                                                                                    .groups(Collections.singletonList(WebFormGroup.builder()
                                                                                                                .groupId("0")
                                                                                                                .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("NATIONALITY").value("FR").build()))
                                                                                                                .calendarFields(Collections.singletonList(CalendarWebFormField.builder().fieldId("BEGIN_DATE").value("2011-10-09T22:00:00.000+00:00").build()))
                                                                                                                .build()))
                                                                                                    .build()
                                                                                            ))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("CONTACT_DETAILS")
                                                                                        .textFields(Arrays.asList(
                                                                                                    TextWebFormField.builder().fieldId("PHONE_NUMBER").value("+2222").build(),
                                                                                                    TextWebFormField.builder().fieldId("MOBILE_PHONE").value("+2222").build(),
                                                                                                    TextWebFormField.builder().fieldId("EMAIL").value("t@t.com").build()
                                                                                            ))
                                                                                        .groups(Collections.singletonList(WebFormGroup.builder()
                                                                                                    .groupId("ADDRESS")
                                                                                                    .textFields(Arrays.asList(
                                                                                                                TextWebFormField.builder().fieldId("STREET_NUMBER").value("dfsdf").build(),
                                                                                                                TextWebFormField.builder().fieldId("STREET_NAME").value("asdasd").build(),
                                                                                                                TextWebFormField.builder().fieldId("TOWN").value("asdasd").build(),
                                                                                                                TextWebFormField.builder().fieldId("POST_CODE").value("asdasd").build(),
                                                                                                                TextWebFormField.builder().fieldId("COUNTRY").value("FR").build()
                                                                                                        ))
                                                                                                    .build()))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("IDENTITY")
                                                                                        .textFields(Arrays.asList(
                                                                                                    TextWebFormField.builder().fieldId("TYPE").value("IDCARD").build(),
                                                                                                    TextWebFormField.builder().fieldId("DELIVERED_BY").value("EMB").build(),
                                                                                                    TextWebFormField.builder().fieldId("ID_NUMBER").value("asdasdasd").build(),
                                                                                                    TextWebFormField.builder().fieldId("DELIVERY_PLACE").value("adasdasd").build(),
                                                                                                    TextWebFormField.builder().fieldId("DELIVERY_COUNTRY").value("FR").build()
                                                                                            ))
                                                                                        .calendarFields(Arrays.asList(
                                                                                                    CalendarWebFormField.builder().fieldId("DELIVERY_DATE").value("2026-06-15T22:00:00.000+00:00").build(),
                                                                                                    CalendarWebFormField.builder().fieldId("EXPIRATION_DATE").value("2047-10-07T22:00:00.000+00:00").build()
                                                                                            ))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("AEOI")
                                                                                        .groups(Collections.singletonList(WebFormGroup.builder()
                                                                                                    .groupId("0")
                                                                                                    .textFields(Arrays.asList(
                                                                                                                TextWebFormField.builder().fieldId("RESIDENCE_COUNTRY").value("FR").build(),
                                                                                                                TextWebFormField.builder().fieldId("TAX_ID").value("0000000000000").build()
                                                                                                        ))
                                                                                                    .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("HAS_TAX_ID").value(true).build()))
                                                                                                    .build()))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("SIGNATURE_PLACE")
                                                                                        .textFields(Arrays.asList(
                                                                                                    TextWebFormField.builder().fieldId("COUNTRY").value("FR").build(),
                                                                                                    TextWebFormField.builder().fieldId("CITY").value("asdasd").build()
                                                                                            ))
                                                                                        .build()
                                                                                ))
                                                                            .build()))
                                                                .build()))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("EBO")
                                                    .groups(Collections.singletonList(WebFormGroup.builder()
                                                                .groupId("EBOS_PHYSICAL_PERSON")
                                                                .groups(Collections.singletonList(WebFormGroup.builder()
                                                                            .groupId("0")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("UUID").value("a0cbcc73-8ddf-4eeb-b014-bcb27e7e3ae9").build(),
                                                                                        TextWebFormField.builder().fieldId("SUBSCRIBER").value("7be1794d-8e75-4d78-9940-e251f745074d").build(),
                                                                                        TextWebFormField.builder().fieldId("PERSONAL_ANTECEDENTS").value("adasdasd").build()
                                                                                ))
                                                                            .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("IS_SUBSCRIBER").value(true).build()))
                                                                            .build()))
                                                                .build()))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("KNOW_YOUR_CUSTOMER")
                                                    .groups(Arrays.asList(
                                                                WebFormGroup.builder()
                                                                .groupId("PREMIUM_ORIGINS")
                                                                .groups(Collections.singletonList(WebFormGroup.builder()
                                                                            .groupId("0")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("BANK_NAME").value("asdasd").build(),
                                                                                        TextWebFormField.builder().fieldId("CITY").value("asdad").build(),
                                                                                        TextWebFormField.builder().fieldId("COUNTRY").value("FR").build(),
                                                                                        TextWebFormField.builder().fieldId("ACCOUNT_OWNER_ID").value("a0cbcc73-8ddf-4eeb-b014-bcb27e7e3ae9").build(),
                                                                                        TextWebFormField.builder().fieldId("ACCOUNT_OWNER").value("sdfsd fsdfsdf").build(),
                                                                                        TextWebFormField.builder().fieldId("PREMIUM_TYPE").value("CASH").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build()
                                                                                ))
                                                                            .booleanFields(Arrays.asList(
                                                                                        BooleanWebFormField.builder().fieldId("IS_JOINT_ACCOUNT").value(false).build(),
                                                                                        BooleanWebFormField.builder().fieldId("IS_THIRD_PARTY_PAYMENT").value(false).build()
                                                                                ))
                                                                            .bigDecimalFields(Collections.singletonList(BigDecimalWebFormField.builder().fieldId("AMOUNT").value(new BigDecimal("250000")).build()))
                                                                            .calendarFields(Collections.singletonList(CalendarWebFormField.builder().fieldId("BANK_ACCOUNT_OPENED_DATE").value("2026-06-29T22:00:00.000+00:00").build()))
                                                                            .build()))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("DISTRIBUTOR_DECLARATION")
                                                                .textFields(Arrays.asList(
                                                                            TextWebFormField.builder().fieldId("EBO_MEETING_CONTEXT").value("asdasda").build(),
                                                                            TextWebFormField.builder().fieldId("MEET_TYPE").value("IN_PERSON").build(),
                                                                            TextWebFormField.builder().fieldId("EBO_KNOWLEDGE_LEVEL").value("EXCELLENT").build(),
                                                                            TextWebFormField.builder().fieldId("PROPOSED_INVESTMENT_COMPATIBILITY_WITH_EBO").value("asdasdas").build(),
                                                                            TextWebFormField.builder().fieldId("DEAL_REFUSED_BY_OTHER_COMPANY").value("asdasda").build(),
                                                                            TextWebFormField.builder().fieldId("INVESTMENT_OBJECTIVES").value("LONG_TERM").build(),
                                                                            TextWebFormField.builder().fieldId("ADDITIONAL_PAYMENTS").value("asdasda").build()
                                                                    ))
                                                                .calendarFields(Collections.singletonList(CalendarWebFormField.builder().fieldId("BUSINESS_RELATIONSHIP_START_DATE").value("2026-06-10T22:00:00.000+00:00").build()))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("PHYSICAL_PERSONS")
                                                                .groups(Collections.singletonList(WebFormGroup.builder()
                                                                            .groupId("0")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("UUID_EBO").value("a0cbcc73-8ddf-4eeb-b014-bcb27e7e3ae9").build(),
                                                                                        TextWebFormField.builder().fieldId("UUID_PERSON").value("7be1794d-8e75-4d78-9940-e251f745074d").build(),
                                                                                        TextWebFormField.builder().fieldId("EBO_NAME").value("sdfsd fsdfsdf").build()
                                                                                ))
                                                                            .groups(Arrays.asList(
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("IDENTIFICATION")
                                                                                        .textFields(Arrays.asList(
                                                                                                    TextWebFormField.builder().fieldId("SITUATION").value("active").build(),
                                                                                                    TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build()
                                                                                            ))
                                                                                        .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("IS_QUOTED_COMPANY").value(false).build()))
                                                                                        .bigDecimalFields(Collections.singletonList(BigDecimalWebFormField.builder().fieldId("ANNUAL_INCOME_BEFORE_TAX").value(new BigDecimal("250000")).build()))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("PEP_INFO")
                                                                                        .booleanFields(Arrays.asList(
                                                                                                    BooleanWebFormField.builder().fieldId("HAS_PUBLIC_FUNCTION").value(false).build(),
                                                                                                    BooleanWebFormField.builder().fieldId("HAS_RELATIVE_WITH_PUBLIC_FUNCTION").value(false).build()
                                                                                            ))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("AMOUNT")
                                                                                        .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build()))
                                                                                        .bigDecimalFields(Collections.singletonList(BigDecimalWebFormField.builder().fieldId("AMOUNT").value(new BigDecimal("250000")).build()))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("BENEFICIARY_WEALTH")
                                                                                        .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("AMOUNT_TYPE").value("percentage").build()))
                                                                                        .groups(Collections.singletonList(WebFormGroup.builder()
                                                                                                    .groupId("PROFESSIONAL_ACTIVITY")
                                                                                                    .textFields(Arrays.asList(
                                                                                                                TextWebFormField.builder().fieldId("COMPANY_NAME").value("asdasdasd").build(),
                                                                                                                TextWebFormField.builder().fieldId("ACTIVITY_COUNTRY").value("FR").build(),
                                                                                                                TextWebFormField.builder().fieldId("BUSINESS_SECTOR").value("art").build(),
                                                                                                                TextWebFormField.builder().fieldId("SECOND_ACTIVITY_COUNTRY").value("AF").build(),
                                                                                                                TextWebFormField.builder().fieldId("ADDITIONAL_INFO").value("asdasd").build()
                                                                                                        ))
                                                                                                    .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("ARE_FUNDS_GENERATED_FROM_SOURCE").value(true).build()))
                                                                                                    .bigDecimalFields(Arrays.asList(
                                                                                                                BigDecimalWebFormField.builder().fieldId("ACTIVITY_PERIOD").value(new BigDecimal("3")).build(),
                                                                                                                BigDecimalWebFormField.builder().fieldId("AMOUNT").value(new BigDecimal("100")).build()
                                                                                                        ))
                                                                                                    .build()))
                                                                                        .build(),
                                                                                        WebFormGroup.builder()
                                                                                        .groupId("ASSET_TYPE")
                                                                                        .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("AMOUNT_TYPE").value("percentage").build()))
                                                                                        .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("SEE_BALANCED_SHEET").value(false).build()))
                                                                                        .groups(Collections.singletonList(WebFormGroup.builder()
                                                                                                    .groupId("FINANCIAL_INVESTMENT")
                                                                                                    .textFields(Arrays.asList(
                                                                                                                TextWebFormField.builder().fieldId("COMPANY_NAME").value("asdasda").build(),
                                                                                                                TextWebFormField.builder().fieldId("CITY").value("asdasdas").build(),
                                                                                                                TextWebFormField.builder().fieldId("COUNTRY").value("FR").build(),
                                                                                                                TextWebFormField.builder().fieldId("ADDITIONAL_INFO").value("asdasdas").build()
                                                                                                        ))
                                                                                                    .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("SELECTED").value(true).build()))
                                                                                                    .bigDecimalFields(Collections.singletonList(BigDecimalWebFormField.builder().fieldId("AMOUNT").value(new BigDecimal("100")).build()))
                                                                                                    .calendarFields(Collections.singletonList(CalendarWebFormField.builder().fieldId("TRANSACTION_DATE").value("2026-06-17T22:00:00.000+00:00").build()))
                                                                                                    .build()))
                                                                                        .build()
                                                                                ))
                                                                            .build()))
                                                                .build()
                                                        ))
                                                    .build()
                                            ))
                                        .build(),
                                        WebFormGroup.builder()
                                        .groupId("PRODUCT_CHOICE")
                                        .textFields(Arrays.asList(
                                                    TextWebFormField.builder().fieldId("PRODUCT_CHOICE").value("MULTI_FR_001").build(),
                                                    TextWebFormField.builder().fieldId("PRODUCT_NAME").value("Liberté").build()
                                            ))
                                        .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("WANT_TO_HAVE_PAPER_VERSION").value(true).build()))
                                        .build(),
                                        WebFormGroup.builder()
                                        .groupId("RISK_PROFILE")
                                        .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("CALCULATED_RISK").value("BALANCED").build()))
                                        .groups(Arrays.asList(
                                                    WebFormGroup.builder()
                                                    .groupId("QUESTIONS")
                                                    .bigDecimalFields(Arrays.asList(
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION1").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION2").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION3").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION4").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION5").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION6").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION7").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION8").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION9").value(new BigDecimal("4")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION10").value(new BigDecimal("4")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION11").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION12").value(new BigDecimal("4")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION13").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION14").value(new BigDecimal("3")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("QUESTION15").value(new BigDecimal("3")).build()
                                                        ))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("INVESTMENT_PROFILE")
                                                    .textFields(Arrays.asList(
                                                                TextWebFormField.builder().fieldId("POLICY_HOLDER_CATEGORY").value("N").build(),
                                                                TextWebFormField.builder().fieldId("UPGRADE").value("A").build(),
                                                                TextWebFormField.builder().fieldId("UPGRADE_REASON").value("asdadasda").build()
                                                        ))
                                                    .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("DEVIATION").value(false).build()))
                                                    .bigDecimalFields(Arrays.asList(
                                                                BigDecimalWebFormField.builder().fieldId("INVESTMENT_PERCENT").value(new BigDecimal("1")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("INVESTMENT_PRIME").value(new BigDecimal("250000")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("IMMOVABLE_PROPERTY").value(new BigDecimal("100000")).build()
                                                        ))
                                                    .build()
                                            ))
                                        .build()
                                ))
                            .build(),
                            WebFormGroup.builder()
                            .groupId("APP_FORM")
                            .groups(Arrays.asList(
                                        WebFormGroup.builder()
                                        .groupId("ADDITIONAL_INFO")
                                        .groups(Arrays.asList(
                                                    WebFormGroup.builder()
                                                    .groupId("LIFE_ASSURED")
                                                    .groups(Collections.singletonList(WebFormGroup.builder()
                                                                .groupId("PHYSICAL_PERSONS")
                                                                .groups(Collections.singletonList(WebFormGroup.builder()
                                                                            .groupId("0")
                                                                            .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("POLICY_HOLDER").value("7be1794d-8e75-4d78-9940-e251f745074d").build()))
                                                                            .booleanFields(Arrays.asList(
                                                                                        BooleanWebFormField.builder().fieldId("LIFE_ASSURED_IS_PH").value(true).build(),
                                                                                        BooleanWebFormField.builder().fieldId("SHOULD_BE_SIGNED").value(false).build()
                                                                                ))
                                                                            .build()))
                                                                .build()))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("BENEFICIARY")
                                                    .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("MODEL").value("NO_BENEFICIARY").build()))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("CORRESPONDENCE")
                                                    .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("CORRESPONDENCE_TYPE").value("PLATEFORME_DIGITAL_INSURER").build()))
                                                    .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("IS_CORRESPONDENCE_BY_EMAIL").value(true).build()))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("GENERAL_CONSENT")
                                                    .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("CP_SENDING_PREFERENCE").value("digital_sending_policy_holder").build()))
                                                    .booleanFields(Arrays.asList(
                                                                BooleanWebFormField.builder().fieldId("DISCLAIMER_DIGITAL_SIGNATURE").value(false).build(),
                                                                BooleanWebFormField.builder().fieldId("CONNECT_ACCESS_REQUESTED").value(true).build(),
                                                                BooleanWebFormField.builder().fieldId("PAYMENT_DELIVERY_SHARES").value(true).build()
                                                        ))
                                                    .groups(Arrays.asList(
                                                                WebFormGroup.builder()
                                                                .groupId("TARGET_MARKET")
                                                                .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("BELONG_TO_TARGET_MARKET").value(true).build()))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("AGGREGATOR")
                                                                .textFields(Arrays.asList(
                                                                            TextWebFormField.builder().fieldId("PERSON_TO_CONTACT").value("adas").build(),
                                                                            TextWebFormField.builder().fieldId("ADDRESS").value("asdasd").build()
                                                                    ))
                                                                .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("DISCLAIMER_AGGREGATOR").value(true).build()))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("SIGNATORIES")
                                                                .groups(Arrays.asList(
                                                                            WebFormGroup.builder()
                                                                            .groupId("0")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("UUID").value("B/2763").build(),
                                                                                        TextWebFormField.builder().fieldId("DENOMINATION").value("(Internal) - Amaury de Potter d'Indoye").build(),
                                                                                        TextWebFormField.builder().fieldId("COUNTRY").value("FR").build(),
                                                                                        TextWebFormField.builder().fieldId("CITY").value("weerwe").build(),
                                                                                        TextWebFormField.builder().fieldId("TYPE").value("broker").build(),
                                                                                        TextWebFormField.builder().fieldId("PHONE_NUMBER").value("+2222").build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("1")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("UUID").value("7be1794d-8e75-4d78-9940-e251f745074d").build(),
                                                                                        TextWebFormField.builder().fieldId("DENOMINATION").value("fsdfsdf sdfsd").build(),
                                                                                        TextWebFormField.builder().fieldId("COUNTRY").value("FR").build(),
                                                                                        TextWebFormField.builder().fieldId("CITY").value("asdasd").build(),
                                                                                        TextWebFormField.builder().fieldId("TYPE").value("physical").build(),
                                                                                        TextWebFormField.builder().fieldId("PHONE_NUMBER").value("+2222").build()
                                                                                ))
                                                                            .build()
                                                                    ))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("SUITABILITY_DECLARATION")
                                                                .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("DUTY_ADVICE").value(true).build()))
                                                                .build()
                                                        ))
                                                    .build()
                                            ))
                                        .build(),
                                        WebFormGroup.builder()
                                        .groupId("INVESTMENT_FEES")
                                        .groups(Arrays.asList(
                                                    WebFormGroup.builder()
                                                    .groupId("PREMIUM_INVESTMENT")
                                                    .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("SPECIALIZED_INVESTMENT_ACKNOWLEDGE").value(false).build()))
                                                    .groups(Arrays.asList(
                                                                WebFormGroup.builder()
                                                                .groupId("PREMIUM_INFORMATION")
                                                                .textFields(Arrays.asList(
                                                                            TextWebFormField.builder().fieldId("POLICY_CONTRACT").value("EUR").build(),
                                                                            TextWebFormField.builder().fieldId("PREMIUM_CURRENCY").value("EUR").build()
                                                                    ))
                                                                .booleanFields(Arrays.asList(
                                                                            BooleanWebFormField.builder().fieldId("FOLDER_LINK_TO_REINVESTMENT").value(false).build(),
                                                                            BooleanWebFormField.builder().fieldId("IS_FAMILY_CASE").value(false).build(),
                                                                            BooleanWebFormField.builder().fieldId("IS_ASSET_TRANSFER").value(false).build(),
                                                                            BooleanWebFormField.builder().fieldId("IS_REST_OF_PREMIUM").value(false).build()
                                                                    ))
                                                                .bigDecimalFields(Arrays.asList(
                                                                            BigDecimalWebFormField.builder().fieldId("GROSS_INITIAL_PREMIUM").value(new BigDecimal("250000")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("NET_INITIAL_PREMIUM").value(new BigDecimal("247500")).build()
                                                                    ))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("EXTERNAL_FUND_ANNEXES")
                                                                .groups(Arrays.asList(
                                                                            WebFormGroup.builder()
                                                                            .groupId("0")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("DE000A2JJ1V7").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("ODDO BHF Polaris Moderate CN-EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.29")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.25")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("3.47")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.43")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.43")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.25")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("1")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("DE000A2QBG39").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("ODDO BHF Money Market CN-EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("ODDO BHF Asset Management GmbH").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MONEY_MARKET_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("2.36")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.16")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("1.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.16")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("2")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FI0008812011").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Evli Nordic Corporate Bond Serie IB EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("EVLI FUNDS").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("Fund").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("5.05")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("4.65")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.6")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.6")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("2.65")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("3")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FI4000233242").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Evli Short Corporate Bond Units IB EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("Fund").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("4.36")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("4.01")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("2.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("2.01")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("4")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0000003998").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Lazard Equity SRI PC EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("ISR").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("17.44")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("16.31")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("12.71")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("11.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("9.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("14.31")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("5")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0010610725").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("LFR Actions Solidaires GP").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("FINANSOL").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.61")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("5.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("3.94")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("1.94")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.61")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.03")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("6")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0011036920").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("ODDO BHF Avenir Europe CN-EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("ODDO ASSET MANAGEMENT").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("12.12")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.96")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("11.16")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("2.38")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.42")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.96")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("9.16")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("7")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0011845429").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("R-co Conviction Equity Value Euro P EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("27.1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("26.15")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("13.17")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("12.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("10.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("24.15")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("8")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0011847409").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("R-co Valor P EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("ROTHSCHILD & CIE GESTION").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("17.88")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("16.78")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("11.34")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("14.78")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("9")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013274354").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("ODDO BHF Avenir Euro CN-EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("12.57")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("11.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("3.25")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.26")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.26")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("9.58")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("10")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013286614").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Echiquier Credit SRI Europe G").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FINANCIERE DE L'ECHIQUIER").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.82")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.68")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("1.77")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("0.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-1.05")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.82")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.68")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("11")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013289386").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Amundi 3 M R - (C)").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("AMUNDI ASSET MANAGEMENT S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MONEY_MARKET_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("ISR").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("2.46")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.19")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.27")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("1.92")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.73")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.27")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.19")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.27")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("12")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013293891").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Fidelity Europe Action - N").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FIL GESTION SASU").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("13.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("12.4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("5.21")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("4.26")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("2.26")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("10.4")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("13")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013294303").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("DNCA Value Europe FCP - N").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("DNCA FINANCE").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("15.365857")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.32")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("14.045857")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("7.87")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("6.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("4.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.32")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("12.045857")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("14")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013294311").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Eurose - N").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("DNCA FINANCE").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("9.32")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.96")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("8.36")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.09")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.96")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("6.36")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("15")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013304664").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("OFI RS MultiTrack - RF - Cap").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("HEDGE_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("10.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("9.32")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("4.61")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("3.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("1.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("7.32")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("16")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013305935").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("LAZARD CREDIT FI-TC").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Lazard Freres Gestion SAS").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("7.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.68")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("6.62")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("4.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("3.54")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("1.54")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.68")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("4.62")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("17")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013309705").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Fourpoints Euro Global leaders FCP - G").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FOURPOINTS INVESTMENT MANAGEMENT").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("10.696568")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("9.496568")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("7.496568")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("18")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013311487").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Ostrum SRI Cash Plus T(C) EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MONEY_MARKET_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("2.48")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.25")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.23")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("1.98")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.73")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.27")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.25")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.23")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("19")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013392636").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Mondrian G").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FOURPOINTS INVESTMENT MANAGERS SASU").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.71")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.19")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("4.44")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.73")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.73")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.71")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.19")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("20")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013406717").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Echiquier Alpha Major G EUR Cap").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("-9.6")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-10.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("-0.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-1.48")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-3.48")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-12.5")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("21")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("FR0013458783").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("DNCA Serenite Plus N").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.59")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("3.09")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("2.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("1.09")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("22")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("IE0007356250").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Russell Continental European Equity Fund A Acc").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Russell Investment Ireland Limited").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("18.26")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("17.27")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("11.4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.41")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.41")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("15.27")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("23")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("IE000CMDDU15").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Comgest Growth Global Compounders Z Eur Acc").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("-2.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-3.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-5.3")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("24")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("IE00B0Z6XF88").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Russell World Equity - Euro Hedged T").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Russell Investment Ireland Limited").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("16.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.02")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("15.93")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("10.76")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("9.74")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("7.74")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.02")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("13.93")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("25")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("IE00B7XQ9518").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Russell Global Bond (Euro Hdg) EH A").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Russell Investment Ireland Limited").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("4.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.79")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("3.34")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("-1.26")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-2.05")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-4.05")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.79")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("1.34")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("26")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0104884605").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Pictet Funds (Lux) - Water - I").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Pictet Asset Management (Europe) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("-2.19")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-3.29")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("7.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("6.89")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("4.89")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-5.29")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("27")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0167237972").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("BNP Paribas InstiCash EUR 1D LVNAV P").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("BNP Paribas S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MONEY_MARKET_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("2.4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.23")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.17")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("1.88")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.65")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.23")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.17")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("28")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0318941662").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Fidelity Funds - World Fund Y-ACC-EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FIL Investment Management (Luxembourg) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("11.27")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("10.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("11.61")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("8.24")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("29")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0346393704").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Fidelity Euro short term bond").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FIL Investment Management (Luxembourg) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("1.37")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.49")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("0.88")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("0.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("0.41")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-1.59")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.49")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-1.12")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("30")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0358043668").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("UBS (Lux) Equity Fund - European Opportunity (EUR) Q-acc").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("UBS Fund Management (Luxembourg) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("15.28")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("14.31")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.94")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("12.31")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("31")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0439179432").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("JPMorgan Funds - Global Corporate Bond C (acc) - EUR (hedged)").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("JPMORGAN ASSET MANAGEMENT SARL").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("5.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("4.63")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("-0.69")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-1.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-3.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("2.63")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("32")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0565136040").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("FIRST EAGLE AMUNDI INTERNATIONAL IE (EUR) ACC").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("AMUNDI LUXEMBOURG SA").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("12.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("10.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("12.11")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.93")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.93")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("8.95")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("33")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0755218046").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Fidelity Funds - America Fund Y-ACC-EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FIL Investment Management (Luxembourg) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("-1.6")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-2.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("12.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-4.64")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("34")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0782316961").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("JPMorgan Investment Funds - Global Income Fund C").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("JPMORGAN ASSET MANAGEMENT SARL").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("10")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.75")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("9.25")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("4.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("3.33")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("1.33")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.75")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("7.25")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("35")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0858293193").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Goldman Sachs Global Credit Portfolio (Hedged) R Acc EUR-Hedged").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("GOLDMAN SACHS ASSET MANAGEMENT INTERNATIONAL").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("5.53")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("4.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("-0.63")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-1.21")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-3.21")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("2.95")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("36")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0860993814").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Goldman Sachs Growth & Emerging Markets Debt Portfolio R EUR Acc").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("GOLDMAN SACHS ASSET MANAGEMENT INTERNATIONAL").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("2.06")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("1.16")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("3.07")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.17")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.17")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-0.84")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("37")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0866427866").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("LO Funds - Golden Age EUR MA").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("LOMBARD ODIER FUNDS").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("6.82")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("5.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("0.16")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-1.02")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-3.02")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("3.64")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("38")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0878867430").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("First Eagle Amundi International EUR -RE-C ACC").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("AMUNDI LUXEMBOURG SA").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("12.11")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.56")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("10.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("12.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.56")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("8.55")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("39")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0914733646").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Mirova Europe Environmental Equity N/A (EUR)").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Natixis Investment Managers S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("FCP").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("13.06")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.15")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("11.91")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("0.91")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-0.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-2.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.15")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("9.91")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("40")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0975201434").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Sparinvest SICAV - Global Investment Grade").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("SPARINVEST SA").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.6")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("-0.34")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-0.94")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-2.94")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.6")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.9")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("41")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0992624949").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Carmignac Portfolio Securite F EUR Cap").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("CARMIGNAC PORTFOLIO").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.28")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("2.41")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.77")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.23")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.64")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("42")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU0992627611").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Carmignac Portfolio - Patrimoine - F").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("CARMIGNAC PORTFOLIO").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("13.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.15")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("12.09")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("3.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.15")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("10.09")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("43")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1038809049").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Flossbach Von Storch - Multiple Opportunities II - IT").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Flossbach von Storch Invest SA").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("4.11")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.87")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("3.24")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("4.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("4.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("2.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.87")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("1.24")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("44")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1097728445").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Fidelity Funds - Global Multi Asset Income Fund - Y - ACC - EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FIL Investment Management (Luxembourg) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("10.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("9.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("2.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.03")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("7.52")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("45")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1234714159").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("DNCA Invest Global Leaders N").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("7.68455")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.47")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("6.21455")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("2.89")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("1.42")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-0.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.47")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("4.21455")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("46")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1276832125").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("JH Pan Eur H2C~Shs -H2 EUR-~Capitalisatio").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("HENDERSON FUND MANAGEMENT LUXEMBOURG").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("21.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.88")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("20.16")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("12.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("11.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("9.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.88")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("18.16")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("47")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1434520000").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Candriam Sus Bd EmMkt R EUR C").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("-1.59")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.91")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-2.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("0.83")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-0.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-2.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.91")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-4.5")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("48")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1434524416").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Candriam Sust Eq EM R EUR C").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("16.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.06")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("15.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("1.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("0.58")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-1.42")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.06")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("13.64")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("49")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1434528169").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Candriam Sust Eq World R EUR C").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("7.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.01")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("6.51")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("12.39")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("11.38")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("9.38")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.01")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("4.51")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("50")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1452410738").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("MWM SICAV Patrimonial SRI Fd I").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("9.21")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("8.31")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.42")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.9")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("6.31")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("51")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1481584016").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Flossbach von Storch - Bond Opportunities IT").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FVS FCP - AKTIEN DEUTSCHLAND").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.83")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("3.31")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("0.88")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("0.36")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-1.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("1.31")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("52")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1500599094").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Monocle Fund B EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Carne Global Fund Managers (Luxembourg) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("4.27")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.78")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("2.49")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("3.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("2.21")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("0.21")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.78")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("0.49")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("53")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1582988488").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("M&G(LUX)Dynamic Allocation Euro C ACC").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("M&G SECURITIES LTD").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("15.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.02")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("14.11")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.63")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.61")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.61")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.02")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("12.11")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("54")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1665237969").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("M&G(Lux) Global Listed Infrastructure C EUR Acc").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("2.26")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("1.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.46")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.46")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-0.78")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("55")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1670710232").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("M&G (Lux) Global Dividend C Acc EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("M&G Luxembourg S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("4.75")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.92")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("3.83")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("12.29")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("11.37")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("9.37")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.92")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("1.83")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("56")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1670715546").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("M&G LUX GLOBAL SUSTAIN PARIS ALIGNED C EUR ACC").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("M&G Luxembourg S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("0.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-0.02")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("11.68")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.71")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.71")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-2.02")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("57")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1694789709").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("DNCA Invest Alpha Bonds N").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("DNCA Finance Luxembourg SA").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("6.2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.89")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("5.31")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.11")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.89")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("3.31")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("58")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1731833056").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Fidelity Funds - Global Dividend - Y").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("FIL INVESTMENT MANAGEMENT (LUXEMBOURG) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("9.98")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("8.94")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("11.59")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("10.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("8.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("6.94")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("59")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1781814329").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("EDR INCOME EUROPE CR EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Edmond de Rothschild Asset Management (Luxembourg) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("7.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("6.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("4.48")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("3.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("1.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("4.04")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("60")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1781815995").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("EdR Fund Healthcare CR EUR C").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("EDMOND DE ROTHSCHILD ASSET MANAGEMENT").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("2.63")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.32")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("1.31")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("5.84")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("4.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("2.52")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.32")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-0.69")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("61")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1781816530").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Edmond de Rothschild Fund-Big Data CR-EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("7.37")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.33")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("6.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("13.97")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("12.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("10.64")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.33")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("4.04")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("62")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1781816704").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("EdR Fund Bond Alloaction CR EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Edmond de Rothschild Asset").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("3.99")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.93")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("3.06")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("1.63")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("0.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-1.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.93")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("1.06")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("63")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1809976100").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("LO Funds - World Brands (EUR) MA").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("-5.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-6.65")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.75")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("5.45")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("3.45")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-8.65")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("64")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1849527855").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("ODDO BHF II Polaris Balanced CN-EUR~Cap").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("ODDO BHF Asset Management GmbH").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("MIXED_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("1.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("-0.13")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("5.11")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("3.76")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("1.76")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-2.13")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("65")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1883308279").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("European Eq Smll Cap R2 E C").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Amundi Luxembourg S.A..").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("15.48")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.23")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("14.25")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("5.27")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("4.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("2.04")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.23")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("12.25")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("66")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1883852656").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Amundi Funds US Bond - R2 EUR H C").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Amundi Luxembourg S.A..").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("BOND_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("6.68")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("0.73")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("5.95")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("-0.92")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-1.65")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-3.65")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("2.73")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("3.95")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("67")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU1906457897").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("AIM LUX-C-Quadrat Europe Selecton F EUR Cap").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("C-Quadrat Asset Management France").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("1.68")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.38")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("0.3")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("6.08")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("4.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("2.7")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.38")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("-1.7")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("68")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU2016064383").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("Schroder ISF Global Energy Transition C Acc EUR Hed").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("GREENFIN").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("5")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("35.85")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.07")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("34.78")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("-5.15")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("-6.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("-8.22")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.07")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("32.78")).build()
                                                                                ))
                                                                            .build(),
                                                                            WebFormGroup.builder()
                                                                            .groupId("69")
                                                                            .textFields(Arrays.asList(
                                                                                        TextWebFormField.builder().fieldId("ISIN_CODE").value("LU2107591401").build(),
                                                                                        TextWebFormField.builder().fieldId("FUND_NAME").value("LOF Planetary Transition SH EUR MA").build(),
                                                                                        TextWebFormField.builder().fieldId("CURRENCY").value("EUR").build(),
                                                                                        TextWebFormField.builder().fieldId("FIRM_NAME").value("Lombard Odier Funds (Europe) S.A.").build(),
                                                                                        TextWebFormField.builder().fieldId("CATEGORY").value("EQUITY_FUND").build(),
                                                                                        TextWebFormField.builder().fieldId("LEGAL_FORM").value("SICAV").build(),
                                                                                        TextWebFormField.builder().fieldId("ESG_LABEL").value("").build()
                                                                                ))
                                                                            .bigDecimalFields(Arrays.asList(
                                                                                        BigDecimalWebFormField.builder().fieldId("FUND_SSRI").value(new BigDecimal("4")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS").value(new BigDecimal("20.53")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_MANAGEMENT_FEES").value(new BigDecimal("1.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET").value(new BigDecimal("19.35")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_GROSS_N5").value(new BigDecimal("4.55")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_NET_N5").value(new BigDecimal("3.37")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("UNIT_PERFORMANCE_FINAL_N5").value(new BigDecimal("1.37")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("CONTRACT_CHARGES").value(new BigDecimal("2")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("TOTAL_CHARGES").value(new BigDecimal("3.18")).build(),
                                                                                        BigDecimalWebFormField.builder().fieldId("FINAL_PERFORMANCE").value(new BigDecimal("17.35")).build()
                                                                                ))
                                                                            .build()
                                                                    ))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("EXTERNAL_COLLECTIVE_FUND")
                                                                .textFields(Arrays.asList(
                                                                            TextWebFormField.builder().fieldId("FUND_ID").value("ECF_1").build(),
                                                                            TextWebFormField.builder().fieldId("PREMIUM_INVESTED_BY_FUND_CURRENCY").value("EUR").build(),
                                                                            TextWebFormField.builder().fieldId("CURRENCY_REFERENCE").value("EUR").build(),
                                                                            TextWebFormField.builder().fieldId("FUND_GROUP_ID").value("6000000004753522659").build()
                                                                    ))
                                                                .booleanFields(Collections.singletonList(BooleanWebFormField.builder().fieldId("HAS_REINVESTMENT").value(false).build()))
                                                                .bigDecimalFields(Collections.singletonList(BigDecimalWebFormField.builder().fieldId("PREMIUM_INVESTED_BY_FUND").value(new BigDecimal("250000")).build()))
                                                                .groups(Collections.singletonList(WebFormGroup.builder()
                                                                            .groupId("EXTERNAL_COLLECTIVE_FUNDS")
                                                                            .groups(Collections.singletonList(WebFormGroup.builder()
                                                                                        .groupId("0")
                                                                                        .textFields(Arrays.asList(
                                                                                                    TextWebFormField.builder().fieldId("FUND_MNEMONIC").value("FR0013289386_EUR").build(),
                                                                                                    TextWebFormField.builder().fieldId("FUND_NAME").value("Amundi 3 M R - (C)").build(),
                                                                                                    TextWebFormField.builder().fieldId("ISIN").value("FR0013289386").build()
                                                                                            ))
                                                                                        .bigDecimalFields(Collections.singletonList(BigDecimalWebFormField.builder().fieldId("PERCENT").value(new BigDecimal("100")).build()))
                                                                                        .build()))
                                                                            .build()))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("SUITABILITY_CONTROL")
                                                                .bigDecimalFields(Collections.singletonList(BigDecimalWebFormField.builder().fieldId("ROUNDED_POLICY_RISK_LEVEL").value(new BigDecimal("1")).build()))
                                                                .build()
                                                        ))
                                                    .build(),
                                                    WebFormGroup.builder()
                                                    .groupId("FEES")
                                                    .bigDecimalFields(Arrays.asList(
                                                                BigDecimalWebFormField.builder().fieldId("ENTRY_FEE").value(new BigDecimal("1")).build(),
                                                                BigDecimalWebFormField.builder().fieldId("TOTAL_FUND_FEES").value(new BigDecimal("2")).build()
                                                        ))
                                                    .groups(Arrays.asList(
                                                                WebFormGroup.builder()
                                                                .groupId("THIRD_FEES")
                                                                .bigDecimalFields(Arrays.asList(
                                                                            BigDecimalWebFormField.builder().fieldId("INITIAL_COMMISSION").value(new BigDecimal("1")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("INTERMEDIATION_COMMISSION").value(new BigDecimal("1")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("INTERMEDIATION_COMMISSION_QUARTERLY").value(new BigDecimal("0.25")).build()
                                                                    ))
                                                                .build(),
                                                                WebFormGroup.builder()
                                                                .groupId("INSURANCE_FEES")
                                                                .bigDecimalFields(Arrays.asList(
                                                                            BigDecimalWebFormField.builder().fieldId("LIA_FEE").value(new BigDecimal("0")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("ADMINISTRATIVE_FEE").value(new BigDecimal("1")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("ADMINISTRATIVE_FEE_QUARTERLY").value(new BigDecimal("0.25")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("POLICY_FEE").value(new BigDecimal("2126")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("POLICY_FEE_FOR_YEAR").value(new BigDecimal("2026")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("POLICY_FEE_QUARTERLY").value(new BigDecimal("531.5")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("SWITCH_OPERATION_NUMBER").value(new BigDecimal("2")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("SWITCH_FEE").value(new BigDecimal("0.5")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("MAXIMUM_SWITCH_FEE_AMOUNT").value(new BigDecimal("500")).build(),
                                                                            BigDecimalWebFormField.builder().fieldId("SURRENDER_FEES_WITHIN_YEARS").value(new BigDecimal("1")).build()
                                                                    ))
                                                                .build()
                                                        ))
                                                    .build()
                                            ))
                                        .build()
                                ))
                            .build(),
                            WebFormGroup.builder()
                            .groupId("ONBOARDING_ID")
                            .textFields(Collections.singletonList(TextWebFormField.builder().fieldId("CASE_ID").value("NBD_68219_20260619").build()))
                            .build(),
                            WebFormGroup.builder()
                            .groupId("FORM_INITIATOR")
                            .textFields(Arrays.asList(
                                        TextWebFormField.builder().fieldId("USER_LOGIN").value("jtchietcheu@lia.int").build(),
                                        TextWebFormField.builder().fieldId("DISPLAYED_NAME").value("Rahma Hmissi").build(),
                                        TextWebFormField.builder().fieldId("COMPANY_NAME").value("Demo Partner").build(),
                                        TextWebFormField.builder().fieldId("PARTNER_TYPE").value("OTHER").build(),
                                        TextWebFormField.builder().fieldId("INITIATOR_ROLE").value("INTERNAL").build()
                                ))
                            .bigDecimalFields(Arrays.asList(
                                        BigDecimalWebFormField.builder().fieldId("USER_ID").value(new BigDecimal("43815")).build(),
                                        BigDecimalWebFormField.builder().fieldId("COMPANY_ID").value(new BigDecimal("1")).build()
                                ))
                            .build()
                    ))
                .build();
    }
}
