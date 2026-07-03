package com.lombardinternational.casemanagement.service.businesscase.domain.service;

import static com.lombardinternational.casemanagement.service.businesscase.domain.constant.CaseConstant.*;

import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lombardinternational.casemanagement.service.businesscase.adapter.spi.db.mapper.ScreenDescriptionCdmMapper;
import com.lombardinternational.casemanagement.service.businesscase.adapter.spi.db.mapper.WebFormCdmMapper;
import com.lombardinternational.casemanagement.service.businesscase.domain.api.CaseDecisionService;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.CaseType;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.filter.CaseDynamicScreenDataFilter;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.CaseDynamicScreenData;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.CheckListRequest;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.ControlDefinition;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.ControlDefinitionRequest;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.DocumentDefinition;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.DocumentDefinitionRequest;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.TaskDefinition;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.TaskListRequest;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.TaskScreenRequest;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.screen.ScreenDescription;
import com.lombardinternational.casemanagement.service.businesscase.domain.entity.rules.webform.WebForm;
import com.lombardinternational.casemanagement.service.businesscase.domain.exception.ParseCdmEntityException;
import com.lombardinternational.casemanagement.service.businesscase.domain.spi.db.CaseDynamicScreenDataRepositoryService;
import com.lombardinternational.casemanagement.service.businesscase.domain.spi.rules.CaseDecisionRulesService;

import lombok.AllArgsConstructor;
import lu.fujitsu.ts.eportal.server.core.exceptions.NotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class CaseDecisionServiceImpl implements CaseDecisionService {

    private final CaseDecisionRulesService rulesService;
    private final CaseDynamicScreenDataRepositoryService repositoryService;
    private final WebFormCdmMapper webFormCdmMapper;
    private final ScreenDescriptionCdmMapper screenDescriptionCdmMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ScreenDescription loadCheckList(final String screenId, final CaseType caseType, final String policyNumber,
            final String transactionNumber, final String caseRef, JsonNode screenDescription) throws NotFoundException, ParseCdmEntityException {

        var screen = new ScreenDescription();
        if (screenDescription == null) {
            screen.setScreenId(screenId);
        } else {
            screen = mapJsonToScreenDescription(screenDescription);
        }

        var webForm = getWebFormFromDynamicScreen(caseRef);

        var request = new CheckListRequest(screen, webForm, policyNumber, transactionNumber);

        return rulesService.generateCheckList(request);
    }

    @Override
    public ScreenDescription loadTaskScreen(final String screenId, final CaseType caseType, final String caseRef, String checklistScreenId,
            JsonNode screenCheckList, JsonNode screenToFill) throws NotFoundException, ParseCdmEntityException {

        var checklist = new ScreenDescription();
        if (screenCheckList == null) {
            checklist.setScreenId(checklistScreenId);
        } else {
            checklist = mapJsonToScreenDescription(screenCheckList);
        }

        var screen = new ScreenDescription();
        if (screenToFill == null) {
            screen.setScreenId(screenId);
        } else {
            screen = mapJsonToScreenDescription(screenToFill);
        }

        var webForm = getWebFormFromDynamicScreen(caseRef);

        var request = new TaskScreenRequest(webForm, checklist, screen);
        return rulesService.generateTaskScreen(request);
    }

    @Override
    public List<DocumentDefinition> loadDocumentsDefinition(final CaseType caseType, final String policyNumber, final String transactionNumber,
            final String caseRef, final JsonNode checkListOverview) throws NotFoundException, ParseCdmEntityException {

        var webForm = getWebFormFromDynamicScreen(caseRef);

        var checklist = new ScreenDescription();
        if (checkListOverview != null) {
            checklist = mapJsonToScreenDescription(checkListOverview);

        }

        var request = new DocumentDefinitionRequest(webForm, checklist, policyNumber, transactionNumber);
        return rulesService.generateDocumentsDefinition(request);
    }

    @Override
    public List<TaskDefinition> loadTaskList(final CaseType caseType, final String policyNumber, final String caseRef,
            final JsonNode checkListOverview) throws NotFoundException, ParseCdmEntityException {

        var webForm = getWebFormFromDynamicScreen(caseRef);

        var checklist = new ScreenDescription();
        if (checkListOverview != null) {
            checklist = mapJsonToScreenDescription(checkListOverview);

        }

        var request = new TaskListRequest(webForm, checklist, policyNumber);
        return rulesService.generateTaskList(request);
    }

    @Override
    public List<ControlDefinition> loadControlDefinition(final CaseType caseType, final String caseRef, final JsonNode checkListOverview)
            throws NotFoundException, ParseCdmEntityException {

        var webForm = getWebFormFromDynamicScreen(caseRef);

        var checklist = new ScreenDescription();
        if (checkListOverview != null) {
            checklist = mapJsonToScreenDescription(checkListOverview);

        }

        var request = new ControlDefinitionRequest(webForm, checklist);
        return rulesService.generateControlDefinition(request);
    }

    private WebForm getWebFormFromDynamicScreen(String caseRef) throws NotFoundException, ParseCdmEntityException {

        var filter = new CaseDynamicScreenDataFilter(List.of(caseRef), WEBFORM_DATA_ID);
        CaseDynamicScreenData dynamicScreenData = repositoryService.findOne(filter).orElseThrow(NotFoundException::new);

        try {
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
            JsonNode node = mapper.readTree(dynamicScreenData.getWebForm());
            if (node.get(0) != null && Objects.equals(node.get(0).asText(), com.lombard.cdm.WebForm.class.getCanonicalName())) {
                com.lombard.cdm.WebForm cdmWebForm = mapper.readValue(dynamicScreenData.getWebForm(), com.lombard.cdm.WebForm.class);
                return webFormCdmMapper.fromCdm(cdmWebForm);
            } else {
                mapper.deactivateDefaultTyping();
                return mapper.readValue(dynamicScreenData.getWebForm(), WebForm.class);
            }
        } catch (JsonProcessingException e) {
            throw new ParseCdmEntityException("WebForm in json format cannot be parsed to WebForm", e);
        }
    }

    private ScreenDescription mapJsonToScreenDescription(JsonNode screenDescription) throws ParseCdmEntityException {

        try {
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

            if (screenDescription.get(0) != null
                    && Objects.equals(screenDescription.get(0).asText(), com.lombard.cdm.ScreenDescription.class.getCanonicalName())) {
                com.lombard.cdm.ScreenDescription cdmScreenDescription =
                        mapper.treeToValue(screenDescription, com.lombard.cdm.ScreenDescription.class);
                return screenDescriptionCdmMapper.fromCdm(cdmScreenDescription);
            } else {
                return mapper.treeToValue(screenDescription, ScreenDescription.class);
            }
        } catch (JsonProcessingException e) {
            throw new ParseCdmEntityException("ScreenDescription in json format cannot be parsed to ScreenDescription", e);
        }
    }
}
