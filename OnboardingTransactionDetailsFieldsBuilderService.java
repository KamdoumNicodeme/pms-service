<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL"
                  xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
                  xmlns:dc="http://www.omg.org/spec/DD/20100524/DC"
                  xmlns:di="http://www.omg.org/spec/DD/20100524/DI"
                  xmlns:zeebe="http://camunda.org/schema/zeebe/1.0"
                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  id="Definitions_PoliceAssurance"
                  targetNamespace="http://bpmn.io/schema/bpmn"
                  exporter="Camunda Modeler" exporterVersion="5.x">

  <!-- ======================= MESSAGES ======================= -->
  <bpmn:message id="Message_Avenant" name="demande-avenant">
    <bpmn:extensionElements>
      <zeebe:subscription correlationKey="=numeroPolice" />
    </bpmn:extensionElements>
  </bpmn:message>
  <bpmn:message id="Message_Sinistre" name="declaration-sinistre">
    <bpmn:extensionElements>
      <zeebe:subscription correlationKey="=numeroPolice" />
    </bpmn:extensionElements>
  </bpmn:message>
  <bpmn:message id="Message_Resiliation" name="demande-resiliation">
    <bpmn:extensionElements>
      <zeebe:subscription correlationKey="=numeroPolice" />
    </bpmn:extensionElements>
  </bpmn:message>

  <!-- ======================= PROCESS ======================= -->
  <bpmn:process id="Process_PoliceAssurance" name="Cycle de vie d'une police d'assurance" isExecutable="true">

    <!-- Souscription -->
    <bpmn:startEvent id="StartEvent_Demande" name="Demande de souscription re&#231;ue">
      <bpmn:outgoing>Flow_01</bpmn:outgoing>
    </bpmn:startEvent>

    <bpmn:serviceTask id="Task_Enregistrer" name="Enregistrer la demande">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="enregistrer-demande" />
        <zeebe:ioMapping>
          <zeebe:output source="=numeroPolice" target="numeroPolice" />
        </zeebe:ioMapping>
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_01</bpmn:incoming>
      <bpmn:outgoing>Flow_02</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:userTask id="Task_Verifier" name="V&#233;rifier le dossier client">
      <bpmn:extensionElements>
        <zeebe:userTask />
        <zeebe:assignmentDefinition candidateGroups="gestionnaires" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_02</bpmn:incoming>
      <bpmn:outgoing>Flow_03</bpmn:outgoing>
    </bpmn:userTask>

    <bpmn:serviceTask id="Task_Evaluer" name="&#201;valuer le risque">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="evaluer-risque" />
        <zeebe:ioMapping>
          <zeebe:output source="=body.decision" target="decision" />
        </zeebe:ioMapping>
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_03</bpmn:incoming>
      <bpmn:outgoing>Flow_04</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:exclusiveGateway id="Gateway_Decision" name="Souscription accept&#233;e ?" default="Flow_Refuse">
      <bpmn:incoming>Flow_04</bpmn:incoming>
      <bpmn:outgoing>Flow_Accepte</bpmn:outgoing>
      <bpmn:outgoing>Flow_Refuse</bpmn:outgoing>
    </bpmn:exclusiveGateway>

    <bpmn:serviceTask id="Task_Calculer" name="Calculer la prime">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="calculer-prime" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_Accepte</bpmn:incoming>
      <bpmn:outgoing>Flow_05</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:serviceTask id="Task_Emettre" name="&#201;mettre la police">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="emettre-police" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_05</bpmn:incoming>
      <bpmn:outgoing>Flow_06</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:serviceTask id="Task_NotifierRefus" name="Notifier le refus">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="notifier-refus" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_Refuse</bpmn:incoming>
      <bpmn:outgoing>Flow_07</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:endEvent id="EndEvent_Refus" name="Souscription refus&#233;e">
      <bpmn:incoming>Flow_07</bpmn:incoming>
    </bpmn:endEvent>

    <!-- Gestion du cycle de vie (police en vigueur) -->
    <bpmn:eventBasedGateway id="Gateway_Cycle" name="Attente d'un &#233;v&#233;nement du cycle de vie">
      <bpmn:incoming>Flow_06</bpmn:incoming>
      <bpmn:incoming>Flow_09</bpmn:incoming>
      <bpmn:incoming>Flow_11</bpmn:incoming>
      <bpmn:incoming>Flow_13</bpmn:incoming>
      <bpmn:outgoing>Flow_ToAvenant</bpmn:outgoing>
      <bpmn:outgoing>Flow_ToSinistre</bpmn:outgoing>
      <bpmn:outgoing>Flow_ToEcheance</bpmn:outgoing>
      <bpmn:outgoing>Flow_ToResiliation</bpmn:outgoing>
    </bpmn:eventBasedGateway>

    <!-- Avenant -->
    <bpmn:intermediateCatchEvent id="Event_Avenant" name="Demande d'avenant">
      <bpmn:incoming>Flow_ToAvenant</bpmn:incoming>
      <bpmn:outgoing>Flow_08</bpmn:outgoing>
      <bpmn:messageEventDefinition id="MED_Avenant" messageRef="Message_Avenant" />
    </bpmn:intermediateCatchEvent>
    <bpmn:userTask id="Task_TraiterAvenant" name="Traiter l'avenant">
      <bpmn:extensionElements>
        <zeebe:userTask />
        <zeebe:assignmentDefinition candidateGroups="gestionnaires" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_08</bpmn:incoming>
      <bpmn:outgoing>Flow_09</bpmn:outgoing>
    </bpmn:userTask>

    <!-- Sinistre -->
    <bpmn:intermediateCatchEvent id="Event_Sinistre" name="D&#233;claration de sinistre">
      <bpmn:incoming>Flow_ToSinistre</bpmn:incoming>
      <bpmn:outgoing>Flow_10</bpmn:outgoing>
      <bpmn:messageEventDefinition id="MED_Sinistre" messageRef="Message_Sinistre" />
    </bpmn:intermediateCatchEvent>
    <bpmn:userTask id="Task_GererSinistre" name="G&#233;rer le sinistre">
      <bpmn:extensionElements>
        <zeebe:userTask />
        <zeebe:assignmentDefinition candidateGroups="gestionnaires-sinistres" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_10</bpmn:incoming>
      <bpmn:outgoing>Flow_11</bpmn:outgoing>
    </bpmn:userTask>

    <!-- &#201;ch&#233;ance / renouvellement -->
    <bpmn:intermediateCatchEvent id="Event_Echeance" name="&#201;ch&#233;ance annuelle">
      <bpmn:incoming>Flow_ToEcheance</bpmn:incoming>
      <bpmn:outgoing>Flow_12</bpmn:outgoing>
      <bpmn:timerEventDefinition id="TED_Echeance">
        <bpmn:timeDuration xsi:type="bpmn:tFormalExpression">P1Y</bpmn:timeDuration>
      </bpmn:timerEventDefinition>
    </bpmn:intermediateCatchEvent>

    <bpmn:exclusiveGateway id="Gateway_Renouvellement" name="Renouveler la police ?" default="Flow_NonRenouv">
      <bpmn:incoming>Flow_12</bpmn:incoming>
      <bpmn:outgoing>Flow_Renouv</bpmn:outgoing>
      <bpmn:outgoing>Flow_NonRenouv</bpmn:outgoing>
    </bpmn:exclusiveGateway>

    <bpmn:serviceTask id="Task_Renouveler" name="Renouveler la police">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="renouveler-police" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_Renouv</bpmn:incoming>
      <bpmn:outgoing>Flow_13</bpmn:outgoing>
    </bpmn:serviceTask>

    <bpmn:endEvent id="EndEvent_Expiree" name="Police expir&#233;e">
      <bpmn:incoming>Flow_NonRenouv</bpmn:incoming>
    </bpmn:endEvent>

    <!-- R&#233;siliation -->
    <bpmn:intermediateCatchEvent id="Event_Resiliation" name="Demande de r&#233;siliation">
      <bpmn:incoming>Flow_ToResiliation</bpmn:incoming>
      <bpmn:outgoing>Flow_14</bpmn:outgoing>
      <bpmn:messageEventDefinition id="MED_Resiliation" messageRef="Message_Resiliation" />
    </bpmn:intermediateCatchEvent>
    <bpmn:userTask id="Task_Resilier" name="R&#233;silier la police">
      <bpmn:extensionElements>
        <zeebe:userTask />
        <zeebe:assignmentDefinition candidateGroups="gestionnaires" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_14</bpmn:incoming>
      <bpmn:outgoing>Flow_15</bpmn:outgoing>
    </bpmn:userTask>
    <bpmn:endEvent id="EndEvent_Resiliee" name="Police r&#233;sili&#233;e">
      <bpmn:incoming>Flow_15</bpmn:incoming>
    </bpmn:endEvent>

    <!-- ======================= SEQUENCE FLOWS ======================= -->
    <bpmn:sequenceFlow id="Flow_01" sourceRef="StartEvent_Demande" targetRef="Task_Enregistrer" />
    <bpmn:sequenceFlow id="Flow_02" sourceRef="Task_Enregistrer" targetRef="Task_Verifier" />
    <bpmn:sequenceFlow id="Flow_03" sourceRef="Task_Verifier" targetRef="Task_Evaluer" />
    <bpmn:sequenceFlow id="Flow_04" sourceRef="Task_Evaluer" targetRef="Gateway_Decision" />
    <bpmn:sequenceFlow id="Flow_Accepte" name="Accept&#233;e" sourceRef="Gateway_Decision" targetRef="Task_Calculer">
      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression">=decision = "ACCEPTE"</bpmn:conditionExpression>
    </bpmn:sequenceFlow>
    <bpmn:sequenceFlow id="Flow_Refuse" name="Refus&#233;e" sourceRef="Gateway_Decision" targetRef="Task_NotifierRefus" />
    <bpmn:sequenceFlow id="Flow_05" sourceRef="Task_Calculer" targetRef="Task_Emettre" />
    <bpmn:sequenceFlow id="Flow_06" sourceRef="Task_Emettre" targetRef="Gateway_Cycle" />
    <bpmn:sequenceFlow id="Flow_07" sourceRef="Task_NotifierRefus" targetRef="EndEvent_Refus" />
    <bpmn:sequenceFlow id="Flow_ToAvenant" sourceRef="Gateway_Cycle" targetRef="Event_Avenant" />
    <bpmn:sequenceFlow id="Flow_ToSinistre" sourceRef="Gateway_Cycle" targetRef="Event_Sinistre" />
    <bpmn:sequenceFlow id="Flow_ToEcheance" sourceRef="Gateway_Cycle" targetRef="Event_Echeance" />
    <bpmn:sequenceFlow id="Flow_ToResiliation" sourceRef="Gateway_Cycle" targetRef="Event_Resiliation" />
    <bpmn:sequenceFlow id="Flow_08" sourceRef="Event_Avenant" targetRef="Task_TraiterAvenant" />
    <bpmn:sequenceFlow id="Flow_09" sourceRef="Task_TraiterAvenant" targetRef="Gateway_Cycle" />
    <bpmn:sequenceFlow id="Flow_10" sourceRef="Event_Sinistre" targetRef="Task_GererSinistre" />
    <bpmn:sequenceFlow id="Flow_11" sourceRef="Task_GererSinistre" targetRef="Gateway_Cycle" />
    <bpmn:sequenceFlow id="Flow_12" sourceRef="Event_Echeance" targetRef="Gateway_Renouvellement" />
    <bpmn:sequenceFlow id="Flow_Renouv" name="Oui" sourceRef="Gateway_Renouvellement" targetRef="Task_Renouveler">
      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression">=renouvellement = true</bpmn:conditionExpression>
    </bpmn:sequenceFlow>
    <bpmn:sequenceFlow id="Flow_NonRenouv" name="Non" sourceRef="Gateway_Renouvellement" targetRef="EndEvent_Expiree" />
    <bpmn:sequenceFlow id="Flow_13" sourceRef="Task_Renouveler" targetRef="Gateway_Cycle" />
    <bpmn:sequenceFlow id="Flow_14" sourceRef="Event_Resiliation" targetRef="Task_Resilier" />
    <bpmn:sequenceFlow id="Flow_15" sourceRef="Task_Resilier" targetRef="EndEvent_Resiliee" />
  </bpmn:process>

  <!-- ======================= DIAGRAM (DI) ======================= -->
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_PoliceAssurance">

      <!-- Shapes -->
      <bpmndi:BPMNShape id="StartEvent_Demande_di" bpmnElement="StartEvent_Demande">
        <dc:Bounds x="172" y="192" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="150" y="235" width="82" height="27" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_Enregistrer_di" bpmnElement="Task_Enregistrer">
        <dc:Bounds x="240" y="170" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_Verifier_di" bpmnElement="Task_Verifier">
        <dc:Bounds x="400" y="170" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_Evaluer_di" bpmnElement="Task_Evaluer">
        <dc:Bounds x="560" y="170" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Gateway_Decision_di" bpmnElement="Gateway_Decision" isMarkerVisible="true">
        <dc:Bounds x="725" y="185" width="50" height="50" />
        <bpmndi:BPMNLabel><dc:Bounds x="712" y="155" width="76" height="27" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_Calculer_di" bpmnElement="Task_Calculer">
        <dc:Bounds x="830" y="170" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_Emettre_di" bpmnElement="Task_Emettre">
        <dc:Bounds x="990" y="170" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Gateway_Cycle_di" bpmnElement="Gateway_Cycle">
        <dc:Bounds x="1155" y="185" width="50" height="50" />
        <bpmndi:BPMNLabel><dc:Bounds x="1145" y="242" width="70" height="40" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_NotifierRefus_di" bpmnElement="Task_NotifierRefus">
        <dc:Bounds x="700" y="320" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="EndEvent_Refus_di" bpmnElement="EndEvent_Refus">
        <dc:Bounds x="860" y="342" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="840" y="385" width="78" height="27" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_Avenant_di" bpmnElement="Event_Avenant">
        <dc:Bounds x="1262" y="72" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="1246" y="45" width="72" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_TraiterAvenant_di" bpmnElement="Task_TraiterAvenant">
        <dc:Bounds x="1360" y="50" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_Sinistre_di" bpmnElement="Event_Sinistre">
        <dc:Bounds x="1262" y="172" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="1244" y="145" width="74" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_GererSinistre_di" bpmnElement="Task_GererSinistre">
        <dc:Bounds x="1360" y="150" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_Echeance_di" bpmnElement="Event_Echeance">
        <dc:Bounds x="1262" y="282" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="1246" y="322" width="72" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Gateway_Renouvellement_di" bpmnElement="Gateway_Renouvellement" isMarkerVisible="true">
        <dc:Bounds x="1360" y="275" width="50" height="50" />
        <bpmndi:BPMNLabel><dc:Bounds x="1348" y="332" width="76" height="27" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_Renouveler_di" bpmnElement="Task_Renouveler">
        <dc:Bounds x="1470" y="260" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="EndEvent_Expiree_di" bpmnElement="EndEvent_Expiree">
        <dc:Bounds x="1367" y="420" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="1352" y="463" width="66" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_Resiliation_di" bpmnElement="Event_Resiliation">
        <dc:Bounds x="1262" y="402" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="1246" y="442" width="72" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Task_Resilier_di" bpmnElement="Task_Resilier">
        <dc:Bounds x="1360" y="380" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="EndEvent_Resiliee_di" bpmnElement="EndEvent_Resiliee">
        <dc:Bounds x="1522" y="402" width="36" height="36" />
        <bpmndi:BPMNLabel><dc:Bounds x="1505" y="445" width="70" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>

      <!-- Edges -->
      <bpmndi:BPMNEdge id="Flow_01_di" bpmnElement="Flow_01">
        <di:waypoint x="208" y="210" /><di:waypoint x="240" y="210" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_02_di" bpmnElement="Flow_02">
        <di:waypoint x="340" y="210" /><di:waypoint x="400" y="210" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_03_di" bpmnElement="Flow_03">
        <di:waypoint x="500" y="210" /><di:waypoint x="560" y="210" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_04_di" bpmnElement="Flow_04">
        <di:waypoint x="660" y="210" /><di:waypoint x="725" y="210" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_Accepte_di" bpmnElement="Flow_Accepte">
        <di:waypoint x="775" y="210" /><di:waypoint x="830" y="210" />
        <bpmndi:BPMNLabel><dc:Bounds x="783" y="192" width="45" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_Refuse_di" bpmnElement="Flow_Refuse">
        <di:waypoint x="750" y="235" /><di:waypoint x="750" y="320" />
        <bpmndi:BPMNLabel><dc:Bounds x="756" y="268" width="40" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_05_di" bpmnElement="Flow_05">
        <di:waypoint x="930" y="210" /><di:waypoint x="990" y="210" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_06_di" bpmnElement="Flow_06">
        <di:waypoint x="1090" y="210" /><di:waypoint x="1155" y="210" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_07_di" bpmnElement="Flow_07">
        <di:waypoint x="800" y="360" /><di:waypoint x="860" y="360" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_ToAvenant_di" bpmnElement="Flow_ToAvenant">
        <di:waypoint x="1180" y="185" /><di:waypoint x="1180" y="90" /><di:waypoint x="1262" y="90" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_ToSinistre_di" bpmnElement="Flow_ToSinistre">
        <di:waypoint x="1205" y="190" /><di:waypoint x="1262" y="190" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_ToEcheance_di" bpmnElement="Flow_ToEcheance">
        <di:waypoint x="1205" y="210" /><di:waypoint x="1240" y="210" /><di:waypoint x="1240" y="300" /><di:waypoint x="1262" y="300" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_ToResiliation_di" bpmnElement="Flow_ToResiliation">
        <di:waypoint x="1205" y="225" /><di:waypoint x="1255" y="225" /><di:waypoint x="1255" y="420" /><di:waypoint x="1262" y="420" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_08_di" bpmnElement="Flow_08">
        <di:waypoint x="1298" y="90" /><di:waypoint x="1360" y="90" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_09_di" bpmnElement="Flow_09">
        <di:waypoint x="1460" y="90" /><di:waypoint x="1600" y="90" /><di:waypoint x="1600" y="520" /><di:waypoint x="1180" y="520" /><di:waypoint x="1180" y="235" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_10_di" bpmnElement="Flow_10">
        <di:waypoint x="1298" y="190" /><di:waypoint x="1360" y="190" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_11_di" bpmnElement="Flow_11">
        <di:waypoint x="1460" y="190" /><di:waypoint x="1620" y="190" /><di:waypoint x="1620" y="545" /><di:waypoint x="1180" y="545" /><di:waypoint x="1180" y="235" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_12_di" bpmnElement="Flow_12">
        <di:waypoint x="1298" y="300" /><di:waypoint x="1360" y="300" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_Renouv_di" bpmnElement="Flow_Renouv">
        <di:waypoint x="1410" y="300" /><di:waypoint x="1470" y="300" />
        <bpmndi:BPMNLabel><dc:Bounds x="1432" y="282" width="18" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_NonRenouv_di" bpmnElement="Flow_NonRenouv">
        <di:waypoint x="1385" y="325" /><di:waypoint x="1385" y="420" />
        <bpmndi:BPMNLabel><dc:Bounds x="1391" y="360" width="20" height="14" /></bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_13_di" bpmnElement="Flow_13">
        <di:waypoint x="1570" y="300" /><di:waypoint x="1640" y="300" /><di:waypoint x="1640" y="570" /><di:waypoint x="1180" y="570" /><di:waypoint x="1180" y="235" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_14_di" bpmnElement="Flow_14">
        <di:waypoint x="1298" y="420" /><di:waypoint x="1360" y="420" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_15_di" bpmnElement="Flow_15">
        <di:waypoint x="1460" y="420" /><di:waypoint x="1522" y="420" />
      </bpmndi:BPMNEdge>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn:definitions>
