{
  "checkListOverviewData": [
    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.ScreenDescription",
    {
      "loadTimeStamp": null,
      "screenId": "NEW_BUSINESS_CHECKLIST",
      "inputedScreenDescription": null,
      "tabs": [
        "java.util.ArrayList",
        [
          [
            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Tab",
            {
              "tabId": "CHECKLIST",
              "label": "Checklist",
              "i18NLabelKey": null,
              "order": 1,
              "triggersRecomputeScreen": false,
              "type": "FormTab",
              "groups": [
                "java.util.ArrayList",
                [
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "GENERAL_DETAILS",
                      "title": "General details - New Business",
                      "i18NTitleKey": null,
                      "order": 2,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "CONTRACT_TYPE",
                              "label": "Contract type",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "Investment policy",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "Investment policy",
                                      "value": "Investment policy"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "POLICY_NUMBER",
                              "label": "Policy number",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "2606-162590",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "LIFE_COVER",
                      "title": "Life Cover Type",
                      "i18NTitleKey": null,
                      "order": 2,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "LIFE_COVER_TYPE",
                              "label": "Life cover type capped",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ]
                        ]
                      ],
                      "displayIf": "#CONTRACT_TYPE# == Capitalised policy"
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "INTERMEDIATION",
                      "title": "Intermediation",
                      "i18NTitleKey": null,
                      "order": 3,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "APP_FORM_SIGNER",
                              "label": "Application form signer",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "BROKER",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BROKER",
                                      "value": "Broker"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ILA",
                                      "value": "Internal Lombard Agent"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MC",
                                      "value": "Marketing Consultant"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "SIGNATURE",
                      "title": "Signature",
                      "i18NTitleKey": null,
                      "order": 4,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
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
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ELECTRONIC",
                                      "value": "Electronic"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "WET",
                                      "value": "Wet"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MULTIPLE",
                                      "value": "Multiple"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
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
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "INTERNAL_PROVIDER",
                                      "value": "Internal Provider"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PARTNER_ESIGN_SERVICE",
                                      "value": "Partner e-signature service"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.CheckBoxField",
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
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.CheckBoxField",
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
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.CheckBoxField",
                            {
                              "fieldId": "MULTIFACTOR_AUTHENTICATION",
                              "label": "Multifactor Authentication?",
                              "i18NLabelKey": null,
                              "order": 5,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "false",
                              "displayIf": "(#SIGNATURE_TYPE# == ELECTRONIC || #SIGNATURE_TYPE# == MULTIPLE) && #PROVIDER# == PARTNER_ESIGN_SERVICE",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.CheckBoxField",
                            {
                              "fieldId": "ORIGINAL_SIGNED_DOCUMENT_AUDIT_LOG_CMT",
                              "label": "Original Signed Document + Audit Log in CMT?",
                              "i18NLabelKey": null,
                              "order": 6,
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
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "PRICING_APPROVAL",
                      "title": "Pricing Approval",
                      "i18NTitleKey": null,
                      "order": 5,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "NTA_MARKUP_EXCEPTION",
                              "label": "NTA Markup Exception?",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "EXPLAIN_EXCEPTION",
                              "label": "Explain Exception",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "rows": null,
                              "cols": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "IS_FAMILY_CASE",
                              "label": "It is a family case ?",
                              "i18NLabelKey": null,
                              "order": 8,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PRICING_APPROVAL_CHECKED",
                              "label": "Pricing approval has been checked in CRM tab (Salesforce) and is in line with the charging structure signed by the PH in the Application form, and the premium received",
                              "i18NLabelKey": null,
                              "order": 11,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "SIGNOFFS",
                      "title": "Sign offs",
                      "i18NTitleKey": null,
                      "order": 6,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "TRANSACTION_FEEDBACK",
                              "label": "Transaction feedback",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "ACCEPTED",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ACCEPTED",
                                      "value": "Accepted"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "REJECTED",
                                      "value": "Rejected"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "REJECTED_AML",
                                      "value": "Rejected AML"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "DUE_DILIGENCE",
                      "title": "Due diligence - EBO and related parties to the policy",
                      "i18NTitleKey": null,
                      "order": 7,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "BACKGROUND_DETAILS",
                              "label": "Profession background details",
                              "i18NLabelKey": null,
                              "order": 200,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "sdfsdf",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "rows": null,
                              "cols": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "RISK_ASSESSMENT",
                              "label": "Profession risk assessment",
                              "i18NLabelKey": null,
                              "order": 201,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "sfsdfsd",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "rows": null,
                              "cols": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PEP",
                              "label": "Is there a PEP on the policy",
                              "i18NLabelKey": null,
                              "order": 202,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "ORIGINATOR_PEP",
                              "label": "Is the Originator, linked to the source of funds to be invested, a PEP?",
                              "i18NLabelKey": null,
                              "order": 203,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "true",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "TCC_SIGNED",
                              "label": "TCC duly signed received?",
                              "i18NLabelKey": null,
                              "order": 308,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "TCC_SIGNED_REFUSED",
                              "label": "Did the PH/EBO refuse to sign the TCC and this after several reminders to sign the form?",
                              "i18NLabelKey": null,
                              "order": 309,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "#TCC_SIGNED# == NO",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "TRANSACTIONS_DETAILS",
                      "title": "Transaction details",
                      "i18NTitleKey": null,
                      "order": 100,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PREMIUM_WITH_ASSETS",
                              "label": "Premium with assets?",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "IS_ROP_CASE",
                              "label": "ROP Case ?",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "IS_DEALING",
                              "label": "Is dealing requested",
                              "i18NLabelKey": null,
                              "order": 8,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PAYMENT_THIRD_PARTY",
                              "label": "Payment from a third party payer",
                              "i18NLabelKey": null,
                              "order": 9,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": true,
                              "sourceSystem": "From CLASS",
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "BANK_NOT_IN_RESIDENCE",
                              "label": "Are the funds paid from a bank account located in a country which is not the tax country of residence of the PH(s)/EBO(s)?",
                              "i18NLabelKey": null,
                              "order": 16,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "YES",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "CASE_RISK",
                      "title": "Escalation level",
                      "i18NTitleKey": null,
                      "order": 1000,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "RISK_VALUE",
                              "label": "Overall case risk",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "HIGH",
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "Calculated based on checklist values",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "BLOCKED_VALUE",
                              "label": "Actions required in CLASS to complete Docs & Controls and trigger reviews",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "Calculated based on checklist values",
                              "rows": null,
                              "cols": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PCS_REVIEW",
                              "label": "PCS Review",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "None (Compliance escalation required)",
                              "displayIf": "true",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "Calculated based on Overall case risk",
                              "minWidthPct": null,
                              "minWidthPx": null,
                              "options": [
                                "java.util.ArrayList",
                                [
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "",
                                      "value": ""
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "None (Compliance escalation required)",
                                      "value": "None (Compliance escalation required)"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "RISK_REASON",
                              "label": "Compliance escalation rules triggered",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "EBO/originator industry sector: TP 0003314214: Animals and plants trading (wildlife, wild plants, protected species, ivory). Sanction list. Distribution Risk",
                              "displayIf": "true",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "Calculated based on Overall case risk",
                              "rows": null,
                              "cols": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ]
                        ]
                      ],
                      "displayIf": null
                    }
                  ]
                ]
              ]
            }
          ]
        ]
      ]
    }
  ],
  "policyNumber": "2606-162701",
  "businessTransaction": "15317939",
  "caseRef": "NBD_68219_20260619"
}
