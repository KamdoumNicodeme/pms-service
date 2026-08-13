[Client Profiling Frontend] - Change Client Information - Complete task with Sign Off Assignment

Context

As part of the Change of Client Information (CCI) workflow, once the user has reviewed and updated the client information, the task must be completed.

Before completing the “Change of Client Information” task, the user must select the assignee responsible for the following CCI Sign Off task.

Description

When the user clicks on the “Complete” button of the “Change of Client Information” task, the frontend shall display a Sign Off Assignment pop-up.

The pop-up shall allow the user to select the assignee responsible for the next CCI Sign Off task.

The pop-up contains:

* Sign Off Assignment
    * Selection of a Physical assignee / Team assignee
* Cancel button
* Complete button

Cancel

When the user clicks Cancel:

* The pop-up is closed.
* The current Change of Client Information task remains open.
* No task completion request is sent.

Complete

When the user clicks Complete:

* The selected Sign Off assignment is sent as part of the task completion request.
* The current Change of Client Information task is completed.
* The workflow can continue to the CCI Sign off task.

The assignment selected in this pop-up will be used by the workflow/backend to assign the following CCI Sign Off task.

CoAs

* Clicking Complete on the Change of Client Information screen opens the Sign Off Assignment pop-up.
* The user can select the required Physical/Team assignee.
* Clicking Cancel closes the pop-up without completing the task.
* Clicking Complete sends the selected Sign Off assignment.
* The Change of Client Information task is completed only after confirmation from the pop-up.
* The selected assignment is made available for the following CCI Sign off task.
* Error handling is displayed if task completion fails.
