FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_1", TextInputField.class, group, "N/A", order, null, false, false);
FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_2", TextInputField.class, group, "N/A", order, null, false, false);

FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_1", SelectInputField.class, group, NO, order, null, false, false);
FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH + "_1", TextInputField.class, group, null, order, null, false, true);

FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_2", SelectInputField.class, group, NO, order, null, false, false);
FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH + "_2", TextInputField.class, group, null, order, null, false, true);

FieldHelper.testFieldValueAndIncr(MINIMUM_WEALTH + "_1", SelectInputField.class, group, null, order, null, true, true);

FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION_OK + "_1", SelectInputField.class, group, NO, order, null, true, true);

FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_1", TextInputField.class, group, "", order, null, true, false);
FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_1", TextInputField.class, group, "", order, null, false, false);
FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_2", TextInputField.class, group, "", order, null, true, false);
FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_2", TextInputField.class, group, "", order, null, false, false);

FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION + "_1", TextInputField.class, group, null, order, null, true, true);

FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_1", TextInputField.class, group, "N/A", order, null, false, false);
FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_2", TextInputField.class, group, "N/A", order, null, false, false);

FieldHelper.testFieldValueAndIncr(KYC_SUPPORTING_DOCUMENTS, SelectInputField.class, group, null, order, null, true, true);
