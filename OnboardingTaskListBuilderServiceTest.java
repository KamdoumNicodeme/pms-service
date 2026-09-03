[Client Profiling Frontend] - Change Client Information - Display additional CLASS snapshot information

Context

As part of the Change of Client Information (CCI) workflow, the Client Profiling application displays the initial business data snapshot coming from CLASS.

A first version of the snapshot and the corresponding frontend screens have already been implemented.

Following the enrichment of the CLASS snapshot, additional information is now available through the Client Profiling backend API.

This ticket aims to update the Client Profiling frontend models and CCI screens in order to consume and display these new fields.

Description

Update the Client Profiling frontend to support the additional fields provided in initialBusinessData.

The existing comparison principle must remain unchanged:

* CLASS snapshot data is displayed as the current/source value.
* The corresponding CCI value is displayed according to the existing comparison/editing mechanism.
* Existing source/conflict/retained-value behaviour must remain unchanged.
* The frontend models must be updated according to the new backend API payload.

1. Policy Holder – Legal Address

Add the following fields to the existing Contact details / Legal Address section for each Policy Holder:

* House name
* Apartment Number
* County
* Area

These fields complement the address information already handled by the frontend.

The values come from the Policy Holder’s legalAddress in the CLASS snapshot.

2. Policy Holder – Tax Information

Update the existing Tax Information section to support the new fiscal information provided by CLASS.

Display:

* TIN
* Reason if TIN Unavailable
* US Person / Entity

The frontend must continue to support multiple Tax Information entries per Policy Holder when provided by the backend.

3. Controlling Person

For a Moral Person, display the Trustee / Controlling Person information when provided by the backend.

The exact backend mapping/source for Trustee information is still to be confirmed.

The implementation should therefore follow the final API contract provided by the backend.

4. Policy – Correspondence / Sending Address

Update the Policy tab to display the policy correspondence address.

This address belongs to the Policy and must not be confused with the legal/contact address of a Policy Holder.

Display:

* Street
* Number
* House name
* Apartment number
* City
* Postcode
* County
* Area
* Country

5. Policy – Opt In / Out

Display the following additional information in the existing Policy correspondence / communication section:

* Policy Type / Opt In-Out value

The value must be taken from the new policy snapshot information returned by the backend.

6. Contact Preferences

Display the following contact preference:

* Language

The value must come from the snapshot returned by the backend.

Frontend model update

Update the TypeScript models representing initialBusinessData to support the enriched backend payload, in particular:
export interface IClientProfilingInitialBusinessData {
  policy: IClientProfilingPolicy;
}
The models must support the new structures/fields for:
ILegalAddress
ITaxInformation
IPolicyCorrespondenceAddress
IContactPreferences

  as well as the controlling-person information once its backend contract has been confirmed.

The frontend model must follow the backend API contract and must not duplicate or transform business information unnecessarily.

Conditions of Acceptance

* The new Legal Address fields are displayed for each applicable Policy Holder.
* House name, Apartment Number, County and Area are correctly populated from the CLASS snapshot.
* Tax Information displays TIN, Reason if TIN Unavailable and US Person / Entity.
* Multiple Tax Information entries are supported.
* Policy correspondence address is displayed in the Policy tab and not as a Policy Holder address.
* All correspondence-address fields provided by the backend are displayed.
* Policy Opt In/Out information is displayed.
* Contact preference Language is displayed.
* Controlling Person / Trustee information is displayed for Moral Persons once available in the backend contract.
* Missing optional CLASS values do not break the screen.
* Existing CCI comparison behaviour remains unchanged.
* Existing conflict/retained/source-system behaviour remains unchanged.
* Existing CCI screens continue to work without regression.
* Frontend TypeScript models are aligned with the new backend response.
* Unit tests are added/updated for the new fields.
