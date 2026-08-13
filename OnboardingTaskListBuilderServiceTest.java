# JIRA Backlog --- CLIP Document Management / Change Client Information

## Ticket 1 --- Backend: Provide Case Documents

**Summary**\
`[CLIP BACKEND] - Document Management - Provide case documents`

### Context

As part of the Client Profiling Application, a new **Documents** tab
must be available for CLIP cases and tasks.

The tab must provide all documents linked to the current case, including
documents originating from **Connect Servicing** and documents created
or uploaded directly from **CLIP**.

The document list currently available in the **360 view** must no longer
be displayed there and will be exposed through the dedicated
**Documents** tab.

### Description

The CLIP backend shall expose the documents associated with a case.

Documents may originate from:

-   Connect
-   CLIP

For each document, the backend shall provide at least:

  -----------------------------------------------------------------------
  Field                               Description
  ----------------------------------- -----------------------------------
  Filename                            Filename coming from Connect or
                                      CLIP

  Document Type                       Type of the document

  Metadata                            Metadata associated with the
                                      document

  Source                              `Connect` or `CLIP`

  Status                              Empty, `Validated`, `Rejected` or
                                      `Replaced`

  Document identifier                 Identifier required to execute
                                      document actions
  -----------------------------------------------------------------------

Metadata must allow association of the document with the corresponding
business entity.

For Third Party related documents, the **ThirdParty identifier** must be
provided.

Metadata shall be ordered with the Third Party identifier first when
applicable, followed by the other metadata in alphabetical order.

### CoAs

-   All documents associated with the case can be retrieved.
-   Documents originating from Connect are returned.
-   Documents originating from CLIP are returned.
-   Source allows differentiation between `Connect` and `CLIP`.
-   Document type is provided.
-   Filename is provided.
-   Metadata is provided.
-   ThirdParty identifier is available for Third Party related
    documents.
-   Current document status is returned.
-   The API provides the technical identifier necessary for subsequent
    document actions.

------------------------------------------------------------------------

## Ticket 2 --- Backend: Manage Documents

**Summary**\
`[CLIP BACKEND] - Document Management - Provide APIs to manage documents`

### Context

The new Documents panel allows users to manage the documents associated
with a CLIP case.

The backend must provide the operations required by the frontend to
download, create, update, replace, validate, reject and delete
documents.

### Description

The CLIP backend shall provide the APIs required to manage documents
from the Documents tab.

#### Download

The user can download an existing document.

#### Add

The user can upload a new document and provide:

-   File
-   Document type
-   Filename
-   Metadata according to the selected document type

The newly created document has `CLIP` as source.

#### Edit

The user can update:

-   Filename
-   Document type
-   Metadata

If no new file is provided, only document information is updated.

If a new file is provided:

-   the existing file is replaced;
-   the document information is updated;
-   status becomes `Replaced`;
-   source becomes `CLIP`.

#### Validate

The document status becomes `Validated`.

#### Reject

The document status becomes `Rejected`.

#### Delete

The document and its corresponding data are deleted.

The list of available metadata shall depend on the document type and
follow the same applicable rules as DMA.

### CoAs

-   Existing documents can be downloaded.
-   A new document can be uploaded.
-   A newly uploaded document has `Source = CLIP`.
-   Filename, document type and metadata can be edited.
-   Editing data without providing a new file does not replace the
    binary file.
-   Uploading a replacement file replaces the previous file.
-   Replacing a document sets its status to `Replaced`.
-   Replacing a Connect document changes its source to `CLIP`.
-   A document can be set to `Validated`.
-   A document can be set to `Rejected`.
-   A document can be deleted.
-   Available metadata depend on the selected document type.

------------------------------------------------------------------------

## Ticket 3 --- Frontend: Add Documents Tab

**Summary**\
`[Client Profiling Frontend] - Document Management - Add Documents tab`

### Context

A dedicated **Documents** tab must be introduced in the Client Profiling
Application.

The tab must be accessible from CLIP cases and tasks and centralize the
documents linked to the current case.

Following the updated business requirement, the main navigation of the
CCI screen is organized as:

-   1 tab per Policy Holder
-   1 tab for Policy
-   1 tab for Documents

### Description

The frontend shall add a new tab named **Documents** on the right side
of the existing business tabs.

Example:

`PH 1 - XXX | PH 2 - YYY | POLICY | DOCUMENTS`

When the user opens the Documents tab, all documents associated with the
case shall be displayed.

The table shall contain:

  Column          Content
  --------------- -----------------------------------------
  Filename        Document filename
  Document type   Type of document
  Metadata        Associated metadata
  Source          Connect / CLIP
  Status          Empty / Validated / Rejected / Replaced
  Actions         Available document actions

Available actions:

-   Download
-   Edit
-   Reject
-   Validate
-   Delete

An **Add new documents** button shall allow the user to upload a new
document.

The upload form shall provide:

-   File selection
-   Document type
-   Filename
-   Metadata depending on the selected document type

The existing list of documents in the **360 view must no longer be
displayed**.

### CoAs

-   Documents tab is displayed for CLIP cases/tasks.
-   Documents tab is positioned after the Policy tab.
-   All case documents are displayed.
-   Connect documents are displayed.
-   CLIP documents are displayed.
-   Filename, document type, metadata, source and status are correctly
    displayed.
-   User can download a document.
-   User can edit a document.
-   User can replace a document.
-   User can validate a document.
-   User can reject a document.
-   User can delete a document.
-   User can add a new document.
-   Available metadata depend on document type.
-   Documents are no longer displayed in the 360 view.

------------------------------------------------------------------------

## Ticket 4 --- Frontend CCI: Display Third Party Documents Next to Client Data

