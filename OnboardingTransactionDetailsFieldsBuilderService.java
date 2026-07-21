<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions
        xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL"
        xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
        xmlns:dc="http://www.omg.org/spec/DD/20100524/DC"
        xmlns:di="http://www.omg.org/spec/DD/20100524/DI"
        xmlns:zeebe="http://camunda.org/schema/zeebe/1.0"
        id="Definitions_ChangeClientInformation"
        targetNamespace="http://utmost.com/case-management/cci">

    <bpmn:process
            id="change-client-information-process"
            name="Change of Client Information"
            isExecutable="true">

        <bpmn:startEvent
                id="StartEvent_Cci"
                name="CCI case initiated">
            <bpmn:outgoing>Flow_Start_To_Initialize</bpmn:outgoing>
        </bpmn:startEvent>

        <bpmn:serviceTask
                id="Task_InitializeCciCase"
                name="Initialize CCI case">
            <bpmn:extensionElements>
                <zeebe:taskDefinition
                        type="initialize-change-client-information"
                        retries="3"/>
            </bpmn:extensionElements>

            <bpmn:incoming>Flow_Start_To_Initialize</bpmn:incoming>
            <bpmn:outgoing>Flow_Initialize_To_CciTask</bpmn:outgoing>
        </bpmn:serviceTask>

        <bpmn:userTask
                id="UserTask_ChangeClientInformation"
                name="Change of Client Information">
            <bpmn:extensionElements>

                <!-- Used by the worker/API handling the user task -->
                <zeebe:taskDefinition
                        type="change-client-information"
                        retries="3"/>

                <!-- Assignment expression to be configured by the routing ticket -->
                <zeebe:assignmentDefinition
                        candidateGroups="=cciAssignedGroup"/>

                <!-- Optional reference to the CLIP screen -->
                <zeebe:formDefinition
                        formKey="camunda-forms:bpmn:change-client-information-form"/>

            </bpmn:extensionElements>

            <bpmn:incoming>Flow_Initialize_To_CciTask</bpmn:incoming>
            <bpmn:outgoing>Flow_CciTask_To_DocumentManagement</bpmn:outgoing>
        </bpmn:userTask>

        <bpmn:serviceTask
                id="Task_DocumentManagement"
                name="Document Management">
            <bpmn:extensionElements>
                <zeebe:taskDefinition
                        type="cci-document-management"
                        retries="3"/>
            </bpmn:extensionElements>

            <bpmn:incoming>Flow_CciTask_To_DocumentManagement</bpmn:incoming>
            <bpmn:outgoing>Flow_DocumentManagement_To_End</bpmn:outgoing>
        </bpmn:serviceTask>

        <bpmn:endEvent
                id="EndEvent_CciTaskCompleted"
                name="CCI task completed">
            <bpmn:incoming>Flow_DocumentManagement_To_End</bpmn:incoming>
        </bpmn:endEvent>

        <bpmn:sequenceFlow
                id="Flow_Start_To_Initialize"
                sourceRef="StartEvent_Cci"
                targetRef="Task_InitializeCciCase"/>

        <bpmn:sequenceFlow
                id="Flow_Initialize_To_CciTask"
                sourceRef="Task_InitializeCciCase"
                targetRef="UserTask_ChangeClientInformation"/>

        <bpmn:sequenceFlow
                id="Flow_CciTask_To_DocumentManagement"
                sourceRef="UserTask_ChangeClientInformation"
                targetRef="Task_DocumentManagement"/>

        <bpmn:sequenceFlow
                id="Flow_DocumentManagement_To_End"
                sourceRef="Task_DocumentManagement"
                targetRef="EndEvent_CciTaskCompleted"/>

    </bpmn:process>
</bpmn:definitions>
