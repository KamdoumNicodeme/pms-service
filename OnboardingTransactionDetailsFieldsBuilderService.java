{
  "checkListOverviewData": [
    {
      "loadTimeStamp": null,
      "screenId": "NEW_BUSINESS_CHECKLIST",
      "inputedScreenDescription": null,
      "tabs": [
        {
          "tabId": "CHECKLIST",
          "label": "Checklist",
          "i18NLabelKey": null,
          "order": 1,
          "triggersRecomputeScreen": false,
          "type": "FormTab",
          "groups": [
            {
              "groupId": "SIGNATURE",
              "title": "Signature",
              "i18NTitleKey": null,
              "order": 4,
              "fields": [
                {
                  "fieldId": "SIGNATURE_TYPE",
                  "label": "Signature Type?",
                  "i18NLabelKey": null,
                  "order": 1,
                  "enabled": true,
                  "mandatory": true,
                  "multiple": null,
                  "maxMultiple": null,
                  "selectedValue": "WET",
                  "displayIf": null,
                  "enableIf": null,
                  "labelBold": false,
                  "sourceSystem": null,
                  "minWidthPct": null,
                  "minWidthPx": null,
                  "options": [
                    {
                      "key": "",
                      "value": ""
                    },
                    {
                      "key": "ELECTRONIC",
                      "value": "Electronic"
                    },
                    {
                      "key": "WET",
                      "value": "Wet"
                    },
                    {
                      "key": "MULTIPLE",
                      "value": "Multiple"
                    }
                  ]
                },
                {
                  "fieldId": "PROVIDER",
                  "label": "Provider?",
                  "i18NLabelKey": null,
                  "order": 2,
                  "enabled": true,
                  "mandatory": true,
                  "multiple": null,
                  "maxMultiple": null,
                  "selectedValue": "",
                  "displayIf": "#SIGNATURE_TYPE# == ELECTRONIC || #SIGNATURE_TYPE# == MULTIPLE",
                  "enableIf": null,
                  "labelBold": false,
                  "sourceSystem": null,
                  "minWidthPct": null,
                  "minWidthPx": null,
                  "options": [
                    {
                      "key": "",
                      "value": ""
                    },
                    {
                      "key": "INTERNAL_PROVIDER",
                      "value": "Internal Provider"
                    },
                    {
                      "key": "PARTNER_ESIGN_SERVICE",
                      "value": "Partner e-signature service"
                    }
                  ]
                },
                {
                  "fieldId": "EU_AUTHORISED_PROVIDER",
                  "label": "EU authorised Provider?",
                  "i18NLabelKey": null,
                  "order": 3,
                  "enabled": true,
                  "mandatory": true,
                  "multiple": null,
                  "maxMultiple": null,
                  "selectedValue": "false",
                  "displayIf": "(#SIGNATURE_TYPE# == ELECTRONIC || #SIGNATURE_TYPE# == MULTIPLE) && #PROVIDER# == PARTNER_ESIGN_SERVICE",
                  "enableIf": null,
                  "labelBold": false,
                  "sourceSystem": null
                },
                {
                  "fieldId": "CLIENT_IDENTIFICATION_VALIDATION",
                  "label": "Client Identification & Validation?",
                  "i18NLabelKey": null,
                  "order": 4,
                  "enabled": true,
                  "mandatory": true,
                  "multiple": null,
                  "maxMultiple": null,
                  "selectedValue": "false",
                  "displayIf": "(#SIGNATURE_TYPE# == ELECTRONIC || #SIGNATURE_TYPE# == MULTIPLE) && (#PROVIDER# == PARTNER_ESIGN_SERVICE || #PROVIDER# == INTERNAL_PROVIDER)",
                  "enableIf": null,
                  "labelBold": false,
                  "sourceSystem": null
                }
              ],
              "displayIf": null
            }
          ]
        }
      ]
    }
  ],
  "policyNumber": "2606-162701",
  "businessTransaction": "15317939",
  "caseRef": "NBD_68219_20260619"
}
