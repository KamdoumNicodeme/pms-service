private WebForm webFormWithIntermediate() {
    var companyName = TextWebFormField.builder()
            .fieldId("COMPANY_NAME")
            .selectedValue("My Partner")
            .build();

    var country = TextWebFormField.builder()
            .fieldId("COUNTRY")
            .selectedValue("LU")
            .build();

    var address = WebFormGroup.builder()
            .groupId("ADDRESS")
            .textFields(List.of(country))
            .groups(new ArrayList<>())
            .build();

    var intermediate = WebFormGroup.builder()
            .groupId("INTERMEDIATE")
            .textFields(List.of(companyName))
            .groups(List.of(address))
            .build();

    var distributors = WebFormGroup.builder()
            .groupId("DISTRIBUTORS")
            .groups(List.of(intermediate))
            .build();

    var kyc = WebFormGroup.builder()
            .groupId("KYC")
            .groups(List.of(distributors))
            .build();

    return WebForm.builder()
            .groups(List.of(kyc))
            .build();
}
