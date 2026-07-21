Camunda - Change Client Information - Assign task to PCS Germany or PSA

⸻

Context

As part of the Change of Client Information (CCI) workflow, the new Change of Client Information task must be routed to the appropriate operational team.

The assignment depends on the target market of the broker linked to the policy.

⸻

Description

The Change of Client Information task must be assigned according to the following business rule:

* If the target market of the policy broker is DE, the task must be assigned to the PCS Germany team.
* Otherwise, the task must be assigned to the PSA - Policy Servicing Administration team.

The routing must be performed when the Camunda User Task is created.

The target market used for the routing must be retrieved from the policy/broker information available in the case or from the relevant backend service.

⸻

CoAs

* When the broker target market is DE, the Change of Client Information task is assigned to the PCS Germany team.
* When the broker target market is different from DE, the task is assigned to the PSA team.
* When the broker target market is missing or cannot be resolved, the task is assigned to the configured default team.
* The routing result is persisted and available in the task information.
* The assigned team is authorized to claim and process the task.
* A user who is not part of the assigned team cannot claim or complete the task.
* The routing is executed only once when the task is created, unless the task is explicitly reassigned.
* Routing failures are logged with the case business identifier and the policy number.

⸻

Dev Notes

* The routing logic must be implemented in the CCI worker or through the existing user-task assignment mechanism.
* The assignment must be triggered during the creation of the Camunda User Task.
* The routing configuration must not be hardcoded.
* The configuration should define at least:

routing:
  change-client-information:
    target-markets:
      DE: PCS_GERMANY
    default-group: PSA

 * The implementation should reuse the existing assignment/routing library if one already exists in the Case Management platform.
* The target market must preferably be read from an existing process variable or case data. An additional CLASS call should only be introduced if the information is not already available.
* The assigned group must be stored using the technical group identifier expected by the authorization system.
* Technical errors must follow the standard Camunda retry and incident-management mechanism.

⸻

Dependencies

* Creation of the Change of Client Information Camunda User Task.
* Availability of the broker target market.
* Existing user-task assignment and authorization mechanism.
* Availability of PCS Germany and PSA group identifiers.   
