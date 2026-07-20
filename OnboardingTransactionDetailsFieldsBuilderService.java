The Change of Client Information (CCI) workflow requires an internal domain model to manipulate client information throughout the workflow.

Although the CCI model is structurally very close to the CLASS model, it must remain independent from it. CLASS will remain the system of record and dedicated mappers will be responsible for converting between both models.

The CCI model will become the canonical model used across the application (Camunda process variables, REST APIs, business services and UI).
