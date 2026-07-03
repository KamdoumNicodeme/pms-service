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
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "POLICY_CUR",
                              "label": "Policy ccy",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "EUR",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.NumberInputField",
                            {
                              "fieldId": "EXPECTED_PREM",
                              "label": "Expected premium (in policy currency)",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "250000.00",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "min": null,
                              "max": null,
                              "decimals": null,
                              "size": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.NumberInputField",
                            {
                              "fieldId": "EXPECTED_PREM_EUR",
                              "label": "Expected premium (in EUR)",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "250000.00",
                              "displayIf": "#POLICY_CUR# != EUR",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "min": null,
                              "max": null,
                              "decimals": null,
                              "size": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "NEW_CLIENT",
                              "label": "New client",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.NumberInputField",
                            {
                              "fieldId": "TOTAL_NAV_POLICY_EUR",
                              "label": "Total existing NAV linked to the same EBO, excluding this expected premium",
                              "i18NLabelKey": null,
                              "order": 9,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0.00",
                              "displayIf": "#NEW_CLIENT# == NO && #BUSINESS_ORIGIN# != BE",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "min": null,
                              "max": null,
                              "decimals": null,
                              "size": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.NumberInputField",
                            {
                              "fieldId": "TOTAL_BE_NAV_POLICY_EUR",
                              "label": "Total existing BE NAV",
                              "i18NLabelKey": null,
                              "order": 9,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0",
                              "displayIf": "#NEW_CLIENT# == NO && #BUSINESS_ORIGIN# == BE",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "min": null,
                              "max": null,
                              "decimals": null,
                              "size": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "COUNTRY_OF_LAW",
                              "label": "Country of applicable law",
                              "i18NLabelKey": null,
                              "order": 13,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
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
                                      "key": "FR",
                                      "value": "FR - France"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "RESIDENCE_COUNTRY",
                              "label": "Riskiest / Missing country of residence",
                              "i18NLabelKey": null,
                              "order": 14,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "TP: 0003314214, FR, Policy Role",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "rows": null,
                              "cols": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "RDR_COMPLIANT",
                              "label": "Is it RDR compliant?",
                              "i18NLabelKey": null,
                              "order": 15,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#COUNTRY_OF_LAW# == GB",
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
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NA",
                                      "value": "N/A"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "MARKET_CONSULTANT",
                              "label": "Marketing consultant",
                              "i18NLabelKey": null,
                              "order": 16,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "sdfsdfsdf",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "MULTI_PRODUCT",
                              "label": "Multi product",
                              "i18NLabelKey": null,
                              "order": 200,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
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
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "NUMBER_PRODUCT",
                              "label": "Number of added product",
                              "i18NLabelKey": null,
                              "order": 201,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0",
                              "displayIf": "#MULTI_PRODUCT# == YES",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "BUSINESS_ORIGIN",
                              "label": "Business Origin",
                              "i18NLabelKey": null,
                              "order": 700,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "LU",
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
                                      "key": "LU",
                                      "value": "LU - Luxembourg"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "PH_RESIDENCE_COUNTRY_RISK",
                              "label": "PH(s) residence country",
                              "i18NLabelKey": null,
                              "order": 704,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": "true",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "OFF_SHORE_INDICIA",
                              "label": "(Belgian Branch) Off-shore indicia found ?",
                              "i18NLabelKey": null,
                              "order": 705,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS and converted",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "PH_FISCAL_COUNTRY",
                              "label": "PH(s) fiscal country",
                              "i18NLabelKey": null,
                              "order": 706,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "TAX_RESIDENCE_INCONSISTENT",
                              "label": "Is there any unjustified inconsistency in the information provided concerning the tax residence of the PH/EBO and this without any legitimate reason?",
                              "i18NLabelKey": null,
                              "order": 707,
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "PH_TYPE_ASSESSMENT",
                              "label": "PH type assessment",
                              "i18NLabelKey": null,
                              "order": 708,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "PH Type=Physical",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PH_NON_FINANCIAL",
                              "label": "Is the PH a passive Non Financial Entity (Passive NFE)?",
                              "i18NLabelKey": null,
                              "order": 709,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#PH_TYPE_ASSESSMENT# != PH Type=Physical",
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
                              "fieldId": "ENTITY_SAME_AS_FATCA",
                              "label": "Are the controlling persons of the entity as mentioned in the FATCA/CRS/AEOI form the same individuals as those identified as EBOs for AML purposes and disclosed in the KYC form?",
                              "i18NLabelKey": null,
                              "order": 710,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#PH_TYPE_ASSESSMENT# != PH Type=Physical && #PH_NON_FINANCIAL# == YES",
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
                              "fieldId": "PH_LEGAL_ENTITY_ARRANGEMENT",
                              "label": "Is the PH a legal entity or a legal arrangement (e.g. trust, foundation,...) located in a country which is not the tax country of residence or place of regular economic or professional activities/interests of the EBO?",
                              "i18NLabelKey": null,
                              "order": 711,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NA",
                                      "value": "(N/A for trust with professional trustee based in EU)"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "EVIDENCE_KNOWN_LEGAL_ENTITY",
                              "label": "Did you collect supporting evidence that the legal entity or the legal arrangement is known by the tax authorities of the country of residence of the EBO or could the PH demonstrate that its establishment complies with the legal provisions of the country of residence of the PH/EBO?",
                              "i18NLabelKey": null,
                              "order": 712,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "fieldId": "PH_LEGAL_ENTITY",
                              "label": "Is the PH a legal entity (excluding société simple or any legal arrangement such as trust, fideicomiso, foundation or fiduciary)?",
                              "i18NLabelKey": null,
                              "order": 713,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "fieldId": "PAID_FROM_APPOINTED",
                              "label": "Is the premium paid from the appointed beneficiary who is a natural person ?",
                              "i18NLabelKey": null,
                              "order": 714,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#PH_TYPE_ASSESSMENT# != PH Type=Physical && #PH_LEGAL_ENTITY# == YES",
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
                              "fieldId": "FORMER_SWISS_PH",
                              "label": "Any former Swiss residency known for PH(s)",
                              "i18NLabelKey": null,
                              "order": 715,
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
                              "fieldId": "PROOF_DEREGISTRATION_PH",
                              "label": "Proof of deregistration known for PH(s)",
                              "i18NLabelKey": null,
                              "order": 716,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#FORMER_SWISS_PH# == YES",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "TRUST_RESIDENCE_COUNTRY_RISK",
                              "label": "Trust residence country",
                              "i18NLabelKey": null,
                              "order": 802,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "TRUSTEE_RESIDENCE_COUNTRY_RISK",
                              "label": "Trustee residence country",
                              "i18NLabelKey": null,
                              "order": 900,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "SETTLOR_RESIDENCE_COUNTRY_RISK",
                              "label": "Settlor residence country",
                              "i18NLabelKey": null,
                              "order": 808,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "MRL_TRUSTEE",
                              "label": "MRL Trustee in a participating juridiction",
                              "i18NLabelKey": null,
                              "order": 800,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#THIRD_PARTY_SUB_TYPE_PH_1# == Trust",
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
                              "fieldId": "APP_FORM_SIGNED",
                              "label": "Application Form signed by PH?",
                              "i18NLabelKey": null,
                              "order": 801,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "YES",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "PROXY_COUNTRY_RISK",
                              "label": "Proxy residence country",
                              "i18NLabelKey": null,
                              "order": 804,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "LA_DIFFERENT_TO_PH",
                              "label": "LA different to PH",
                              "i18NLabelKey": null,
                              "order": 805,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "true",
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
                                      "key": "NO",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "LA_RESIDENCE_COUNTRY_RISK",
                              "label": "LA residence country",
                              "i18NLabelKey": null,
                              "order": 806,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "FORMER_SWISS_LA",
                              "label": "Any former Swiss residency known for LA(s)",
                              "i18NLabelKey": null,
                              "order": 807,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#LA_DIFFERENT_TO_PH# == YES",
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
                              "fieldId": "PROOF_DEREGISTRATION_LA",
                              "label": "Proof of deregistration received for LA(s)",
                              "i18NLabelKey": null,
                              "order": 808,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#FORMER_SWISS_LA# == YES",
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
                              "fieldId": "APP_FORM_SIGNED_LA",
                              "label": "Application Form signed by LA?",
                              "i18NLabelKey": null,
                              "order": 809,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#LA_DIFFERENT_TO_PH# == YES",
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
                              "fieldId": "LA_DIFFERENT_BEN",
                              "label": "LA different to BEN",
                              "i18NLabelKey": null,
                              "order": 810,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "YES",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "DATE_OF_BIRTH",
                              "label": "Age of youngest LA",
                              "i18NLabelKey": null,
                              "order": 811,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "sdfsdfsdf",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "POLICY_SIGNED_COUNTRY",
                              "label": "Policy Signed Country",
                              "i18NLabelKey": null,
                              "order": 812,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
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
                                      "key": "AD",
                                      "value": "AD - Andorra"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AE",
                                      "value": "AE - United Arab Emirates (UAE)"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AF",
                                      "value": "AF - Afghanistan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AG",
                                      "value": "AG - Antigua and Barbuda"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AI",
                                      "value": "AI - Anguilla"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AL",
                                      "value": "AL - Albania"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AM",
                                      "value": "AM - Armenia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AN",
                                      "value": "AN - Netherlands Antilles"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AO",
                                      "value": "AO - Angola"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AQ",
                                      "value": "AQ - Antarctica"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AR",
                                      "value": "AR - Argentina"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AS",
                                      "value": "AS - American Samoas"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AT",
                                      "value": "AT - Austria"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AU",
                                      "value": "AU - Australia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AW",
                                      "value": "AW - Aruba"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AX",
                                      "value": "AX - Aland Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "AZ",
                                      "value": "AZ - Azerbaijan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BA",
                                      "value": "BA - Bosnia and Hergovina"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BB",
                                      "value": "BB - Barbados"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BD",
                                      "value": "BD - Bangladesh"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BE",
                                      "value": "BE - Belgium"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BF",
                                      "value": "BF - Burkina Faso"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BG",
                                      "value": "BG - Bulgaria"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BH",
                                      "value": "BH - Bahrain"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BI",
                                      "value": "BI - Burundi"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BJ",
                                      "value": "BJ - Benin"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BM",
                                      "value": "BM - Bermuda"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BN",
                                      "value": "BN - Brunei Darussalam"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BO",
                                      "value": "BO - Bolivia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BR",
                                      "value": "BR - Brazil"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BS",
                                      "value": "BS - Bahamas"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BT",
                                      "value": "BT - Bhutan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BV",
                                      "value": "BV - Bouvet Island"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BW",
                                      "value": "BW - Botswana"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BY",
                                      "value": "BY - Belarus"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "BZ",
                                      "value": "BZ - Belize"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CA",
                                      "value": "CA - Canada"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CC",
                                      "value": "CC - Cocos (Keeling) Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CD",
                                      "value": "CD - Congo- Democratic Republic of"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CF",
                                      "value": "CF - Central African Republic"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CG",
                                      "value": "CG - Congo"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CH",
                                      "value": "CH - Switzerland"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CI",
                                      "value": "CI - Cote d'Ivoire"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CK",
                                      "value": "CK - Cook Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CL",
                                      "value": "CL - Chile"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CM",
                                      "value": "CM - Cameroon"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CN",
                                      "value": "CN - China"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CO",
                                      "value": "CO - Colombia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CR",
                                      "value": "CR - Costa Rica"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CU",
                                      "value": "CU - Cuba"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CV",
                                      "value": "CV - Cape Verde"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CW",
                                      "value": "CW - Curacao"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CX",
                                      "value": "CX - Christmas Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CY",
                                      "value": "CY - Cyprus"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "CZ",
                                      "value": "CZ - Czech Republic"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "DE",
                                      "value": "DE - Germany"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "DJ",
                                      "value": "DJ - Djibouti"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "DK",
                                      "value": "DK - Denmark"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "DM",
                                      "value": "DM - Dominica"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "DO",
                                      "value": "DO - Dominican Republic"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "DZ",
                                      "value": "DZ - Algeria"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "EC",
                                      "value": "EC - Ecuador"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "EE",
                                      "value": "EE - Estonia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "EG",
                                      "value": "EG - Egypt"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "EH",
                                      "value": "EH - Western Sahara"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ER",
                                      "value": "ER - Eritrea"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ES",
                                      "value": "ES - Spain"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ET",
                                      "value": "ET - Ethiopia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "FI",
                                      "value": "FI - Finland"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "FJ",
                                      "value": "FJ - Fiji"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "FK",
                                      "value": "FK - Falkland Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "FM",
                                      "value": "FM - Micronesia- Federated States of"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "FO",
                                      "value": "FO - Faroe Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "FR",
                                      "value": "FR - France"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GA",
                                      "value": "GA - Gabon"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GB",
                                      "value": "GB - United Kingdom"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GD",
                                      "value": "GD - Grenada"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GE",
                                      "value": "GE - Georgia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GF",
                                      "value": "GF - French Guiana"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GG",
                                      "value": "GG - Guernsey"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GH",
                                      "value": "GH - Ghana"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GI",
                                      "value": "GI - Gibraltar"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GL",
                                      "value": "GL - Greenland"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GM",
                                      "value": "GM - Gambia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GN",
                                      "value": "GN - Guinea"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GP",
                                      "value": "GP - Guadeloupe"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GQ",
                                      "value": "GQ - Equatorial Guinea"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GR",
                                      "value": "GR - Greece"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GS",
                                      "value": "GS - South Georgia and the South Sandwich Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GT",
                                      "value": "GT - Guatemala"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GU",
                                      "value": "GU - Guam"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GW",
                                      "value": "GW - Guinea-Bissau"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "GY",
                                      "value": "GY - Guyana"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "HK",
                                      "value": "HK - Hong Kong"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "HM",
                                      "value": "HM - Heard Island and McDonald Isls"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "HN",
                                      "value": "HN - Honduras"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "HR",
                                      "value": "HR - Croatia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "HT",
                                      "value": "HT - Haiti"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "HU",
                                      "value": "HU - Hungary"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IC",
                                      "value": "IC - Canary Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ID",
                                      "value": "ID - Indonesia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IE",
                                      "value": "IE - Ireland"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IL",
                                      "value": "IL - Israel"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IM",
                                      "value": "IM - Isle of Man"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IN",
                                      "value": "IN - India"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IO",
                                      "value": "IO - British Indian Ocean Territory"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IQ",
                                      "value": "IQ - Iraq"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IR",
                                      "value": "IR - Iran- Islamic Republic of"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IS",
                                      "value": "IS - Iceland"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "IT",
                                      "value": "IT - Italy"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "JE",
                                      "value": "JE - Jersey"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "JM",
                                      "value": "JM - Jamaica"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "JO",
                                      "value": "JO - Jordan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "JP",
                                      "value": "JP - Japan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KE",
                                      "value": "KE - Kenya"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KG",
                                      "value": "KG - Kyrgyzstan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KH",
                                      "value": "KH - Cambodia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KI",
                                      "value": "KI - Kiribati"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KM",
                                      "value": "KM - Comoros"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KN",
                                      "value": "KN - Saint Kitts and Nevis"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KP",
                                      "value": "KP - Korea- Democratic P. R. of"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KR",
                                      "value": "KR - Korea- Republic of"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KW",
                                      "value": "KW - Kuwait"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KY",
                                      "value": "KY - Cayman Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "KZ",
                                      "value": "KZ - Kazakhstan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LA",
                                      "value": "LA - Lao People's Democratic Rep."
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LB",
                                      "value": "LB - Lebanon"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LC",
                                      "value": "LC - Saint Lucia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LI",
                                      "value": "LI - Liechtenstein"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LK",
                                      "value": "LK - Sri Lanka"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LR",
                                      "value": "LR - Liberia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LS",
                                      "value": "LS - Lesotho"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LT",
                                      "value": "LT - Lithuania"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LU",
                                      "value": "LU - Luxembourg"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LV",
                                      "value": "LV - Latvia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "LY",
                                      "value": "LY - Libyan Arab Jamahiriya"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MA",
                                      "value": "MA - Morocco"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MC",
                                      "value": "MC - Monaco"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MD",
                                      "value": "MD - Moldova- Republic of"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ME",
                                      "value": "ME - Montenegro"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MG",
                                      "value": "MG - Madagascar"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MH",
                                      "value": "MH - Marshall Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MK",
                                      "value": "MK - Macedonia- Former Yugoslav Rep"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ML",
                                      "value": "ML - Mali"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MM",
                                      "value": "MM - Myanmar"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MN",
                                      "value": "MN - Mongolia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MO",
                                      "value": "MO - Macao"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MP",
                                      "value": "MP - Northern Mariana Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MQ",
                                      "value": "MQ - Martinique"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MR",
                                      "value": "MR - Mauritania"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MS",
                                      "value": "MS - Montserrat"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MT",
                                      "value": "MT - Malta"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MU",
                                      "value": "MU - Mauritius"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MV",
                                      "value": "MV - Maldives"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MW",
                                      "value": "MW - Malawi"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MX",
                                      "value": "MX - Mexico"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MY",
                                      "value": "MY - Malaysia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "MZ",
                                      "value": "MZ - Mozambique"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NA",
                                      "value": "NA - Namibia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NC",
                                      "value": "NC - New Caledonia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NE",
                                      "value": "NE - Niger"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NG",
                                      "value": "NG - Nigeria"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NI",
                                      "value": "NI - Nicaragua"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NL",
                                      "value": "NL - The Netherlands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NO",
                                      "value": "NO - Norway"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NP",
                                      "value": "NP - Nepal"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NR",
                                      "value": "NR - Nauru"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NU",
                                      "value": "NU - Niue"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "NZ",
                                      "value": "NZ - New Zealand"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "OM",
                                      "value": "OM - Oman"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PA",
                                      "value": "PA - Panama"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PE",
                                      "value": "PE - Peru"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PF",
                                      "value": "PF - French Polynesia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PG",
                                      "value": "PG - Papua New Guinea"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PH",
                                      "value": "PH - Philippines"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PK",
                                      "value": "PK - Pakistan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PL",
                                      "value": "PL - Poland"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PM",
                                      "value": "PM - Saint Pierre and Miquelon"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PN",
                                      "value": "PN - Pitcairn"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PR",
                                      "value": "PR - Puerto Rico"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PS",
                                      "value": "PS - Palestinian Territory-Occupied"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PT",
                                      "value": "PT - Portugal"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PW",
                                      "value": "PW - Palau"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "PY",
                                      "value": "PY - Paraguay"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "QA",
                                      "value": "QA - Qatar"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "RE",
                                      "value": "RE - Reunion"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "RO",
                                      "value": "RO - Romania"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "RU",
                                      "value": "RU - Russian Federation"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "RS",
                                      "value": "RS - Republic of Serbia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "RW",
                                      "value": "RW - Rwanda"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SA",
                                      "value": "SA - Saudi Arabia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SB",
                                      "value": "SB - Solomon Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SC",
                                      "value": "SC - Seychelles"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SD",
                                      "value": "SD - Sudan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SE",
                                      "value": "SE - Sweden"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SG",
                                      "value": "SG - Singapore"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SH",
                                      "value": "SH - Saint Helena"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SI",
                                      "value": "SI - Slovenia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SJ",
                                      "value": "SJ - Svalbard and Jan Mayen"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SK",
                                      "value": "SK - Slovakia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SL",
                                      "value": "SL - Sierra Leone"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SM",
                                      "value": "SM - San Marino"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SN",
                                      "value": "SN - Senegal"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SO",
                                      "value": "SO - Somalia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SR",
                                      "value": "SR - Suriname"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SS",
                                      "value": "SS - South Sudan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ST",
                                      "value": "ST - Sao Tome and Principe"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SV",
                                      "value": "SV - El Salvador"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SX",
                                      "value": "SX - Sint Maarten"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SY",
                                      "value": "SY - Syrian Arab Republic"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "SZ",
                                      "value": "SZ - Swaziland"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TC",
                                      "value": "TC - Turks and Caicos Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TD",
                                      "value": "TD - Chad"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TF",
                                      "value": "TF - French Southern Territories"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TG",
                                      "value": "TG - Togo"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TH",
                                      "value": "TH - Thailand"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TJ",
                                      "value": "TJ - Tajikstan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TK",
                                      "value": "TK - Tokelau"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TL",
                                      "value": "TL - Timor-Leste"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TM",
                                      "value": "TM - Turkmenistan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TN",
                                      "value": "TN - Tunisia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TO",
                                      "value": "TO - Tonga"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TP",
                                      "value": "TP - East Timor"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TR",
                                      "value": "TR - Turkey"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TT",
                                      "value": "TT - Trinidad and Tobago"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TV",
                                      "value": "TV - Tuvalu"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TW",
                                      "value": "TW - Taiwan- Province of China"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "TZ",
                                      "value": "TZ - Tanzania- United Republic of"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "UA",
                                      "value": "UA - Ukraine"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "UG",
                                      "value": "UG - Uganda"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "UM",
                                      "value": "UM - United States Minor Outlying Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "US",
                                      "value": "US - United States"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "UY",
                                      "value": "UY - Uruguay"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "UZ",
                                      "value": "UZ - Uzbekistan"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "VA",
                                      "value": "VA - Holy See (Vatican City State)"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "VC",
                                      "value": "VC - Saint Vincent & the Grenadines"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "VE",
                                      "value": "VE - Venezuela"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "VG",
                                      "value": "VG - Virgin Islands- British"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "VI",
                                      "value": "VI - Virgin Islands- U.S"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "VN",
                                      "value": "VN - Viet Nam"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "VU",
                                      "value": "VU - Vanuatu"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "WF",
                                      "value": "WF - Wallis and Futuna"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "WS",
                                      "value": "WS - Samoa"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "XK",
                                      "value": "XK - Kosovo"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "XX",
                                      "value": "XX - Channel Islands"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YE",
                                      "value": "YE - Yemen"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YT",
                                      "value": "YT - Mayotte"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "YU",
                                      "value": "YU - YUGOSLAVIA"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ZA",
                                      "value": "ZA - South Africa"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ZM",
                                      "value": "ZM - Zambia"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "ZW",
                                      "value": "ZW - Zimbabwe"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "SENDING_ADDRESS",
                              "label": "Sending Address Country",
                              "i18NLabelKey": null,
                              "order": 813,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "N/A",
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
                                      "key": "N/A",
                                      "value": "N/A"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "BENEFICIARY_RESIDENCE_COUNTRY_RISK",
                              "label": "Beneficiary residence country",
                              "i18NLabelKey": null,
                              "order": 917,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "IRREVOCABLE_BEN",
                              "label": "Irrevocable BEN",
                              "i18NLabelKey": null,
                              "order": 815,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "IRREVOCABLE_BEN_COUNTRY",
                              "label": "Irrevocable BEN residence country",
                              "i18NLabelKey": null,
                              "order": 816,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "AML_CLAUSE",
                              "label": "AML clause ticked",
                              "i18NLabelKey": null,
                              "order": 817,
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
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PH_DIFFERENT_TO_EBO",
                              "label": "PH different to EBO",
                              "i18NLabelKey": null,
                              "order": 818,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "THIRD_PARTY_SUB_TYPE_EBO",
                              "label": "PH different to Third party sub type for EBO",
                              "i18NLabelKey": null,
                              "order": 819,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "Individual",
                              "displayIf": "#PH_DIFFERENT_TO_EBO# == YES",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "EBO_RESIDENCE_COUNTRY_RISK",
                              "label": "EBO residence country",
                              "i18NLabelKey": null,
                              "order": 820,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "FORMER_SWISS_EBO",
                              "label": "Any former Swiss residency known for EBO(s)",
                              "i18NLabelKey": null,
                              "order": 821,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#PH_DIFFERENT_TO_EBO# == YES",
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
                              "fieldId": "PROOF_DEREGISTRATION_EBO",
                              "label": "Proof of deregistration received for EBO(s)",
                              "i18NLabelKey": null,
                              "order": 822,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#FORMER_SWISS_EBO# == YES",
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
                              "fieldId": "US_INDICIA",
                              "label": "US indicia detected",
                              "i18NLabelKey": null,
                              "order": 950,
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
                              "fieldId": "FATCA_FORM_REFUSED",
                              "label": "Did the PH/EBO refuse to sign the FATCA/CRS/AEOI forms and this after several reminders to sign them?",
                              "i18NLabelKey": null,
                              "order": 951,
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
                              "fieldId": "FATCA_FORM_INCONSISTENT",
                              "label": "Is there any unjustified inconsistency between the information provided in the FATCA/CRS/AEOI form and the information collected for AML/KYC purposes (e.g. TIN, tax residency, etc.) and this without any legitimate reason?",
                              "i18NLabelKey": null,
                              "order": 952,
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
                              "fieldId": "TP_BLOCK",
                              "label": "Transaction block in CLASS for compliance",
                              "i18NLabelKey": null,
                              "order": 1001,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "THIRD_PARTY_SUB_TYPE_PH_1",
                              "label": "Third party sub type for PH(s) #1",
                              "i18NLabelKey": null,
                              "order": 717,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "Individual",
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
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "SAR",
                              "label": "SAR",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#LIFE_COVER_TYPE# == NO",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "UNDERWRITINGS",
                              "label": "Underwritings",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#LIFE_COVER_TYPE# == NO",
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
                                      "key": "L0",
                                      "value": "L0"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "L1",
                                      "value": "L1"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "L2",
                                      "value": "L2"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "L3",
                                      "value": "L3"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "L4",
                                      "value": "L4"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "L5",
                                      "value": "L5"
                                    }
                                  ],
                                  [
                                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputFieldOption",
                                    {
                                      "key": "L6",
                                      "value": "L6"
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
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "PARTNER_CODE",
                              "label": "Introducing partner code",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "B/2345",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "PARTNER_NAME",
                              "label": "Introducing partner name",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "Blacktower Financial  Management (International) Ltd t/a Beacon Global Wealth Management",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Connect",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "PARTNER_TYPE",
                              "label": "Partner type",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "BROKER",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                                      "key": "BROKER",
                                      "value": "Broker"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "IS_PH_REPRESENTATIVE",
                              "label": "Is the PH/EBO a representative (Director/ Shareholder/ Sales Person) of the intermediary who intermediated the policy ?",
                              "i18NLabelKey": null,
                              "order": 5,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
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
                              "fieldId": "REFERRER_AUTO",
                              "label": "Referrer fully automated",
                              "i18NLabelKey": null,
                              "order": 6,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#PARTNER_TYPE# == REFERRER"","enableIf": null,"labelBold": false,
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
                              "fieldId": "PARTNER_STATUS",
                              "label": "Is the partner status Active?",
                              "i18NLabelKey": null,
                              "order": 7,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "YES",
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
                              "fieldId": "ANALYSIS_OF_NEEDS",
                              "label": "Analysis of the Demands and needs/ Fact Find received?",
                              "i18NLabelKey": null,
                              "order": 8,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#PARTNER_TYPE# == AGENT_EMP || #PARTNER_TYPE# == AGENT_IND || #PARTNER_TYPE# == AGENT_EXT",
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
                              "fieldId": "NB_INTERMEDIATED_DS",
                              "label": "Has the New Business been intermediated using the Distance Selling process?",
                              "i18NLabelKey": null,
                              "order": 9,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                              "fieldId": "DS_CONSENT_RECEIVED",
                              "label": "Has the client consent for Distance Selling been received?",
                              "i18NLabelKey": null,
                              "order": 10,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#NB_INTERMEDIATED_DS# == YES && (#PARTNER_TYPE# == AGENT_EMP || #PARTNER_TYPE# == "AGENT_IND)","enableIf": null,"labelBold": false,
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
                              "fieldId": "VIDEO_RECEIVED",
                              "label": "Has the video been received?",
                              "i18NLabelKey": null,
                              "order": 11,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#NB_INTERMEDIATED_DS# == YES && (#PARTNER_TYPE# == AGENT_EMP || #PARTNER_TYPE# == AGENT_IND)",
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
                              "fieldId": "CLIENT_IDENTIFIABLE",
                              "label": "Client could be identified on the video?",
                              "i18NLabelKey": null,
                              "order": 12,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#NB_INTERMEDIATED_DS# == YES && (#PARTNER_TYPE# == AGENT_EMP || #PARTNER_TYPE# == AGENT_IND)",
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
                              "fieldId": "HASH_CHECK_PERFORMED",
                              "label": "Hash tool checks performed",
                              "i18NLabelKey": null,
                              "order": 13,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#NB_INTERMEDIATED_DS# == YES && (#PARTNER_TYPE# == AGENT_EMP || #PARTNER_TYPE# == AGENT_IND)",
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
                              "fieldId": "PASSPORT_CHECK_PERFORMED",
                              "label": "Have the additional checks on passport been performed?",
                              "i18NLabelKey": null,
                              "order": 14,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#NB_INTERMEDIATED_DS# == YES && (#PARTNER_TYPE# == AGENT_EMP || #PARTNER_TYPE# == AGENT_IND)",
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
                              "fieldId": "REQUEST_DS_PROCEDURE",
                              "label": "PCS to request the distance selling procedure from the broker?",
                              "i18NLabelKey": null,
                              "order": 15,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
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
                              "fieldId": "VULNERABLE_INDICIA_DETECTED",
                              "label": "Vulnerable client indicia detected",
                              "i18NLabelKey": null,
                              "order": 16,
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
                              "displayIf": "(#SIGNATURE_TYPE# == ELECTRONIC || #SIGNATURE_TYPE# == MULTIPLE) && #PROVIDER# == PARTNER_ESIGN_SERVICE,"enableIf": null,"labelBold": false,
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
                              "displayIf": "(#SIGNATURE_TYPE# == ELECTRONIC || #SIGNATURE_TYPE# == MULTIPLE) && #PROVIDER# == PARTNER_ESIGN_SERVICE,"enableIf": null,"labelBold": false,
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "PRICING_APPROVAL_STAGE",
                              "label": "Pricing Approval Stage",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "RATIONALE_FOR_EXCEPTION",
                              "label": "Rationale for Exception",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "ADMINISTRATIVE_FEE",
                              "label": "Administrative Fee",
                              "i18NLabelKey": null,
                              "order": 5,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "GAC",
                              "label": "GAC (#Years)",
                              "i18NLabelKey": null,
                              "order": 6,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "POLICY_FEE",
                              "label": "Policy Fee",
                              "i18NLabelKey": null,
                              "order": 7,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.NumberInputField",
                            {
                              "fieldId": "FAMILY_CASE_POL_NBR",
                              "label": "How many policies in the family case",
                              "i18NLabelKey": null,
                              "order": 9,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "min": null,
                              "max": null,
                              "decimals": null,
                              "size": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.NumberInputField",
                            {
                              "fieldId": "FAMILY_CASE_TOTAL_AMOUNT",
                              "label": "Total amount invested in the family case",
                              "i18NLabelKey": null,
                              "order": 10,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "min": null,
                              "max": null,
                              "decimals": null,
                              "size": null
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
                              "fieldId": "IS_PEP_PAYER",
                              "label": "Is one of the payer a PEP?",
                              "i18NLabelKey": null,
                              "order": 204,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "displayIf": "#TCC_SIGNED# == "NO"",
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
                              "fieldId": "INTRODUCING_PARTNER_SIGNED",
                              "label": "Introducing partner signs KYC questionnaire",
                              "i18NLabelKey": null,
                              "order": 310,
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
                              "fieldId": "INFO_PROVIDED_VERIFIED",
                              "label": "Info provided on KYC questionnaire could be verified",
                              "i18NLabelKey": null,
                              "order": 311,
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
                              "fieldId": "NEGATIVE_FINDING",
                              "label": "Negative press finding / World check match (on all roles on policy)",
                              "i18NLabelKey": null,
                              "order": 312,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "No",
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
                                      "key": "No",
                                      "value": "No"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "NEGATIVE_FINDING_THIRD_PARTY",
                              "label": "Negative press finding / Worldcheck match on following Third party(ies)",
                              "i18NLabelKey": null,
                              "order": 313,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": null,
                              "displayIf": "false",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "AT_LEAST_ONE_SOW_OF_KIND",
                              "label": "Invisible field : used as display if condition for ORIGINATOR_WORLD_CHECK",
                              "i18NLabelKey": null,
                              "order": 314,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "YES",
                              "displayIf": "false",
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
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "ORIGINATOR_WORLD_CHECK",
                              "label": "World check match or negative press found on originator",
                              "i18NLabelKey": null,
                              "order": 314,
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
                              "fieldId": "IS_ON_SANCTION_LIST",
                              "label": "Is there any person designated on a sanctions list on the policy?",
                              "i18NLabelKey": null,
                              "order": 315,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "Yes",
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
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "INSIDER",
                              "label": "Is the person an insider to any assets invested in the policy?",
                              "i18NLabelKey": null,
                              "order": 316,
                              "enabled": false,
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
                              "fieldId": "IS_PH_EBO_GOLDEN_VISA",
                              "label": "Is the PH / EBO a country national who applied for residence rights or citizenship in exchange of capital transfers purchase of property or government bonds or investment in corporate entities (who has a Golden Visa) or a Golden Passport",
                              "i18NLabelKey": null,
                              "order": 317,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "No",
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
                                      "key": "No",
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
                              "fieldId": "KYC_SUPPORTING_DOCUMENTS",
                              "label": "Are all KYC supporting documents (and if applicable the ones on the tax conformity of the funds) consistent and not altered (i.e. anomalies/ inconsistencies in the POR, documentation to corroborate the SOF/SOW such as no VAT number, no invoice number, no address, incorrect amount etc.)",
                              "i18NLabelKey": null,
                              "order": 500,
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
                              "fieldId": "INDUSTRY_1",
                              "label": "Industry #1",
                              "i18NLabelKey": null,
                              "order": 100,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "animals",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                                      "key": "animals",
                                      "value": "Animals and plants trading (wildlife, wild plants, protected species, ivory)"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "POSITION_1",
                              "label": "Position #1",
                              "i18NLabelKey": null,
                              "order": 101,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "mngt_dire",
                              "displayIf": "true",
                              "enableIf": null,
                              "labelBold": false,
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
                                      "key": "mngt_dire",
                                      "value": "Management/Director"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "ANNUAL_INCOME_1",
                              "label": "Annual Income #1",
                              "i18NLabelKey": null,
                              "order": 320,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "23332233.000",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "20_PERCENT_INCOME_1",
                              "label": "A portion of wealth is from inheritance / Gift / Donation / Divorce #1",
                              "i18NLabelKey": null,
                              "order": 321,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "YES",
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
                                      "key": "YES",
                                      "value": "Yes"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "SOURCE_OF_WEALTH_1",
                              "label": "Source of wealth description #1",
                              "i18NLabelKey": null,
                              "order": 400,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "sdfsdf",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "MINIMUM_WEALTH_1",
                              "label": "Minimum wealth > 250000€ in transferrable assets (not including property) #1",
                              "i18NLabelKey": null,
                              "order": 401,
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "WEALTH_ORIGINATING_COUNTRY_1_RISK_1",
                              "label": "Country 1 origin of premium #1",
                              "i18NLabelKey": null,
                              "order": 403,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "WEALTH_ORIGINATING_COUNTRY_2_RISK_1",
                              "label": "Country 2 origin of premium #1",
                              "i18NLabelKey": null,
                              "order": 404,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "TOTAL_WEALTH_1",
                              "label": "Total wealth #1",
                              "i18NLabelKey": null,
                              "order": 406,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "1312312123.000",
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
                              "fieldId": "PREMIUM_WITH_UNQ_ASSETS",
                              "label": "Premium with unquoted assets?",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.NumberInputField",
                            {
                              "fieldId": "INITIAL_PREM",
                              "label": "Initial premium (in policy currency)",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0",
                              "displayIf": "#IS_ROP_CASE# == YES",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "min": null,
                              "max": null,
                              "decimals": null,
                              "size": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "INVESTED_IN_ILF",
                              "label": "Will it be reinvested in an ILF?",
                              "i18NLabelKey": null,
                              "order": 5,
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
                              "fieldId": "EXISTING_ILF",
                              "label": "Is it an existing ILF?",
                              "i18NLabelKey": null,
                              "order": 6,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#INVESTED_IN_ILF# == YES",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "ILF_MNEMONIC",
                              "label": "ILF Mnemonic",
                              "i18NLabelKey": null,
                              "order": 7,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#EXISTING_ILF# == YES",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
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
                              "fieldId": "PAYER_IN_SANCTION_LIST",
                              "label": "Is one of the payers designated on a sanctions list?",
                              "i18NLabelKey": null,
                              "order": 10,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "fieldId": "NEGATIVE_FINDING_PAYERS",
                              "label": "Negative press finding / Worldcheck match (on any of the payers)?",
                              "i18NLabelKey": null,
                              "order": 11,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "fieldId": "PAYER_CORPORATE_ENTITY",
                              "label": "Is the payer a Corporate entity where the PH/EBO is the sole shareholder?",
                              "i18NLabelKey": null,
                              "order": 12,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "fieldId": "PAYER_NOT_LOCATED",
                              "label": "Is the 3rd party payer a legal entity located in a country which is not the tax country of residence or place of regular economic or professional activities/interests of the PH/EBO?",
                              "i18NLabelKey": null,
                              "order": 13,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "fieldId": "EVIDENCE_LEGAL_ENTITY",
                              "label": "Did you collect supporting evidence that the legal entity is known by the tax authorities of the country of residence of the PH/EBO or could the legal entity  demonstrate that its establishment complies with the legal provisions of the country of residence of the PH/EBO?",
                              "i18NLabelKey": null,
                              "order": 14,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "false",
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
                              "fieldId": "CLOSE_TO_EBO",
                              "label": "In case of any additional documentation collected to corroborate the tax conformity of the funds (e.g. annual income tax return, the regularisation documentation or memo from the tax lawyer of the PH/EBO), is this  supporting documentation issued by a person who is close to the PH/EBO and leaving room for doubt due to the potential conflict of interest?",
                              "i18NLabelKey": null,
                              "order": 15,
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
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "ECONOMIC_JUSTIF",
                              "label": "Is there an obvious economic justification (e.g. the PH/EBO lived in that jurisdiction, worked and/or works in that jurisdiction, funds were generated in that jurisdiction)?",
                              "i18NLabelKey": null,
                              "order": 17,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "#BANK_NOT_IN_RESIDENCE# == YES",
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
                              "fieldId": "EVIDENCE_TAX_DECLARED",
                              "label": "Did you collect supporting evidence that the bank account is  known by the tax authorities of the country of residence of the PH/EBO or that the funds have been tax declared (e.g. annual income tax , return regularisation documentation)?",
                              "i18NLabelKey": null,
                              "order": 18,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "#BANK_NOT_IN_RESIDENCE# == YES && #ECONOMIC_JUSTIF# == NO",
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
                              "fieldId": "REFUSED_ADDITIONAL_INFO",
                              "label": "Did the PH/EBO refuse to provide this additional supporting documentation?",
                              "i18NLabelKey": null,
                              "order": 19,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "#BANK_NOT_IN_RESIDENCE# == YES && #ECONOMIC_JUSTIF# == NO && #EVIDENCE_TAX_DECLARED# == NO",
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
                              "fieldId": "PREMIUM_RECEIVED_DIFFERENT_EXPECTED",
                              "label": "Is the premium received different than expected one (higher amount or different payment details)?",
                              "i18NLabelKey": null,
                              "order": 20,
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
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "SAME_AS_DISCLOSED",
                              "label": "Is the origin of the funds or additional funds and/or the payment details the same as disclosed in the KYC form?",
                              "i18NLabelKey": null,
                              "order": 21,
                              "enabled": true,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "NO",
                              "displayIf": "#PREMIUM_RECEIVED_DIFFERENT_EXPECTED# == YES",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "RATIONALE_FOR_INVESTMENT",
                              "label": "Rationale for investment",
                              "i18NLabelKey": null,
                              "order": 22,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "sdfsdfsdf",
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
                              "fieldId": "NUMBER_OF_ORIGINATING_ACCOUNTS",
                              "label": "Number of originating accounts",
                              "i18NLabelKey": null,
                              "order": 23,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "1",
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
                                      "key": "1",
                                      "value": "1"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "ORIGINATING_BANK_COUNTRY_RISK",
                              "label": "Riskiest country of originating bank",
                              "i18NLabelKey": null,
                              "order": 200,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FRANCE",
                              "displayIf": "#NUMBER_OF_ORIGINATING_ACCOUNTS# >= 1",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "NAME_OF_ORIGINATING_ACCOUNT_HOLDER_1",
                              "label": "Account holder ID #1",
                              "i18NLabelKey": null,
                              "order": 24,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0003314214",
                              "displayIf": "#NUMBER_OF_ORIGINATING_ACCOUNTS# >= 1",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "COUNTRY_OF_ORIGINATING_ACCOUNT_1",
                              "label": "Country of the originating bank #1",
                              "i18NLabelKey": null,
                              "order": 25,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": "#NUMBER_OF_ORIGINATING_ACCOUNTS# >= 1",
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
                                      "key": "FR",
                                      "value": "FR - France"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "BANK_NAME_OF_ORIGINATING_ACCOUNT_1",
                              "label": "Name of the bank #1",
                              "i18NLabelKey": null,
                              "order": 26,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "bank",
                              "displayIf": "#NUMBER_OF_ORIGINATING_ACCOUNTS# >= 1",
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
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
                      "groupId": "THIRD_PARTY",
                      "title": "Payment from third party",
                      "i18NTitleKey": null,
                      "order": 102,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "THIRD_PARTY_COUNTRY_RISK",
                              "label": "Highest 3rd party risk",
                              "i18NLabelKey": null,
                              "order": 10000,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "STANDARD",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "Highest 3rd party risk from Class",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextAreaField",
                            {
                              "fieldId": "ADDITIONAL_INFO_LINK",
                              "label": "Additional info on link between 3rd party and PH",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": "#LINK_THIRD_PARTY_PH# == other",
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
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "THIRD_PARTY_NAME_1",
                              "label": "3rd party ID #1",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0003314214",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Class",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "LINK_THIRD_PARTY_PH_1",
                              "label": "Link between 3rd party and PH #1",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "policyhold",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Class",
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
                                      "key": "policyhold",
                                      "value": "Policy holder (default)"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "REASON_THIRD_PARTY_PAYMENT_1",
                              "label": "Reason for 3rd party payment #1",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": true,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": null,
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "THIRD_PARTY_COUNTRY_1",
                              "label": "3rd party country #1",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": false,
                              "mandatory": true,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From Class",
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
                                      "key": "FR",
                                      "value": "FR - France"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ]
                        ]
                      ],
                      "displayIf": "#PAYMENT_THIRD_PARTY# == YES"
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
                              "selectedValue": "EBO/originator industry sector :TP 0003314214: Animals and plants trading (wildlife, wild plants, protected species, ivory)\\\\nSanction list\\\\nDistribution Risk",
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
                  ],
                  [
                    "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.Group",
                    {
                      "groupId": "SOURCE_OF_WEALTH_GROUP_1_1",
                      "title": "Due diligence - Originator identification (Inheritance / Gift / Donation / Divorce) - 0003314214",
                      "i18NTitleKey": null,
                      "order": 6,
                      "fields": [
                        "java.util.ArrayList",
                        [
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "WHEN_HAPPENED_1_1",
                              "label": "When the inheritance / Gift / Donation / Divorce happened?",
                              "i18NLabelKey": null,
                              "order": 1,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "01/06/2026",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "ORIGINATOR_NAME_1_1",
                              "label": "Originator ID",
                              "i18NLabelKey": null,
                              "order": 2,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "0003313734",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "LINK_WITH_EBO_1_1",
                              "label": "Link with EBO",
                              "i18NLabelKey": null,
                              "order": 3,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "BROTHER",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                                      "key": "BROTHER",
                                      "value": "Brother/Sister of"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "COMPANY_NAME_1_1",
                              "label": "Company name",
                              "i18NLabelKey": null,
                              "order": 4,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "com",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "ORIGINATOR_INDUSTRY_SECTOR_1_1",
                              "label": "Industry sector",
                              "i18NLabelKey": null,
                              "order": 5,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "agri",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                                      "key": "agri",
                                      "value": "Agriculture (Livestock, Crops, Forestry, etc.)"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.SelectInputField",
                            {
                              "fieldId": "ORIGINATOR_POSITION_1_1",
                              "label": "Position",
                              "i18NLabelKey": null,
                              "order": 6,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "empl",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
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
                                      "key": "empl",
                                      "value": "Employee"
                                    }
                                  ]
                                ]
                              ]
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "COUNTRY_WEALTH_1_RISK_1_1",
                              "label": "Wealth originating country 1",
                              "i18NLabelKey": null,
                              "order": 7,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ],
                          [
                            "com.lombardinternational.casemanagementconnector.domain.casedefinition.entity.TextInputField",
                            {
                              "fieldId": "COUNTRY_WEALTH_2_RISK_1_1",
                              "label": "Wealth originating country 2",
                              "i18NLabelKey": null,
                              "order": 8,
                              "enabled": false,
                              "mandatory": false,
                              "multiple": null,
                              "maxMultiple": null,
                              "selectedValue": "FR",
                              "displayIf": null,
                              "enableIf": null,
                              "labelBold": false,
                              "sourceSystem": "From CLASS",
                              "size": null,
                              "minChars": null,
                              "maxChars": null
                            }
                          ]
                        ]
                      ],
                      "displayIf": "#20_PERCENT_INCOME_1# == YES"
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
  ],
  "policyNumber": "2606-162701",
  "businessTransaction": "15317939",
  "caseRef": "NBD_68219_20260619"
}
