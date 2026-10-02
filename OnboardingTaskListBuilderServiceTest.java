# REQ-5719 --- WP4 --- Changes following analysis of the data returned in CLASS

## Backlog --- Change of Client Information (CCI)

This backlog covers the changes identified following the analysis of the
data returned by CLASS for the Client Profiling servicing flow in
Connect and the **Change of Client Information** task in CLIP.

The implementation is split into four Jira tickets:

1.  Add PO Box field to addresses
2.  Add Obtaining Date to Nationalities
3.  Manage Italian Birth Town Code
4.  Add and validate Primary TIN

------------------------------------------------------------------------

# Ticket 1 --- CCI -- Add PO Box field to addresses

## Summary

`[CCI] Add PO Box field to Third Party and Policy addresses`

## Description

As a **CO user**,\
I want to view and manage the **PO Box** of an address in the Change of
Client Information task,\
so that the complete address can be returned and correctly injected into
CLASS.

The `PO Box` field is currently missing from the address information
handled in Client Profiling.

The field must be added below **House name** for:

-   Residential address of Physical Person
-   Residential address of Moral Person
-   Residential address of Controlling Person
-   Policy → Correspondence → Sending address

The value returned by CLASS must be used as the Core value when
available.

Any value retained or modified by the user must be reflected in
`changeClientInformation`.

## Acceptance Criteria

### AC1 --- Third Party address

Given a Third Party has a residential address\
When the address is displayed in the CCI task\
Then the **PO Box** field is displayed below **House name**.

### AC2 --- Core value

Given a PO Box value exists in CLASS\
When the CCI task is opened\
Then the value is displayed as the **Core value**.

### AC3 --- Third Party types

Given the Third Party is a Physical Person, Moral Person or Controlling
Person\
When the residential address is displayed\
Then the PO Box field is available.

### AC4 --- Policy correspondence address

Given the Policy has a correspondence sending address\
When the Policy correspondence section is displayed\
Then the PO Box field is displayed below **House name**.

### AC5 --- Retained value

Given the user retains or modifies the PO Box\
When the change is applied\
Then the selected value is stored in `changeClientInformation`.

## Technical Notes

Update the address model used by the CCI flow to support:

``` ts
poBox?: string;
```

Update:

-   CLASS/Core mapping
-   Frontend interfaces
-   Comparison field construction
-   `changeClientInformation` mapping
-   Address display/edit components
-   Unit tests

------------------------------------------------------------------------

# Ticket 2 --- CCI -- Add Obtaining Date to Nationalities

## Summary

`[CCI] Add Obtaining Date for each Nationality`

## Description

As a **CO user**,\
I want to view and manage the **Obtaining date** associated with each
nationality,\
so that complete nationality information can be returned to CLASS.

Like in Digital Onboarding, each nationality must contain its
**Obtaining date**.

The nationality structure must therefore contain both the country and
its associated date.

Example:

``` ts
interface NationalityInfo {
  country: string;
  fromDate?: string;
}
```

The Obtaining date must be displayed for existing nationalities and must
also be provided when a nationality is manually added.

The date must remain associated with the correct nationality during:

-   Add
-   Edit
-   Delete
-   Apply
-   Save/reload

The current maximum of **3 nationalities** remains unchanged.

## Acceptance Criteria

### AC1 --- Display existing date

Given a nationality returned by CLASS contains an obtaining date\
When the Nationalities block is displayed\
Then the obtaining date is displayed with the corresponding nationality.

### AC2 --- Add nationality

Given the user adds a new nationality\
When the add nationality form is opened\
Then both **Nationality** and **Obtaining date** can be entered.

### AC3 --- Persist manual nationality

Given a nationality is manually added\
When the change is applied\
Then both country and obtaining date are stored in
`changeClientInformation`.

### AC4 --- Edit nationality

Given several nationalities exist\
When one nationality is edited\
Then its obtaining date remains associated with that nationality.

### AC5 --- Remove nationality

Given a nationality is removed\
When the change is applied\
Then the nationality and its obtaining date are removed together.

### AC6 --- Maximum nationalities

Given the Third Party already has 3 nationalities\
Then another nationality cannot be added.

### AC7 --- Preserve original data

Given nationality information is modified\
Then the original source data remains unchanged\
And only `changeClientInformation` contains the retained modifications.

## Technical Notes

The existing nationality handling must manipulate the complete
`NationalityInfo`, not only the country.

