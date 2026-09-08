@Slf4j
@Service
@RequiredArgsConstructor
public class ClientProfilingAssignService {

    private final InternalDistributionPartnerService internalDistributionPartnerService;
    private final TargetMarketTeamConfigProperties targetMarketTeamConfigProperties;
    private final TeamService teamService;

    public void determineInitialCaseAssignment(final CaseMetadata caseMetadata)
            throws DistributionPartnerNotFoundException {

        Set<String> teams = getTeams(caseMetadata);

        if (CollectionUtils.isEmpty(teams)) {
            throw new IllegalStateException("No team configured for CCI case");
        }

        // CCI expects a single operational team
        String ownerTeam = teams.iterator().next();

        caseMetadata.setOwnerTeam(ownerTeam);

        log.info(
                "CCI case '{}' assigned to team '{}'",
                caseMetadata.getCaseBusinessIdentifier(),
                ownerTeam
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

    public Assignment determineChangeClientInformationAssignment(
            final CaseMetadata caseMetadataByProcessId) {

        String initiator = caseMetadataByProcessId.getInitiator();

        Set<String> teams = getTeams(caseMetadataByProcessId);

        // Note that an empty string must be used for a user task
        // which is left unassigned
        String assignee;

        if (CollectionUtils.containsAny(
                teamService.getUserTeams(initiator),
                teams)) {

            assignee = initiator;

        } else if (CollectionUtils.containsAny(
                teamService.getUserTeams(caseMetadataByProcessId.getOwner()),
                teams)) {

            assignee = caseMetadataByProcessId.getOwner();

        } else {
            assignee = StringUtils.EMPTY;
        }

        return Assignment.builder()
                .assignee(assignee)
                .candidateGroups(teams)
                .build();
    }

    private Set<String> getTeams(final CaseMetadata caseMetadata) {

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

        // Find the broker linked to the policy
        DistributionPartner broker =
                internalDistributionPartnerService.getBrokerOfPolicy(policyNumber);

        // Find the teams associated to the target market of the broker
        return targetMarketTeamConfigProperties.getTeamsOfTargetMarket(
                broker.getTargetMarket()
        );
    }
}
