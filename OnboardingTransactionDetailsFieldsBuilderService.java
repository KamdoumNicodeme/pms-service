private Optional<String> resolveCommonSignatoryCountry() {
    List<WebFormGroup> generalConsentGroups = new ArrayList<>();

    WebformUtils.getWebFormObjectsById(
            GENERAL_CONSENT,
            webForm,
            null,
            null,
            generalConsentGroups,
            null
    );

    if (generalConsentGroups.isEmpty()) {
        return Optional.empty();
    }

    WebFormGroup signatories = WebformUtils.getGroupInGroup(
            generalConsentGroups.getFirst(),
            SIGNATORIES
    );

    if (signatories == null || signatories.getGroups() == null) {
        return Optional.empty();
    }

    String country = null;

    for (WebFormGroup signatory : signatories.getGroups()) {
        String type = WebformUtils.getValueForWebFormField(
                WebformUtils.getFieldInGroup(signatory, TYPE)
        );

        if (!"physical".equals(type) && !"administrator".equals(type)) {
            continue;
        }

        String currentCountry = WebformUtils.getValueForWebFormField(
                WebformUtils.getFieldInGroup(signatory, COUNTRY)
        );

        if (StringUtils.isBlank(currentCountry) || "N/A".equals(currentCountry)) {
            continue;
        }

        if (country == null) {
            country = currentCountry;
        } else if (!country.equals(currentCountry)) {
            return Optional.empty();
        }
    }

    return Optional.ofNullable(country);
}
