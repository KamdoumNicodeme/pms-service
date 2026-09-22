public static ClientProfilingThirdPartyDto buildThirdPartyDto() {

    return ClientProfilingThirdPartyDto.PhysicalPersonDtoClientProfiling
        .builder()
        .type("PHYSICAL_PERSON")
        .thirdPartyId("0003310939")
        .aeoiStatus(TEST_AEOI_STATUS)
        .fatcaStatus(TEST_FATCA_STATUS)
        .taxCountry(TEST_COUNTRY)
        .taxNumber("8798546")
        .legalAddress(buildLegalAddressDto())
        .taxInformations(List.of(buildTaxInformationDto()))
        .build();
}