For example:

``` ts
interface NationalityInfo {
  country: string;
  fromDate?: string;
}
```

The following flows must be reviewed:

-   `applyNationalityChange`
-   `applyManualNationalityChange`
-   `removeNationality`
-   Manual nationality creation
-   Comparison list rendering
-   `changeClientInformation` update
-   Mapping to backend DTO

The nationality index (`first`, `second`, `third`) must remain
consistent after add/remove operations.

------------------------------------------------------------------------

# Ticket 3 --- CCI -- Manage Italian Birth Town Code

## Summary

`[CCI] Display and manage Italian Birth Town Code`

## Description

As a **CO user**,\
I want to manage the **Italian birth town code** when the Third Party's
most recent nationality is Italian,\
so that the mandatory information expected by CLASS can be provided.

The most recent nationality must be determined using the **Obtaining
date**.

When the most recent nationality is Italian, the field:

**Italian birth town code**

must be displayed directly below **Country of birth** in General
Information.

If another nationality has the same Obtaining date as the Italian
nationality, the field must also be displayed.

The field is mandatory when applicable.

The Client/Broker does not provide this value. Therefore, unlike
standard comparison fields, **KEEP CLIENT VALUE must not be available**.

The available choices must be:

1.  **TAKE CORE VALUE**
2.  **TYPE ANOTHER VALUE**

The **TYPE ANOTHER VALUE** option must provide a searchable dropdown
containing Italian birth town codes.

There are approximately 10,000 codes. The application must not display
the complete list at once. The search result must be limited to **30
values**.

## Acceptance Criteria

### AC1 --- Determine most recent nationality

Given the Third Party has multiple nationalities\
When the CCI task is displayed\
Then the most recent nationality is determined using the Obtaining date.

### AC2 --- Italian nationality

Given the most recent nationality is Italian\
When General Information is displayed\
Then **Italian birth town code** is displayed below **Country of
birth**.

### AC3 --- Same obtaining date

Given Italian nationality has the same obtaining date as another
nationality\
Then Italian birth town code is displayed.

### AC4 --- Non-Italian nationality

Given the most recent nationality is not Italian\
And no Italian nationality shares the most recent obtaining date\
Then Italian birth town code is not displayed.

### AC5 --- Mandatory field

Given Italian birth town code is displayed\
Then the field is mandatory.

### AC6 --- Core value

Given a Core value exists in CLASS\
Then **TAKE CORE VALUE** displays the value returned by CLASS.

### AC7 --- No client value

Given the Italian birth town code field is opened\
Then **KEEP CLIENT VALUE** is not available.

### AC8 --- Another value

Given the user selects **TYPE ANOTHER VALUE**\
Then a searchable dropdown of Italian birth town codes is available.

### AC9 --- Search limit

Given the user types characters in the search\
Then a maximum of 30 matching codes is displayed.

### AC10 --- Completion validation

Given Italian birth town code is mandatory\
And no value has been selected\
When the user tries to complete the task\
Then task completion is prevented.

### AC11 --- Persist selected value

Given a value has been retained\
Then the selected Italian birth town code is stored in
`changeClientInformation`.

## Technical Notes

The Italian birth town code depends on the nationality Obtaining Date
introduced by Ticket 2.

The comparison field must support a configuration where the Client value
is not available:

``` text
TAKE CORE VALUE
TYPE ANOTHER VALUE
```

The standard:

``` text
KEEP CLIENT VALUE
```

option must not be rendered for this field.

Because the reference list contains approximately 10,000 codes, prefer a
backend/reference-data search rather than loading all values into the
browser.

Example API contract:

``` http
GET /italian-birth-town-codes?search=VA&limit=30
```

The frontend must:

-   Start searching when the user types
-   Display at most 30 results
-   Allow selection of one code
-   Store the retained code in `changeClientInformation`
-   Validate the mandatory rule before task completion

------------------------------------------------------------------------

# Ticket 4 --- CCI -- Add and validate Primary TIN

## Summary

`[CCI] Add and validate Primary TIN`

## Description

As a **CO user**,\
I want to identify the **Primary TIN** of a Third Party,\
so that CLASS knows which TIN must be used as the default TIN.

A new field:

**Primary TIN?**

must be added to each TIN entry.

At the **first creation of the Change of Client Information task**, the
application must automatically set `Primary TIN = Yes` when:

``` text
TIN Tax Country = Third Party Country of Residence
```

