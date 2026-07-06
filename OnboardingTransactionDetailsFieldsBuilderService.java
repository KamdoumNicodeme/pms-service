Title: Handle null accountCode when the broker is not a business referrer (ROP)
Type: Story / Task
Component: be-transactions, salesforce-connector
Linked to: DEV-10383 (catch block behavior in fetchAccountCode)
Description:
As part of the ROP case creation, the accountCode is computed in be-transactions (fetchAccountCode) from the business referrer linked to the policy's broker. This logic only works when the policy's broker is also registered as a business referrer, which is rarely the case. In all other situations, no business referrer is found and the returned accountCode is null.
We need to define and implement the expected behavior when the accountCode is null, pending the business decision (BA).
To Do:

Confirm with the BA whether a ROP can exist without an accountCode.
Depending on the answer: either relax/disable the validation on the Salesforce side so the opportunity can be created without an accountCode, or implement a default accountCode.
Define what fetchAccountCode should return in the catch block (see TODO DEV-10383).
Verify that ROP opportunity creation/update in Salesforce works end-to-end with a null accountCode.

Acceptance Criteria:

The behavior when the accountCode is null is explicit and validated by the business.
ROP creation in Salesforce does not break when the accountCode is missing (or a consistent default value is applied).
