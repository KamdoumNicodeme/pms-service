# Client Profiling Data Flow

## 1. Overview

The Client Profiling frontend transforms business data into a generic comparison model, lets the user resolve differences, and then applies those resolutions to the Change Client Information model.

```mermaid
flowchart TD
    SOURCES["Source data"] --> CONFIG["Client Profiling comparison configuration"]
    CONFIG --> DTO["Comparison DTOs"]
    DTO --> ENGINE["Data Comparison engine"]
    ENGINE --> ROWS["Comparison rows"]
    ROWS --> UI["Comparison UI"]

    UI --> ACTION["User selects a source or enters a value"]
    ACTION --> RES["Resolution"]
    RES --> STORE["ComparisonStore / resolution state"]
    STORE --> APPLY["Apply changes"]
    APPLY --> SERVICE["ClientProfilingChangeService"]
    SERVICE --> CCI["Updated IChangeClientInformation"]
    CCI --> SAVE["Save through parent/backend flow"]
```

## 2. Source data

A comparison field can contain values from several sources.

A scalar comparison DTO typically contains a structure similar to:

```ts
values: {
  portal: ...,
  core: ...,
  snapshot: ...,
  clip: ...
}
```

The DTO is responsible for describing the values. The comparison engine is responsible for interpreting differences between them.

## 3. Business model to comparison DTO

The Client Profiling configuration creates sections and fields from business models.

Example:

```text
Physical Person
    ↓
General Information
    ↓
First name
Date of birth
Country of birth
Italian birth town code
Nationalities
...
```

Each business field is converted to the appropriate comparison field type.

Typical types are:

```text
text
select
checkbox
date
list
group
```

## 4. DTO to comparison row

The Data Comparison component converts the configured DTO into `ComparisonRow` objects.

For a scalar field, the row contains information such as:

```text
id
key
label
kind
options
values
status
resolution
fallback
needsAttention
availableSources
```

The row is the representation consumed by the generic UI.

## 5. Status calculation

Comparison utilities determine the status from source values.

Typical statuses include:

```text
aligned
updated
drift
conflict
added
removed
```

Scalar fields and list entries may use different status calculation functions because list entries can exist in only some sources.

The status determines whether the row requires user attention.

## 6. Default resolution

When a row is created, the comparison engine builds a fallback/default resolution.

A resolution contains information such as:

```text
source
value
reason
comment
reviewed
```

If the user does not override the row, the fallback resolution remains effective.

If the user changes the resolution, the override is stored and becomes the effective resolution.

## 7. User resolution

The user can typically:

- keep/select a value from an available source;
- type another value;
- select another value from a dropdown;
- add a manual list entry;
- remove an entry;
- provide a reason;
- provide a comment when required.

The generic `comparison-resolution` component is responsible for presenting these choices.

Fields can restrict which source choices are displayed through `availableSources`.

Example:

```text
Italian birth town code

Available:
- CLASS (CURRENT)
- TYPE ANOTHER VALUE

Not available:
- CONNECT
```

This restriction belongs to the field configuration, while the generic resolution component only renders the provided choices.

## 8. Resolution map

After interaction, resolved values are represented as a map keyed by comparison row identifiers.

Conceptually:

```text
field/entry identifier
        ↓
Resolution
```

The identifier allows the change service to understand which business property must be updated.

For complex/list structures, identifiers can contain additional information such as:

```text
prefix
sub-type
field
manual identifier
```

`ChangeIdParser` is used to interpret these identifiers.

## 9. Applying changes to CCI

The Client Profiling change service receives:

```text
IChangeClientInformation
source holder
resolved changes
removed entries
```

It clones the CCI model before applying changes.

```mermaid
flowchart LR
    ORIGINAL["Current CCI"] --> CLONE["structuredClone"]
    CLONE --> HOLDER["Find/Create target holder"]
    HOLDER --> PHYSICAL{"Holder type"}
    PHYSICAL -->|Physical| PC["Apply physical person changes"]
    PHYSICAL -->|Moral| MC["Apply moral person changes"]
    PC --> REMOVE["Apply removed entries"]
    MC --> REMOVE
    REMOVE --> REASONS["Update change reasons"]
    REASONS --> RESULT["Updated CCI"]
```

## 10. Physical person changes

Physical person changes can include:

- scalar fields;
- nationalities;
- manually added nationalities;
- tax information;
- manually added tax information;
- emails;
- manually added emails;
- phone numbers;
- manually added phone numbers;
- nested structures such as addresses, identity documents, and professional details.

The change service is responsible for translating generic resolutions back into the CCI domain model.

## 11. Manual entries

Manual list entries require special processing because they do not necessarily correspond to an existing source entry.

Example for nationality:

```text
nationalities:manual-<id>:country
nationalities:manual-<id>:date
```

The service groups related fields using the manual identifier, then creates the corresponding domain object only when the required values are present.

This same pattern is used for complex manual entries such as tax information.

## 12. Saving

The comparison layer does not directly persist data.

Its responsibility ends with producing resolutions.

The feature/service layer transforms those resolutions into the updated CCI model, which is then passed to the existing save flow.

This separation is important because:

- the comparison engine remains reusable;
- persistence remains feature-specific;
- backend model changes do not need to leak into generic UI components.
