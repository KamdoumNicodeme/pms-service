package com.lombardinternational.casemanagement.service.decision.domain.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lombardinternational.casemanagement.service.decision.domain.model.rules.SelectInputFieldOption;

public class ReferenceServiceHelper {

    public static Map<String,List<String>> getCountryRiskLevel() {

        return Map.of("PROHIBITED", Arrays.asList("AF", "CY"), "EXCEPTION", Arrays.asList("MK", "SZ"), "HIGH", Arrays.asList("AE", "AG"), "MEDIUM",
                Arrays.asList("AD", "CL"), "STANDARD", Arrays.asList("FR", "BE", "LU"));
    }

    public static Map<String,List<String>> getIndustryRiskLevel() {

        return Map.of("VERY_HIGH", Arrays.asList("animals", "adult", "armements"), "HIGH", Arrays.asList("chari_fnd", "art_deal", "civ_serv"),
                "MEDIUM", Arrays.asList("chem_mrisk", "art_deal_m", "construct"), "STANDARD", Arrays.asList("agri", "aero_defen", "arme_lrisk"));
    }

    public static List<SelectInputFieldOption> getYesNoOptions() {

        return List.of(new SelectInputFieldOption("YES", "Yes"), new SelectInputFieldOption("NO", "No"));
    }

    public static List<SelectInputFieldOption> getYesNoNaOptions() {

        return List.of(new SelectInputFieldOption("YES", "Yes"), new SelectInputFieldOption("NO", "No"), new SelectInputFieldOption("NA", "N/A"));
    }

    public static List<SelectInputFieldOption> getComplianceLevelOptions() {

        return List.of(new SelectInputFieldOption("L0", "L0"), new SelectInputFieldOption("L1", "L1"), new SelectInputFieldOption("L2", "L2"),
                new SelectInputFieldOption("L3", "L3"), new SelectInputFieldOption("L4", "L4"), new SelectInputFieldOption("L5", "L5"),
                new SelectInputFieldOption("L6", "L6"));
    }

    public static List<SelectInputFieldOption> getIndustrySectorOptions() {

        return List.of(new SelectInputFieldOption("agri", "Agriculture"), new SelectInputFieldOption("construct", "Construction"),
                new SelectInputFieldOption("art_deal", "Art dealer"));
    }

    public static List<SelectInputFieldOption> getProfessionOptions() {

        return List.of(new SelectInputFieldOption("director", "Director"), new SelectInputFieldOption("lawyer", "Lawyer"),
                new SelectInputFieldOption("retired", "Retired"));
    }

    public static List<SelectInputFieldOption> getPartnerTypeOptions() {

        return List.of(new SelectInputFieldOption("AGENT_EMP", "Agent Employee"), new SelectInputFieldOption("AGENT_IND", "Agent Individual"),
                new SelectInputFieldOption("AGENT_EXT", "Agent External"), new SelectInputFieldOption("REFERRER", "Referrer"));
    }

    public static List<SelectInputFieldOption> getTransactionFeedbackOptions() {

        return List.of(new SelectInputFieldOption("ACCEPTED", "Accepted"), new SelectInputFieldOption("REJECTED", "Rejected"),
                new SelectInputFieldOption("REJECTED_AML", "Rejected AML"));
    }

    public static List<SelectInputFieldOption> getPolicyTypeOptions() {

        return List.of(new SelectInputFieldOption("A", "A"), new SelectInputFieldOption("B", "B"), new SelectInputFieldOption("C", "C"),
                new SelectInputFieldOption("D", "D"), new SelectInputFieldOption("N", "N"));
    }

    public static List<SelectInputFieldOption> getManagerTypeOptions() {

        return List.of(new SelectInputFieldOption("LIMITED", "Limited"), new SelectInputFieldOption("UNLIMITED", "Unlimited"),
                new SelectInputFieldOption("ADVISORY", "Advisory"), new SelectInputFieldOption("SELF_MANAGED", "Self-managed"));
    }

