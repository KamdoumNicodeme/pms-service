import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientProfilingInitialBusinessData {

    private Policy policy;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Policy {

        private String policyNumber;
        private String type;
        private String currency;

        private String broker;
        private String brokerName;
        private String brokerTargetMarket;

        private String countryOfLaw;
        private String countryOfBusinessOrigin;
        private String taxCountry;

        private Amount latestNetAssetValue;

        private String internalLevelRiskScoring;
        private Boolean taxComplianceCertificateReceived;

        private Correspondence correspondence;
        private CommunicationPreferences communicationPreferences;
        private PolicyOptInOut optInOut;

        private List<Pledge> pledges;
        private List<ThirdParty> clients;
        private List<PolicyPenalty> policyPenalties;
        private List<ProductComponentPenalty> productComponentPenalties;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Correspondence {

        private String street;
        private String number;
        private String houseName;
        private String apartmentNumber;

        private String city;
        private String postcode;
        private String county;
        private String area;

        private String country;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommunicationPreferences {

        private String language;
        private Boolean consentToReceiveElectronicCommunication;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PolicyOptInOut {

        private String policyType;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Pledge {

        private String pledgeBankName;
        private Instant pledgeReceivedDate;
        private String pledgeInFavourOf;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ThirdParty {

        // Common fields
        private String type;
        private String thirdPartyId;

        private String aeoiStatus;
        private String fatcaStatus;

        private List<String> roleTypes;

        // General tax information
        private List<TaxInformation> taxInformation;

        // Contact information
        private ContactDetails contactDetails;

        // Identity
        private List<IdentityDocument> identityDocuments;

        // Controlling persons, mainly for moral persons
        private List<ThirdParty> controllingPersons;

        // Moral person specific fields
        private String name;
        private String companyLegalForm;
        private LocalDate dateOfRegistration;
        private String commercialRegisterNumber;
        private String countryOfIncorporation;
        private String companyPurpose;

        // Physical person specific fields
        private String title;
        private String lastName;
        private String firstName;
        private LocalDate birthDate;
        private String countryOfBirth;
        private List<String> nationalities;
        private String gender;
        private String maritalStatus;

        private String profession;
        private String professionStatus;
        private String employerName;
        private String employerCountry;
        private String industrySector;

        // Specific information used for controlling person
        private String trusteeIfMoralPerson;

        // Legacy / compatibility fields already present
        private String taxCountry;
        private String taxNumber;
        private String countryOfResidence;
        private Instant idDocumentExpirationDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContactDetails {

        private Address address;

        private List<String> mobileNumbers;
        private List<String> emails;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {

        private String street;
        private String number;
        private String houseName;
        private String apartmentNumber;

        private String city;
        private String postcode;
        private String county;
        private String area;

        private String country;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IdentityDocument {

        private String documentId;
        private String idType;
        private String idNumber;
        private LocalDate expirationDate;

        private Boolean approved;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TaxInformation {

        private String taxCountry;
        private String tin;
        private String reasonIfTinUnavailable;

        private Boolean usPerson;

        private String aeoiStatus;
        private String fatcaStatus;
        private String crsStatus;
        private String giin;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PolicyPenalty {

        private Instant startDate;
        private Instant endDate;
        private String type;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductComponentPenalty {

        private String productComponentNumber;
        private String fundNumber;

        private Instant startDate;
        private Instant endDate;

        private String type;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Amount {

        private String currency;
        private Double value;
    }
}
