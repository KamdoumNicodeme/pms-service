Context
An external audit (PWC) found that a PEP-related case (answer to "Is there a PEP on the policy" = Yes) was escalated to Senior sign-off (medium) instead of Compliance (high). The business rule requires that any PEP-related transaction be escalated to HIGH for Compliance review. The escalation rules had been reviewed and tested in 2024 and were considered to be working correctly.
Analysis
Investigation of the risk-scoring code (PepRisk.pep, the display method evaluatePep, and the engine rule Onboarding_Checklist.Rule337) identified two conditional-logic defects that can, individually or combined, prevent PEP escalation.
Defect #1 — The "MISSING" data status short-circuits the PEP check.
The PEP = YES → HIGH check is nested inside the else of the condition that tests whether pepStatusData (from risk factor INT_RF_005) starts with "MISSING". When that data is missing, the code enters the MISSING branch, adds the case to BLOCKED without a return / without evaluating the PEP field, and the calculation falls through to STANDARD. As a result, the screen displays "Yes" (source getRiskFactorAnswerDescription) while the risk is scored as STANDARD (source getRiskFactorData). The two methods rely on different sources, which explains the divergence between what is displayed and the actual escalation.
Defect #2 — The FORCED_MEDIUM override skips the PEP evaluation entirely (specific to Rule337).
In the engine rule, if RISK_VALUE = FORCED_MEDIUM, execution enters the first branch and the entire PEP evaluation block sits in the else, so it is never executed. A case with PEP = Yes but forced to medium stays at medium.
Root cause
The regulatory PEP trigger is not unconditional: it is subordinated to data quality (MISSING) and to a manual override (FORCED_MEDIUM). This is therefore not a rule that "does not work", but a rule whose trigger can be silently bypassed by a data state or an operational action. This behavior was not caught during the 2024 testing because those tests most likely did not cover the combination of "PEP = Yes" with a MISSING risk factor or RISK_VALUE = FORCED_MEDIUM.
Remaining verification point
To formally tie the audited case to one of the two defects, the following must be captured for that case: the value returned by getRiskFactorData(transaction, INT_RF_005) and the value of RISK_VALUE at the time of calculation. The case of the YES constant must also be confirmed (since the field is passed through .toUpperCase(), a "Yes" constant would cause the equals to fail).
Scope
To be assessed: query all cases where PEP/IS_PEP_PAYER/ORIGINATOR_PEP = Yes AND (risk factor INT_RF_005 MISSING OR RISK_VALUE = FORCED_MEDIUM) to determine whether the incident is isolated or systemic.
