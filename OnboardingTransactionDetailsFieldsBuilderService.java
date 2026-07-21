Camunda - Change Client Information - Add “Change of Client Information” task

⸻

Context

As part of the replacement of the Case Management Tool (CMT), the Change of Client Information (CCI) workflow must be implemented on the new Camunda platform.

A dedicated Change of Client Information User Task must be introduced into the workflow to allow PSA or PCS users to review and update the client information before continuing the process.

⸻

Description

Update the Camunda BPMN model to introduce the Change of Client Information User Task.

The task shall be created after the case has been initialized and the required client information has been loaded.

The task will allow users to:

* review the current information retrieved from CLASS;
* compare it with the information submitted during the Digital Fact Find;
* update editable information;
* complete the task to continue the workflow.

The assignment of the task is handled in a dedicated story.

The implementation of the task APIs is handled in dedicated Worker stories.

The update of CLASS is handled in dedicated backend stories.

⸻

CoAs

* A Change of Client Information User Task is added to the Camunda workflow.
* A process instance reaching this step creates the corresponding User Task.
* The task is linked to the dedicated CLIP screen.
* The task can only be completed through the dedicated Worker API.
* Completing the task continues the workflow to the next BPMN step.
* The task is configured with the expected process variables.
* The BPMN model is successfully deployed.