This automatic initialization must happen **only once**, during initial
task creation.

If the task is subsequently reopened after a rejection/sign-off, the
automatic rule must **not** run again. The previously retained user
decision must be preserved.

If no TIN has a Tax Country matching the Country of Residence, no TIN is
automatically selected.

If several TINs match the Country of Residence, they are initially
marked according to the requirement. However, the user must resolve the
selection before completing the task because CLASS accepts only one
Primary TIN.

## Acceptance Criteria

### AC1 --- Display Primary TIN

Given a Third Party has Tax Information\
When the TIN block is displayed\
Then **Primary TIN?** is available for each TIN.

### AC2 --- Automatic initialization

Given the CCI task is created for the first time\
And a TIN Tax Country equals the Third Party Country of Residence\
Then this TIN is automatically set as `Primary TIN = Yes`.

### AC3 --- No matching TIN

Given no TIN Tax Country equals the Country of Residence\
When the CCI task is created\
Then no Primary TIN is automatically selected.

### AC4 --- Multiple matching TINs

Given several TINs have Tax Country equal to Country of Residence\
When the task is initially created\
Then the automatic initialization is applied according to the business
rule\
And the user must resolve the selection before task completion.

### AC5 --- Reopened task

Given the task has already been created\
And is reopened after rejection/sign-off\
Then the automatic Primary TIN initialization is not executed again.

### AC6 --- No Primary TIN

Given no TIN is marked as Primary TIN\
When the user tries to complete the task\
Then task completion is blocked\
And the following message is displayed:

``` text
Please select one primary TIN
```

### AC7 --- Multiple Primary TINs

Given more than one TIN is marked as Primary TIN\
When the user tries to complete the task\
Then task completion is blocked\
And the following message is displayed:

``` text
Please select only one primary TIN
```

### AC8 --- Exactly one Primary TIN

Given exactly one TIN is marked as Primary TIN\
Then the Primary TIN validation does not prevent task completion.

### AC9 --- Persist user selection

Given the user changes the Primary TIN\
When the change is applied\
Then the selection is persisted in `changeClientInformation`.

## Technical Notes

Extend the Tax Information model with an equivalent field:

``` ts
primaryTin?: boolean;
```

The automatic initialization must **not** be implemented as a frontend
rule executed every time Angular rebuilds or reloads the comparison
section.

It represents initialization of the CCI business data and must happen
only at the first creation of the task.

This avoids overwriting a CO user's previous decision when:

-   The page is refreshed
-   A section is rebuilt
-   The task is reopened
-   The task returns after rejection/sign-off

Before task completion, validate the complete Third Party
tax-information collection and ensure that exactly one TIN is marked as
primary.

------------------------------------------------------------------------

# Dependencies

## Ticket 2 → Ticket 3

`[CCI] Display and manage Italian Birth Town Code`

is blocked by:

`[CCI] Add Obtaining Date for each Nationality`

The Italian Birth Town Code visibility rule depends on identifying the
most recent nationality using the Obtaining Date.

## Independent tickets

The following tickets can be developed independently:

-   `[CCI] Add PO Box field to Third Party and Policy addresses`
-   `[CCI] Add and validate Primary TIN`

------------------------------------------------------------------------

# Suggested implementation order

1.  **Add Obtaining Date for each Nationality**
2.  **Display and manage Italian Birth Town Code**
3.  **Add PO Box field to Third Party and Policy addresses**
4.  **Add and validate Primary TIN**

The first two should preferably be implemented in this order because the
Italian Birth Town Code directly depends on the new nationality date
information.

------------------------------------------------------------------------

# Overall Definition of Done

The backlog is considered complete when:

-   All new fields are available in the frontend models.
-   CLASS/Core values are correctly mapped.
-   User-retained values are stored in `changeClientInformation`.
-   Original source data is not mutated by frontend modifications.
-   Nationality country and obtaining date remain associated during
    add/edit/delete operations.
-   Italian Birth Town Code visibility and mandatory rules are
    implemented.
-   Italian Birth Town Code search returns a maximum of 30 matching
    values.
-   Primary TIN initialization runs only during initial task creation.
-   Exactly one Primary TIN is required before completion when TIN
    information exists.
-   PO Box is supported for Third Party residential addresses and Policy
    correspondence address.
-   Backend/frontend DTO mappings are updated.
-   Unit tests are added or updated.
-   Existing CCI comparison behaviour is not regressed.
