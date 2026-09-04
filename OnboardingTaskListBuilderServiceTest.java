@Slf4j
@Service
@RequiredArgsConstructor
public class ClientProfilingAssignService {

    private final InternalDistributionPartnerService internalDistributionPartnerService;
    private final TargetMarketTeamConfigProperties targetMarketTeamConfigProperties;
    private final TeamService teamService;

    public void determineInitialCaseAssignment(final CaseMetadata caseMetadata)
            throws DistributionPartnerNotFoundException {

        // Retrieve the policy number
        String policyNumber = Optional.ofNullable(
                        caseMetadata.getSubjects().get("policy")
                )
                .filter(policies -> !policies.isEmpty())
                .flatMap(policies -> policies.stream().findFirst())
                .orElseThrow(() ->
                        new IllegalStateException(
                                String.format(
                                        "Policy number is not provided in subject of case '%s'",
                                        caseMetadata.getCaseBusinessIdentifier()
                                )
                        )
                );

        // Retrieve the broker linked to the policy
        DistributionPartner broker =
                internalDistributionPartnerService.getBrokerOfPolicy(policyNumber);

        // Retrieve the team associated with the broker target market
        Set<String> teams =
                targetMarketTeamConfigProperties.getTeamsOfTargetMarket(
                        broker.getTargetMarket()
                );

        if (CollectionUtils.isEmpty(teams)) {
            throw new IllegalStateException(
                    String.format(
                            "No team configured for target market '%s'",
                            broker.getTargetMarket()
                    )
            );
        }

        // CCI expects a single operational team
        String ownerTeam = teams.iterator().next();

        caseMetadata.setOwnerTeam(ownerTeam);

        log.info(
                "CCI case '{}' assigned to team '{}' for broker target market '{}'",
                caseMetadata.getCaseBusinessIdentifier(),
                ownerTeam,
                broker.getTargetMarket()
        );

        // If the case initiator belongs to the assigned team,
        // assign the case directly to the initiator
        Set<String> initiatorTeams =
                teamService.getUserTeams(caseMetadata.getInitiator());

        if (initiatorTeams.contains(ownerTeam)) {
            caseMetadata.setOwner(caseMetadata.getInitiator());

            log.info(
                    "CCI case '{}' assigned directly to initiator '{}'",
                    caseMetadata.getCaseBusinessIdentifier(),
                    caseMetadata.getInitiator()
            );
        }
    }
}
