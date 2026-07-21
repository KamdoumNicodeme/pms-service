Context

During the Change Client Information task, users may progressively modify the editable information before completing the task.

The worker shall persist these modifications so that they remain available if the task is reopened.

⸻

Description

The worker shall expose REST APIs allowing the frontend to persist the current user input.

Only editable fields shall be stored.

The worker shall update the current task data without completing the workflow.

The stored information will later be used to update CLASS.

⸻

CoAs

* REST API allows updating the current task.

    PUT /cases/{caseBusinessIdentifier}/tasks/change-client-information

    * Only editable fields are updated.
* Read-only information cannot be modified.
* Existing user input is overwritten by the new values.
* The task remains active after the update.
* The updated values are available through the retrieval API.
* The backend verifies that the task is assigned to the current user.






    Worker - Change Client Information - Provide API to complete the task

⸻

Context

Once the user has reviewed and updated the client information, the task must be completed in order to continue the Camunda workflow.

Before completing the task, the worker must validate the submitted information.

⸻

Description

The worker shall expose a REST API allowing the frontend to complete the Change Client Information task.

The API shall:

* validate the submitted information;
* persist the latest modifications;
* complete the Camunda User Task;
* trigger the continuation of the workflow.

Updating CLASS is not part of this story and is handled by the following service tasks in the workflow.

⸻

CoAs

* REST API allows completing the task.

    POST /cases/{caseBusinessIdentifier}/tasks/change-client-information/complete

    * The backend validates the submitted data before completing the task.
* The backend verifies that the task is assigned to the current user.
* The latest user input is persisted before completing the task.
* The Camunda User Task is completed.
* The workflow continues to the next BPMN step.
* Validation errors are returned to the frontend if completion is rejected.
