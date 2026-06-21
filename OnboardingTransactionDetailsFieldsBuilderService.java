private void evaluatePhTypeAssessment(
        Group group,
        BusinessTransaction transaction,
        Map<String, List<String>> overallCaseRisk) {

    var phTypeAssessment = (TextInputField) ChecklistUtils.getFieldInGroup(group, PH_TYPE_ASSESSMENT);

    if (phTypeAssessment == null) {
        phTypeAssessment = TextInputField.builder()
                .fieldId(PH_TYPE_ASSESSMENT)
                .build();
        group.getFields().add(phTypeAssessment);
    }

    phTypeAssessment.setIsActive(true);
    phTypeAssessment.incrementOrder();
    phTypeAssessment.setLabel("PH type assessment");
    phTypeAssessment.setEnabled(false);
    phTypeAssessment.setMandatory(true);
    phTypeAssessment.setDisplayIf("true");
    phTypeAssessment.setLabelBold(false);
    phTypeAssessment.setSourceSystem("From CLASS");

    String phType = RulesUtils.getRiskFactorData(transaction, "INT_RF_014");

    if (phType.contains("N/A")) {
        var blockedPhTypes = Arrays.stream(phType.split(";"))
                .map(String::trim)
                .filter(type -> !"N/A".equals(type))
                .toList();

        overallCaseRisk
                .computeIfAbsent(RulesConstants.BLOCKED, key -> new ArrayList<>())
                .add("PH Type risk factor cannot be assessed - " + String.join(",", blockedPhTypes));
    } else {
        phTypeAssessment.setSelectedValue(phType);
    }
}
