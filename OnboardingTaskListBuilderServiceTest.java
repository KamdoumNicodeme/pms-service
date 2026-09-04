@Slf4j
@Service
@RequiredArgsConstructor
public class ClientProfilingAssignService {

    private static final String TARGET_MARKET_DE = "DE";
    private static final String PCS_GERMANY = "PCS_GERMANY";
    private static final String DEFAULT_TEAM = "PSA";

    public void determineInitialCaseOwnerTeam(
            final ClientProfilingCaseData caseMetadata)
            throws PolicyNotFoundException {

        String brokerTargetMarket = Optional.ofNullable(caseMetadata)
                .map(ClientProfilingCaseData::getInitialBusinessData)
                .map(ClientProfilingInitialBusinessData::getPolicy)
                .map(ClientProfilingInitialBusinessData.Policy::getBrokerTargetMarket)
                .filter(targetMarket -> !targetMarket.isBlank())
                .orElse(null);

        String ownerTeam = determineOwnerTeam(brokerTargetMarket);

        caseMetadata.setOwnerTeam(ownerTeam);

        log.info(
                "CCI owner team determined: caseBusinessIdentifier='{}', " +
                "brokerTargetMarket='{}', ownerTeam='{}'",
                caseMetadata.getCaseBusinessIdentifier(),
                brokerTargetMarket,
                ownerTeam
        );
    }

    private String determineOwnerTeam(final String brokerTargetMarket) {
        if (TARGET_MARKET_DE.equalsIgnoreCase(brokerTargetMarket)) {
            return PCS_GERMANY;
        }

        return DEFAULT_TEAM;
    }
}
