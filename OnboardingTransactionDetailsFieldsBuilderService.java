@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionInvestmentsWebForm implements IConnectWebFormGroup {

    @Valid
    private List<AdditionFasWebForm> specialisedAssuranceFunds;
    @Valid
    private List<AdditionIdfWebForm> internalDedicatedFunds;
    @Valid
    private List<AdditionFdfWebForm> familyDedicatedFunds;
    @Valid
    private AdditionExternalCollectiveFundWebForm externalCollectiveFund;
    @Valid
    private AdditionInternalCollectiveFundWebForm internalCollectiveFunds;
}



public class AdditionInternalFundPositionWebForm implements IConnectWebFormGroup {

    @Valid
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private AdditionInternalFundIdWebForm id;
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private String fundName;
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private String fundMnemonic;
    private String countryOfApplicableLaw; // Contrary to ECF, we don't have this information for ICF
    private String fundStatus; // Contrary to ECF, we don't have this information for ICF (might default to IN_FORCE ?)
    private String fundIssuer; // Contrary to ECF, we don't have this information fo ICF (might default to LIA ?)
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private String lastPriceDate;
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private String lastPriceValue;
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private Integer levelRisk;
    private Integer fundSrri; // Contrary to ECF, we don't have this information for ICF
    private BigDecimal currentEstimatedFundCCY;
    private BigDecimal currentEstimatedFundEUR;
    private BigDecimal estimatedFundCCY;
    private BigDecimal estimatedFundEUR;
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    @Min(value = 0, message = PERCENTAGE_UNDER_0)
    @Max(value = 100, message = PERCENTAGE_OVER_100)
    private BigDecimal percent;
    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    @ReferenceDataExists(domain = DOMAIN_ADDITION_CURRENCY, emptyAllowed = false)
    private String currency;
    private BigDecimal rateChangeFundCcy;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AdditionInternalCollectiveFundIsValid
public class AdditionInternalCollectiveFundWebForm implements IConnectWebFormGroup {

    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private AdditionBasicsWebForm basics;
    @Valid
    private List<AdditionInternalFundWebForm> internalFunds = new ArrayList<AdditionInternalFundWebForm>();
}


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionInternalFundWebForm implements IConnectWebFormGroup {

    @NotNull(message = MANDATORY_FIELD_VALUE_MISSING)
    private AdditionBasicsWebForm basics;
    private String fundsGroupId;
    private String fundName;
    private String productComponentNumber;
    @Valid
    private AdditionInternalFundPositionWebForm internalFundPosition;

}