    public static List<SelectInputFieldOption> getEligibilityPriorityOptions() {

        return List.of(new SelectInputFieldOption("HIGH", "High (1 business day)"),
                new SelectInputFieldOption("MEDIUM", "Medium (3 business days)"), new SelectInputFieldOption("LOW", "Medium (1 week)"),
                new SelectInputFieldOption("NA", "N/A"));
    }

    public static List<SelectInputFieldOption> getComplianceSeniorStandardOptions() {

        return List.of(new SelectInputFieldOption("Standard sign-off", "Standard sign-off"),
                new SelectInputFieldOption("Senior sign-off", "Senior sign-off"),
                new SelectInputFieldOption("None (Compliance escalation required)", "None (Compliance escalation required)"));
    }

    public static List<SelectInputFieldOption> getThirdPartyTypeOptions() {

        return List.of(new SelectInputFieldOption("THIRD_PARTY", "ThirdParty"),
                new SelectInputFieldOption("policyhold", "Policy holder (default)"),
                new SelectInputFieldOption("3pt_family", "3rd party: family member of EBO"),
                new SelectInputFieldOption("3pt_setebo", "3rd party: Settlor/EBO of legal entity acting as PH"));
    }

    public static List<SelectInputFieldOption> getRiskColorOptions() {

        return List.of(new SelectInputFieldOption("GREEN", "Green"), new SelectInputFieldOption("YELLOW", "Yellow"),
                new SelectInputFieldOption("AMBER", "Amber"), new SelectInputFieldOption("RED", "Red"));
    }

    public static List<SelectInputFieldOption> getCountryOptions() {

        return List.of(new SelectInputFieldOption("FR", "France"), new SelectInputFieldOption("BE", "Belgium"),
                new SelectInputFieldOption("LU", "Luxembourg"));
    }

    public static List<SelectInputFieldOption> getUnderWritingsOptions() {

        return List.of(new SelectInputFieldOption("L0", "L0"), new SelectInputFieldOption("L1", "L1"), new SelectInputFieldOption("L2", "L2"),
                new SelectInputFieldOption("L3", "L3"), new SelectInputFieldOption("L4", "L4"), new SelectInputFieldOption("L5", "L5"),
                new SelectInputFieldOption("L6", "L6"));
    }

    public static List<SelectInputFieldOption> getValuationModelDomain() {

        return List.of(new SelectInputFieldOption("STANDARD", "Standard"), new SelectInputFieldOption("EXTENDED", "Extended"));
    }

    public static List<SelectInputFieldOption> getWhereAreAssetsDomain() {

        return List.of(new SelectInputFieldOption("PREMIUM", "Premium"), new SelectInputFieldOption("FUND", "Fund"),
                new SelectInputFieldOption("ILF", "ILF"));
    }

    public static List<SelectInputFieldOption> getCurrencyDomain() {

        return List.of(new SelectInputFieldOption("AED", "AED"), new SelectInputFieldOption("CRC", "CRC"),
                new SelectInputFieldOption("CZK", "CZK"), new SelectInputFieldOption("DZD", "DZD"), new SelectInputFieldOption("EGP", "EGP"),
                new SelectInputFieldOption("EUR", "EUR"), new SelectInputFieldOption("GBP", "GBP"), new SelectInputFieldOption("MRO", "MRO"),
                new SelectInputFieldOption("NOK", "NOK"), new SelectInputFieldOption("OMR", "OMR"), new SelectInputFieldOption("QAR", "QAR"),
                new SelectInputFieldOption("SAR", "SAR"), new SelectInputFieldOption("SDG", "SDG"), new SelectInputFieldOption("SEK", "SEK"),
                new SelectInputFieldOption("USD", "USD"));
    }

    public static List<SelectInputFieldOption> getSignatureDomain() {

        return List.of(new SelectInputFieldOption("ELECTRONIC", "Electronic"), new SelectInputFieldOption("WET", "Wet"),
                new SelectInputFieldOption("MULTIPLE", "Multiple"));
    }

    public static List<SelectInputFieldOption> getProviderDomain() {

        return List.of(new SelectInputFieldOption("INTERNAL_PROVIDER", "Internal provider"),
                new SelectInputFieldOption("PARTNER_ESIGN_SERVICE", "Partner e-signature service"));
    }

}
