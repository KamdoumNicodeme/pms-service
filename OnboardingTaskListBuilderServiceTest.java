# Client Profiling Frontend - Technical Documentation

This directory contains the technical documentation of the `client-profiling-frontend` worker.

The goal of this documentation is to help developers understand the application architecture, the data flow, the comparison mechanism, and the Change Client Information (CCI) workflow.

> The integration flow between Case Management tasks and the Client Profiling worker will be documented separately.

## Documentation

### [Architecture](./architecture.md)

High-level overview of the Client Profiling frontend structure and the responsibilities of its main layers and components.

### [Data Flow](./data-flow.md)

Explains how source data is transformed into comparison data, displayed to the user, resolved, and propagated to the CCI model.

### [Comparison Engine](./comparison-engine.md)

Describes the generic Data Comparison mechanism: fields, rows, statuses, resolutions, source values, lists, groups, and the comparison store.

### [Change Client Information](./change-client-information.md)

Explains the Change Client Information workflow and how selected or manually entered values are applied to the CCI model.

### [Development Guide](./development-guide.md)

Practical guidance for developers adding or modifying fields, comparison rules, manual entries, and CCI mappings.

## Recommended reading order

For a developer joining the project:

1. [Architecture](./architecture.md)
2. [Data Flow](./data-flow.md)
3. [Comparison Engine](./comparison-engine.md)
4. [Change Client Information](./change-client-information.md)
5. [Development Guide](./development-guide.md)

## Main source locations

```text
src/app/
├── core/
│   └── providers/
├── features/
│   └── client-profiling/
│       ├── components/
│       │   ├── cci-manual-change/
│       │   ├── change-client-information/
│       │   └── change-client-information-signoff/
│       ├── shared/
│       ├── client-profiling.component.ts
│       ├── client-profiling.component.html
│       ├── client-profiling.route.ts
│       └── client-profiling-configuration.ts
└── shared/
    └── components/
        └── data-comparison/
```

The generic comparison engine is shared and should remain independent from CCI-specific business rules whenever possible.
