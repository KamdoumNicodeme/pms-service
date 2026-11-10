# Client Profiling Frontend Architecture

## 1. Purpose

`client-profiling-frontend` is the frontend worker responsible for displaying Client Profiling information and supporting Change Client Information (CCI) operations.

The application separates:

- feature-specific orchestration;
- comparison and resolution logic;
- CCI model updates;
- reusable UI components;
- API/provider access.

This separation allows the generic comparison engine to be reused while keeping CCI business rules in the Client Profiling feature.

## 2. High-level architecture

```mermaid
flowchart TD
    CP["Client Profiling Feature"]

    CP --> CCI["Change Client Information"]
    CP --> MANUAL["CCI Manual Change"]
    CP --> SIGNOFF["CCI Sign-off"]
    CP --> SHARED["Client Profiling Shared Components"]

    CCI --> DC["Data Comparison Engine"]
    MANUAL --> DC

    DC --> ROWS["Comparison Rows"]
    DC --> STORE["ComparisonStore"]
    DC --> RES["Resolution"]

    RES --> CHANGE["ClientProfilingChangeService"]
    CHANGE --> MODEL["IChangeClientInformation"]

    CP --> PROVIDERS["Core / Providers"]
    PROVIDERS --> API["Backend APIs"]
```

## 3. Main application areas

### `core/providers`

Contains services/providers used to communicate with backend APIs and services containing application-level business transformations.

A key service in the CCI workflow is the change service responsible for applying resolved changes to the `IChangeClientInformation` model.

### `features/client-profiling`

Contains the Client Profiling feature.

Important elements include:

- `client-profiling.component.*`: feature-level orchestration;
- `client-profiling.route.ts`: route definition;
- `client-profiling-configuration.ts`: Client Profiling configuration;
- `components/change-client-information`: CCI screen;
- `components/cci-manual-change`: manual CCI changes;
- `components/change-client-information-signoff`: CCI sign-off flow;
- `shared`: components and utilities specific to Client Profiling.

### `shared/components/data-comparison`

Contains the generic comparison engine used to compare values coming from different sources and allow users to resolve differences.

Typical elements include:

- comparison rows;
- scalar fields;
- list fields;
- group fields;
- comparison resolution;
- comparison annotations;
- comparison result;
- comparison toolbar;
- `ComparisonStore`;
- comparison utilities;
- Core comparison utilities.

## 4. Main architectural principle

The UI should not directly manipulate the final CCI object for every user interaction.

The expected flow is:

```mermaid
flowchart LR
    A["Source models"] --> B["Comparison DTOs"]
    B --> C["Comparison Rows"]
    C --> D["User Resolution"]
    D --> E["Resolution map"]
    E --> F["Change service"]
    F --> G["Updated CCI model"]
```

This gives the application a clear separation between:

1. what the different systems contain;
2. what the user sees;
3. what the user chooses;
4. what is finally persisted.

## 5. Source concepts

The comparison engine works with multiple source values.

The current project uses source identifiers such as:

- `snapshot`;
- `portal`;
- `core`;
- `clip`.

Their business meaning depends on the data provided to the comparison configuration.

The comparison engine itself should remain generic: it receives source values and determines comparison statuses without embedding feature-specific business rules.

For fields where a source is not applicable, the field can restrict the available resolution sources through `availableSources`.

## 6. Comparison configuration

Feature code converts business models into comparison DTOs.

Examples of comparison DTO types include:

```text
ComparisonSectionDto
ComparisonScalarFieldDto
ComparisonListFieldDto
ComparisonGroupFieldDto
```

These DTOs describe:

- the field key;
- the display label;
- the field type;
- values by source;
- available options;
- editability;
- allowed resolution sources.

The generic Data Comparison component then converts these DTOs into rows used by the UI.

## 7. CCI update layer

Once the user has resolved fields, the selected resolutions are passed to the Client Profiling change service.

The service:

- clones the current CCI model;
- locates the relevant client/policy structure;
- applies scalar changes;
- applies list changes;
- applies manual entries;
- applies removals;
- stores change reasons/comments where required;
- returns the updated CCI object.

This prevents the comparison UI from becoming coupled to the backend CCI structure.

## 8. Design rule

When implementing a new requirement, first determine where the change belongs:

| Requirement | Preferred location |
|---|---|
| Display a new comparison field | Client Profiling comparison configuration |
| Change generic comparison status logic | Data Comparison utilities |
| Add generic resolution UI behavior | Data Comparison components |
| Apply a selected value to CCI | Client Profiling change service |
| Add CCI-specific manual behavior | CCI feature/change service |
| Call a backend endpoint | Core provider/service |
| Add reusable comparison behavior | Shared Data Comparison layer |

Avoid adding CCI-specific rules to generic comparison components unless the behavior is genuinely reusable.
