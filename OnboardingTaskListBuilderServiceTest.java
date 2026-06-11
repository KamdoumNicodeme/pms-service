private WebForm webFormWithIntermediate() {
    var companyName = TextInputField.builder()
            .fieldId("COMPANY_NAME")
            .selectedValue("My Partner")
            .build();

    var country = SelectInputField.builder()
            .fieldId("COUNTRY")
            .selectedValue("LU")
            .build();

    var address = WebFormGroup.builder()
            .groupId("ADDRESS")
            .fields(List.of(country))
            .groups(new ArrayList<>())
            .build();

    var intermediate = WebFormGroup.builder()
            .groupId("INTERMEDIATE")
            .fields(List.of(companyName))
            .groups(List.of(address))
            .build();

    var distributors = WebFormGroup.builder()
            .groupId("DISTRIBUTORS")
            .fields(new ArrayList<>())
            .groups(List.of(intermediate))
            .build();

    var kyc = WebFormGroup.builder()
            .groupId("KYC")
            .fields(new ArrayList<>())
            .groups(List.of(distributors))
            .build();

    return WebForm.builder()
            .groups(List.of(kyc))
            .build();
}
