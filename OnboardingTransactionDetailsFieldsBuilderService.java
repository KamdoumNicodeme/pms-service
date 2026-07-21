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
            retries="3" />
      </bpmn:extensionElements>

      <bpmn:incoming>Flow_Start_To_Initialize</bpmn:incoming>
      <bpmn:outgoing>Flow_Initialize_To_CciTask</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:userTask
        id="UserTask_ChangeClientInformation"
        name="Change of Client Information">

      <bpmn:extensionElements>
        <zeebe:assignmentDefinition
            candidateGroups="=cciAssignedGroup" />
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
            retries="3" />
      </bpmn:extensionElements>

      <bpmn:incoming>Flow_CciTask_To_DocumentManagement</bpmn:incoming>
      <bpmn:outgoing>Flow_DocumentManagement_To_End</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:endEvent
        id="EndEvent_CciCompleted"
        name="CCI processing continued">
      <bpmn:incoming>Flow_DocumentManagement_To_End</bpmn:incoming>
    </bpmn:endEvent>

    <bpmn:sequenceFlow
        id="Flow_Start_To_Initialize"
        sourceRef="StartEvent_Cci"
        targetRef="Task_InitializeCciCase" />

    <bpmn:sequenceFlow
        id="Flow_Initialize_To_CciTask"
        sourceRef="Task_InitializeCciCase"
        targetRef="UserTask_ChangeClientInformation" />

    <bpmn:sequenceFlow
        id="Flow_CciTask_To_DocumentManagement"
        sourceRef="UserTask_ChangeClientInformation"
        targetRef="Task_DocumentManagement" />

    <bpmn:sequenceFlow
        id="Flow_DocumentManagement_To_End"
        sourceRef="Task_DocumentManagement"
        targetRef="EndEvent_CciCompleted" />

  </bpmn:process>

  <bpmndi:BPMNDiagram id="BPMNDiagram_ChangeClientInformation">

    <bpmndi:BPMNPlane
        id="BPMNPlane_ChangeClientInformation"
        bpmnElement="change-client-information-process">

      <bpmndi:BPMNShape
          id="Shape_StartEvent_Cci"
          bpmnElement="StartEvent_Cci">
        <dc:Bounds x="150" y="180" width="36" height="36" />
      </bpmndi:BPMNShape>

      <bpmndi:BPMNShape
          id="Shape_Task_InitializeCciCase"
          bpmnElement="Task_InitializeCciCase">
        <dc:Bounds x="240" y="158" width="140" height="80" />
      </bpmndi:BPMNShape>

      <bpmndi:BPMNShape
          id="Shape_UserTask_ChangeClientInformation"
          bpmnElement="UserTask_ChangeClientInformation">
        <dc:Bounds x="440" y="158" width="180" height="80" />
      </bpmndi:BPMNShape>

      <bpmndi:BPMNShape
          id="Shape_Task_DocumentManagement"
          bpmnElement="Task_DocumentManagement">
        <dc:Bounds x="680" y="158" width="150" height="80" />
      </bpmndi:BPMNShape>

      <bpmndi:BPMNShape
          id="Shape_EndEvent_CciCompleted"
          bpmnElement="EndEvent_CciCompleted">
        <dc:Bounds x="890" y="180" width="36" height="36" />
      </bpmndi:BPMNShape>

      <bpmndi:BPMNEdge
          id="Edge_Flow_Start_To_Initialize"
          bpmnElement="Flow_Start_To_Initialize">
        <di:waypoint x="186" y="198" />
        <di:waypoint x="240" y="198" />
      </bpmndi:BPMNEdge>

      <bpmndi:BPMNEdge
          id="Edge_Flow_Initialize_To_CciTask"
          bpmnElement="Flow_Initialize_To_CciTask">
        <di:waypoint x="380" y="198" />
        <di:waypoint x="440" y="198" />
      </bpmndi:BPMNEdge>

      <bpmndi:BPMNEdge
          id="Edge_Flow_CciTask_To_DocumentManagement"
          bpmnElement="Flow_CciTask_To_DocumentManagement">
        <di:waypoint x="620" y="198" />
        <di:waypoint x="680" y="198" />
      </bpmndi:BPMNEdge>

      <bpmndi:BPMNEdge
          id="Edge_Flow_DocumentManagement_To_End"
          bpmnElement="Flow_DocumentManagement_To_End">
        <di:waypoint x="830" y="198" />
        <di:waypoint x="890" y="198" />
      </bpmndi:BPMNEdge>

    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>

</bpmn:definitions>
