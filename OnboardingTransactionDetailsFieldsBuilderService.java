  Context

As part of the Change of Client Information (CCI) workflow, the assigned user must be able to retrieve all the information required to review the client information before performing any modification.

The worker shall expose a REST API returning both the current information retrieved from CLASS and the editable information stored for the current task.

⸻

Description

The worker shall expose a REST API allowing the frontend to retrieve the complete Change Client Information task.

The API shall return all information required to render the task, including:

* Case information
* Policy information
* Current Policy Correspondence
* Current Policy Holders
    * Physical Person
    * Legal Entity
* Current Controlling Persons
* Current Tax Information
* Current Identity Documents
* Communication Preferences
* Current user input (if already saved)