**Summary**\
`[Client Profiling Frontend] - Change Client Information - Display Third Party documents next to client data`

### Context

In addition to the centralized Documents tab, the Change of Client
Information task requires specific documents linked to a Third Party to
be displayed next to the corresponding client information.

The association between the information and the document must rely on
the Third Party metadata/identifier.

### Description

Within the **Change of Client Information** task, documents associated
with a Third Party must be accessible from the corresponding client
information sections.

For each Policy Holder / Third Party, the following document
associations shall be supported.

#### Contact Details

-   Proof of residence

#### Identity Documents

-   ID document

#### Tax Information

-   AEOI Self Certification
-   W9

The frontend shall use the ThirdParty identifier/metadata to associate
each document with the correct Policy Holder.

Documents displayed next to the corresponding information shall be
downloadable.

This requirement complements the existing CCI screens: the business data
(ID Type, ID Number, Tax Country, TIN, etc.) remain part of the Policy
Holder information while the corresponding binary documents are managed
through Document Management.

### CoAs

-   Proof of residence is associated with the correct Third Party
    Contact Details.
-   ID document is associated with the correct Third Party Identity
    Document section.
-   AEOI Self Certification is associated with the correct Third Party
    Tax Information.
-   W9 is associated with the correct Third Party Tax Information.
-   Documents are associated using the correct ThirdParty identifier.
-   With multiple Policy Holders, a document is never displayed against
    the wrong Third Party.
-   The associated document can be downloaded from the corresponding
    section.

------------------------------------------------------------------------

## Ticket 5 --- Backend / Business Rule: Block Completion on Rejected Document

**Summary**\
`[CLIP BACKEND] - Document Management - Prevent task completion when a document is rejected`

### Context

During document review, users can validate or reject documents.

A rejected document represents an unresolved issue and the Change of
Client Information task must therefore not be completed while at least
one associated document remains rejected.

### Description

The **Change of Client Information** task must not be completed while at
least one document associated with the case has the status `Rejected`.

A document does not have to be explicitly validated in order to complete
the task.

Expected behavior:

  Document situation                    Task completion
  ------------------------------------- -----------------
  At least one `Rejected` document      Blocked
  All documents `Validated`             Allowed
  Documents without validation status   Allowed
  No `Rejected` document                Allowed

The rule must be enforced server-side and must not rely only on frontend
validation.

The frontend shall receive sufficient information to inform the user
when completion is rejected because of a rejected document.

### CoAs

-   Task cannot be completed when at least one document is `Rejected`.
-   Task can be completed when no document is `Rejected`.
-   It is not mandatory to validate every document before completing the
    task.
-   The completion rule cannot be bypassed through a direct API
    invocation.
-   The frontend can display an appropriate message when completion is
    refused.

------------------------------------------------------------------------

icket 6 — Camunda - CCI - Add CCI Sign Off task

Summary
[Camunda] - Change Client Information - Add CCI Sign Off task

Context

As part of the Change of Client Information (CCI) workflow, the information entered and reviewed during the “Change of Client Information” task must go through a Sign Off validation before being propagated to the downstream systems.

The CCI workflow is orchestrated by Camunda.

Description

In Camunda Modeler, adapt the Change of Client Information workflow in order to add the “CCI Sign off” user task after the “Change of Client Information” task.

The workflow must follow the defined business process:

Change of Client Information → CCI Sign off → Sign off OK?

The Sign Off task allows the assigned user/team to review and approve or reject the changes made during the Change of Client Information task.

Depending on the Sign Off decision:

* Sign off OK = Yes → continue the workflow to Integration with DMA.
* Sign off OK = No → return to the Change of Client Information task for correction/review.

The assignment information selected when completing the Change of Client Information task must be used to determine the assignee/team of the CCI Sign Off task.

CoAs

* The CCI Sign off User Task exists in the CCI BPMN.
* It is created after completion of Change of Client Information.
* The task is assigned according to the Sign Off assignment selected during the previous task.
* A Sign off OK? decision/gateway is evaluated after completion of the Sign Off task.
* When Sign Off is approved, the process continues to Integration with DMA.
* When Sign Off is rejected, the process returns to Change of Client Information.
* The BPMN is valid and deployable on the project’s Camunda environment.

Il y a cependant une distinction importante avec ce que je t’avais proposé avant : la popup “Sign Off Assignment” et la User Task “CCI Sign off” ne devraient pas forcément être dans le même ticket.

Je garderais donc Ticket 6 = création/intégration de la User Task CCI Sign Off dans Camunda, et la gestion de la popup/assignment côté frontend peut rester dans un ticket Front séparé si elle n’est pas déjà couverte.

Et ton workflow révèle également qu’il faudra probablement prévoir plus tard des tickets séparés pour Integration with DMA, Update CLASS fields, et éventuellement la logique Manual action to do?, mais ils ne font pas partie du ticket 6.

------------------------------------------------------------------------

# Impact on Existing DEV-21917

**DEV-21917 ---
`[Client Profiling Frontend] - Change Client Information - Provide Current Identity document & Tax Information`
should not be replaced by the Documents ticket.**

DEV-21917 remains responsible for displaying/comparing the business
information associated with the Policy Holder, such as:

### Identity Document data

-   ID Type
-   ID Number
-   ID Expiration Date

### Tax Information data

-   Tax Country
-   TIN
-   Reason if TIN unavailable
-   US Person?
-   Other applicable tax information

The new Document Management requirement adds the associated binary
documents:

-   ID document
-   Proof of residence
-   AEOI Self Certification
-   W9

Therefore:

`DEV-21917 = business/client data`

`Document Management tickets = associated files/documents`

The Documents tab centralizes all case documents, while relevant Third
Party documents can additionally be displayed contextually next to the
corresponding Policy Holder information.
