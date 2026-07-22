public abstract class KycCont
        extends AbstractDocumentRequiredSignatoryBuilderService {

    public void buildDocumentRequiredSignatoryPhysical(
            final WebForm webForm,
            final List<DocumentRequiredSignatory> documentRequiredSignatories,
            final List<RequiredSignatory> signatories) {

        if (WebformUtils.isHolderSpecificType(webForm, "PP")) {
            buildDocumentRequiredSignatoryGlobal(
                    webForm,
                    documentRequiredSignatories,
                    signatories
            );
        }
    }

    public void buildDocumentRequiredSignatoryEntity(
            final WebForm webForm,
            final List<DocumentRequiredSignatory> documentRequiredSignatories,
            final List<RequiredSignatory> signatories) {

        if (WebformUtils.isHolderSpecificType(webForm, "PM")) {
            buildDocumentRequiredSignatoryGlobal(
                    webForm,
                    documentRequiredSignatories,
                    signatories
            );
        }
    }

    public void buildDocumentRequiredSignatorySC(
            final WebForm webForm,
            final List<DocumentRequiredSignatory> documentRequiredSignatories,
            final List<RequiredSignatory> signatories) {

        if (WebformUtils.isHolderSpecificType(webForm, "SC")) {
            buildDocumentRequiredSignatoryGlobal(
                    webForm,
                    documentRequiredSignatories,
                    signatories
            );
        }
    }

    private void buildDocumentRequiredSignatoryGlobal(
            final WebForm webForm,
            final List<DocumentRequiredSignatory> documentRequiredSignatories,
            final List<RequiredSignatory> signatories) {

        final var kycGrp =
                WebformUtils.getFirstWebFormGroupById(webForm, KYC_WF);

        if (kycGrp == null) {
            return;
        }

        final boolean isKycRequired = Boolean.parseBoolean(
                WebformUtils.getWebFormFieldValueById(
                        kycGrp,
                        IS_KYC_REQUIRED_WF
                )
        );

        if (!isKycRequired) {
            return;
        }

        final var detailsGrp =
                WebformUtils.getGroupInGroup(kycGrp, DETAILS_WF);

        if (detailsGrp == null) {
            return;
        }

        final var ebosGrp =
                WebformUtils.getGroupInGroup(detailsGrp, EBO_KYCS_WF);

        if (ebosGrp == null
                || CollectionUtils.isEmpty(ebosGrp.getGroups())) {
            return;
        }

        // Tous les EBO sont conservés, indépendamment de leur type.
        ebosGrp.getGroups().forEach(
                eboKycGrp -> buildDocumentRequiredSignatory(
                        webForm,
                        eboKycGrp,
                        documentRequiredSignatories,
                        signatories
                )
        );
    }

    private void buildDocumentRequiredSignatory(
            final WebForm webForm,
            final WebFormGroup group,
            final List<DocumentRequiredSignatory> documentRequiredSignatories,
            final List<RequiredSignatory> signatories) {

        final var eboId =
                WebformUtils.getWebFormFieldValueById(group, EBO_ID_WF);

        final List<RequiredSignatory> requiredSignatories =
                signatories.stream()
                        .filter(Objects::nonNull)
                        .filter(signatory ->
                                Set.of(
                                        "BROKER",
                                        "POLICY_HOLDER",
                                        "SIGNATORY"
                                ).contains(signatory.getType())
                                || (
                                        "ECONOMICAL_BENEFICIAL_OWNER"
                                                .equals(signatory.getType())
                                        && Objects.equals(
                                                eboId,
                                                signatory.getThirdPartyId()
                                        )
                                )
                        )
                        .toList();

        buildDocumentRequiredSignatory(
                documentRequiredSignatories,
                requiredSignatories,
                eboId
        );
    }
}
