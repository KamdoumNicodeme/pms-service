Context

As part of the Change of Client Information (CCI) workflow, the Policy Servicing Administrator (PSA) or PCS user must be able to review and update the client information submitted during the Digital Fact Find.

The user input must be persisted during the execution of the task and be available until the task is completed.

The CCI worker shall expose REST APIs allowing the frontend to retrieve, update and complete the user task.

⸻

Description

The user edits the information displayed on the right section of the Change of Client Information screen.

The worker shall expose APIs allowing the frontend to:

* retrieve the current user input;
* save the current modifications;
* complete the Change of Client Information task.

The user input is stored in the CCI worker data and will later be used to update CLASS.

The following sections are concerned:

* Policy Correspondence
* Physical Person
* Legal Entity
* Controlling Persons
* Contact Details
* Identity Documents
* Tax Information
* Communication Preferences
* Opt In / Out
