window.refreshTabPageContent = function () {
	// Load and render sub-tasks datagrid
	$("#7E02F8C5173B475BA476D184D1D25DBA-btnEl").click();	
}

window.setMustBeRecalculated = function() {
	window.mustBeRecalculated = true;
}

window.validateTasksTab = function () {
	var mandatoryTasksDone = [];
	var tasks = window.dgSubTasks.getAll();

	if(window.mustBeRecalculated == true) {
		alert("Please open the tasks tab, it must be recalculated.");
		return false;
	}

	// In case of dealing : ignore mandatory and completeRequired flags
	if ($.trim($("#CASE_SUBTASKS-lblCaseRef").text()).substring(0, 3).toUpperCase() == 'DRF') {
		console.log('Dealing case, mandatory and completeRequired flags ignored');
		return true;
	}

	// Mandatory tasks should have been done at least once
	var mandatoryStatuses = ['COMPLETED', 'NOT APPLICABLE'];
	var completeStatuses = ['COMPLETED', 'NOT APPLICABLE', 'NOT STARTED', 'MISSING INFORMATION', 'REJECTED', 'TERMINATED'];
	
	for(var j = 0; j < tasks.length; j++){
		var status = $.trim(tasks[j].status).toUpperCase();
		if(tasks[j].mandatory == true && mandatoryStatuses.indexOf(status) != -1) {
			mandatoryTasksDone[tasks[j].taskName] = "done";
		}
		if(tasks[j].completeRequired && completeStatuses.indexOf(status) == -1) {
			alert("The task " + tasks[j].taskName + " is still active." );
			return false;
		}
	}

	for (j = 0; j < tasks.length; j++){
		if (tasks[j].mandatory == true && mandatoryTasksDone[tasks[j].taskName] == null) {
			alert("The mandatory task " + tasks[j].taskName + " has not been completed.");
			return false;
		}
	}

	return true;
}

window.renderDatagridSubTasksData = function(rulesTasksJSON, savedTasksJSON, placeHolderDivId, subTaskMode, currentTaskJobId) {
   window.mustBeRecalculated = false;
	var merged = [];
	var mergedTask = null;
	if( currentTaskJobId==null) {
		 currentTaskJobId= "";
	}
	 
	// Load on the saved data
	for(var i=0;i<savedTasksJSON.length;i++) {
		if( currentTaskJobId != savedTasksJSON[i].fragmentJobId ) { // Do not show the task beeing displayed 
			mergedTask = new Object();
			merged.push(mergedTask); 
			
			// Copy the fields from savedTasksJSON
			mergedTask.suggested = false;
			mergedTask.mandatory = false;
			mergedTask.fragmentJobId = savedTasksJSON[i].fragmentJobId;
			mergedTask.initiatorComment = savedTasksJSON[i].initiatorComment;
			mergedTask.initiatorId = savedTasksJSON[i].initiatorId;
			mergedTask.initiatorName = savedTasksJSON[i].initiatorName;
			mergedTask.mainJobId = savedTasksJSON[i].mainJobId;
			mergedTask.mainTaskAssigneeId = savedTasksJSON[i].mainTaskAssigneeId;
			mergedTask.mainTaskAssigneeName = savedTasksJSON[i].mainTaskAssigneeName;
			mergedTask.mainTaskComment = savedTasksJSON[i].mainTaskComment;		
			mergedTask.mainTaskcompletionDate = savedTasksJSON[i].mainTaskcompletionDate;
			mergedTask.signOffTaskAssigneeId = savedTasksJSON[i].signOffTaskAssigneeId;
			mergedTask.signOffTaskAssigneeName = savedTasksJSON[i].signOffTaskAssigneeName;
			mergedTask.signOffTaskComment = savedTasksJSON[i].signOffTaskComment;
			mergedTask.signOffTaskDynamicScreenId = savedTasksJSON[i].signOffTaskDynamicScreenId;
			mergedTask.signOffTaskcompletionDate = savedTasksJSON[i].signOffTaskcompletionDate;
			mergedTask.signOff2TaskAssigneeId = savedTasksJSON[i].signOff2TaskAssigneeId;
			mergedTask.signOff2TaskAssigneeName = savedTasksJSON[i].signOff2TaskAssigneeName;
			mergedTask.signOff2TaskComment = savedTasksJSON[i].signOff2TaskComment;
			mergedTask.signOff2TaskDynamicScreenId = savedTasksJSON[i].signOff2TaskDynamicScreenId;
			mergedTask.signOff2TaskcompletionDate = savedTasksJSON[i].signOff2TaskcompletionDate;
			mergedTask.startDate = savedTasksJSON[i].startDate;
			mergedTask.status = savedTasksJSON[i].status;
			mergedTask.taskName = savedTasksJSON[i].taskName;
			mergedTask.signoffTaskName = savedTasksJSON[i].signoffTaskName;
			mergedTask.signoff2TaskName = savedTasksJSON[i].signoff2TaskName;
		}
	}
	
	// Loop on the rules tasks definition
	for(i=0;i<rulesTasksJSON[1].tasks[1].length;i++) {
		var rulesTask = rulesTasksJSON[1].tasks[1][i][1];
		
		// Check if already in list, if not, add it
		// Force some values from the rules engine
		var present = false;
		for(var j=0;j<merged.length;j++){
			
			if(merged[j].taskName == rulesTask.mainTaskName) {	
				console.log("merging : db:" + merged[j].taskName + " existing " + rulesTask.mainTaskName );
				
				merged[j].suggested = rulesTask.suggested;
				merged[j].mandatory = rulesTask.mandatory;
				merged[j].taskOrder = rulesTask.taskOrder;
				merged[j].releasableFromSubTask = rulesTask.releasableFromSubTask;
				merged[j].decisionReasons = "";
				merged[j].mainTaskDynamicScreenId = rulesTask.mainTaskDynamicScreenId;
				merged[j].mainTaskExpectedDurationSeconds = rulesTask.mainTaskExpectedDurationSeconds;
				merged[j].signoffTaskExpectedDurationSeconds = rulesTask.signoffTaskExpectedDurationSeconds;
				merged[j].signoff2TaskExpectedDurationSeconds = rulesTask.signoff2TaskExpectedDurationSeconds;
				
				for(var rIdx=0;rIdx<rulesTask.decisionReasons[1].length;rIdx++){ 
					merged[j].decisionReasons += rulesTask.decisionReasons[1][rIdx].replace(new RegExp('"', 'g'), ' ').replace(new RegExp("'", 'g'), ' ')+"<br/>";
				}
				
				merged[j].completeRequired = true;
				if (rulesTask.completeRequired != undefined && rulesTask.completeRequired != null) {
					merged[j].completeRequired = rulesTask.completeRequired;
				}
				
				console.log("mainTaskDynamicScreenId : " + merged[j].mainTaskDynamicScreenId + " " + rulesTask.mainTaskDynamicScreenId + " "  );
				present = true;
			}
		}
		if(!present ) {
			// If subTaskMode and releasable from subtask
			// If docs & controls mode and not releasable from subtask			
			if(	(subTaskMode && rulesTask.releasableFromSubTask) ||
				(!subTaskMode && !rulesTask.releasableFromSubTask)
			) {
				var decisions = "";
				for(var rIdx=0;rIdx<rulesTask.decisionReasons[1].length;rIdx++){ 
					decisions += rulesTask.decisionReasons[1][rIdx].replace(new RegExp('"', 'g'), ' ').replace(new RegExp("'", 'g'), ' ')+ "<br/>";
				}
				
				var isCompleteRequired = true;
				if (rulesTask.completeRequired != undefined && rulesTask.completeRequired != null) {
					isCompleteRequired = rulesTask.completeRequired;
				}
				
				mergedTask = new Object();
				mergedTask = {
					fragmentJobId: null,
					initiatorComment: null,
					initiatorId: null,
					initiatorName: null,
					mainJobId: null,
					mainTaskAssigneeId: null,
					mainTaskAssigneeName: null,
					mainTaskComment: null,			
					mainTaskcompletionDate: null,
					signOffTaskAssigneeId: null,
					signOffTaskAssigneeName: null,
					signOffTaskComment: null,
					signOffTaskcompletionDate: null,
					signOff2TaskAssigneeId: null,
					signOff2TaskAssigneeName: null,
					signOff2TaskComment: null,
					signOff2TaskcompletionDate: null,
					startDate: null,
					status: "Not started",
					taskName: 							rulesTask.mainTaskName,
					taskOrder : 						rulesTask.taskOrder ,
					suggested : 						rulesTask.suggested,
					mandatory : 						rulesTask.mandatory,
					taskOrder: 							rulesTask.taskOrder,
					mainTaskExpectedDurationSeconds : 	rulesTask.mainTaskExpectedDurationSeconds,
					mainTaskDynamicScreenId : 			rulesTask.mainTaskDynamicScreenId,
					signoffTaskActive : 				rulesTask.signoffTaskActive,
					signoffTaskExpectedDurationSeconds: rulesTask.signoffTaskExpectedDurationSeconds,
					signoff2TaskActive : 				rulesTask.signoff2TaskActive,
					signoff2TaskExpectedDurationSeconds: rulesTask.signoff2TaskExpectedDurationSeconds,
					releasableFromSubTask : rulesTask.releasableFromSubTask,
					decisionReasons : decisions,
					completeRequired : isCompleteRequired
				};
				merged.push(mergedTask);	
			}			
		}
	} 

	
	// Filter the displayed tasks
	var filtered = []; 
	for(var j=0;j<merged.length;j++){		
		if(merged[j].taskOrder == null) {
			merged[j].taskOrder = 999;
		}
		
		console.log(merged[j].taskName + " : " + 
					merged[j].taskOrder + 
					merged[j].taskName+ " " +	
					merged[j].mainTaskDynamicScreenId+ " " + 
					merged[j].mainTaskExpectedDurationSeconds + " " + 
					merged[j].signoffTaskName+ " " + 
					merged[j].signOffTaskDynamicScreenId+" " +
					merged[j].signoffTaskExpectedDurationSeconds+" "+
					merged[j].signoff2TaskName+ " " + 
					merged[j].signOff2TaskDynamicScreenId+" " +
					merged[j].signoff2TaskExpectedDurationSeconds+" "+
					merged[j].decisionReasons);
		
		
		if(subTaskMode) {
			// Sub-task mode : display sub-tasks only
			if(merged[j].releasableFromSubTask == true) {
				merged[j].allowNew = true;
				filtered.push(merged[j]);
			}
		} else {
			// Docs and controls mode : display all
			merged[j].allowNew = !merged[j].releasableFromSubTask;
			filtered.push(merged[j]);			
		}
	}
	merged = filtered; 
	
	// sort based on task name + startDate 
	merged.sort(function(t1, t2) { 
		if (t1.taskOrder < t2.taskOrder) {
			return -1;
		}
		if (t1.taskOrder > t2.taskOrder) {
			return 1;
		}
		return 0;
	}
	);
	
	console.log(merged);
	
	// Construct DG
	var dgGridDiv = "<div class='gj-margin-top-10'><table id='dgSubTasks' style='width:100%;height:100%;background-color:white;' /></div>";
	$("#"+placeHolderDivId).css('overflowY', 'auto'); 
	$("#"+placeHolderDivId).css('height', '560px'); 
	$("#"+placeHolderDivId).html(dgGridDiv);
	window.dgSubTasks = $("#dgSubTasks").grid({
			primaryKey:'taskName',
			dataSource:merged,
			fontSize:11,
			columns:[
			{
				field : "taskName",
				sortable : true,
				title : "Task name", 
				renderer: function (value, record) { return record.mandatory==true ? '<label style="font-weight:bold; color:#EE0000;" title="This task is mandatory : '+record.decisionReasons.replace(new RegExp('<br/>', 'g'), '. ') +' ">' + value + '</label>' :  (record.suggested==true ? '<label style="font-weight:bold; color:#0000EE;" title="This task is strongly suggested : '+record.decisionReasons.replace(new RegExp('<br/>', 'g'), '. ') +' ">' + value + '</label>' :  value) ; },
				width : 200
			},
			{
				title : "Create new" ,
				align: "center", 				
				renderer :function (value, record) { 	if(record.allowNew) {
															return "<input type='button' onclick='displayNewTaskPopup(\""+record.taskName+"\" , \""+record.mainTaskDynamicScreenId+"\", \""+record.mainTaskExpectedDurationSeconds+"\", \"\", \"\", \"" + record.signoffTaskExpectedDurationSeconds + "\", \""+record.decisionReasons+"\");' value ='+'/> ";
														} else {
															return "";
														}  
														} ,
				width : 75
			},
			{
				field : "status",
				sortable : true,
				align: "center", 
				title : "Status",
				renderer: function (value, record) { 
					return record.status=="Completed" || record.status=="Rejected" || record.status=="Missing Information" || record.status=="Sign-off needed" || record.status=="2nd sign-off needed" || record.status == "Not applicable" ? '<a href="#" onclick="displayTask(\''+record.fragmentJobId+'\')" >'+value+'</a>' :  value ; 
					},
				width : 85			
			},
			{
				field : "startDate",
				sortable : true,
				title : "Start Date",
				width : 110	
			},
			{
				field : "initiatorName",
				sortable : true,
				title : "From",
				width : 140
			},			
			{
				field : "mainTaskAssigneeName",
				sortable : true,
				title : "To",
				width : 140
			},
			{
				field : "mainTaskcompletionDate",
				sortable : true,
				title : "Completion Date",
				width : 110	
			},
			
			{
				field : "signOffTaskAssigneeName",
				sortable : true,
				title : "Signoff by",
				width : 140
			},
			{
				field : "signOffTaskcompletionDate",
				sortable : true,
				title : "Signoff Date",
				width : 110	
			},

			{
				field : "signOff2TaskAssigneeName",
				sortable : true,
				title : "2nd signoff by",
				width : 140
			},
			{
				field : "signOff2TaskcompletionDate",
				sortable : true,
				title : "2nd signoff Date",
				width : 110	
			}	
		]
	});	
}

window.displayTask = function(fragmentJobId) {
	var sessionId = $("#CASE_SUBTASKS-lblSession").html();
	var caseRef = $("#CASE_SUBTASKS-lblCaseRef").html();
	
	window.parent.document.getElementById('viewSubTaskDiv').style.display='block';
	window.parent.document.getElementById('viewSubTaskDivContent').innerHTML = "";	
	window.parent.document.getElementById('viewSubTaskDivContent').focus();
	
	var iframe = window.parent.document.createElement("iframe");	
	iframe.width = 805;
	iframe.height = 655;
	iframe.frameBorder=0;
	iframe.src = "/TotalAgility/Forms/VIEW_DYNAMIC_TASK.form?txtJobId="+fragmentJobId+"&txtCaseRef="+caseRef+"&SESSION_ID="+sessionId;
	window.parent.document.getElementById('viewSubTaskDivContent').appendChild(iframe);
}

// on click
window.displayNewTaskPopup = function(taskName, task1DynamicScreenName, task1ExpectedDurationSeconds, task2Name, task2DynamicScreenName, task2ExpectedDurationSeconds, decisionReasons) { 
	console.log("new popup : " + taskName + " " + task1DynamicScreenName + " " + task1ExpectedDurationSeconds + " " + task2Name + " " + task2DynamicScreenName + " " + task2ExpectedDurationSeconds+ " " + decisionReasons);
	
	decisionReasons = decisionReasons.replace(new RegExp('<br/>', 'g'), '\n');
	var userAndTeams = JSON.parse($("#E1ECD1A1B4114F35812C7E0C45F2EF8B-inputEl").val()); 
	
	// Prepare the users and teams select
	window.parent.$("#popupUserTeamSelect").find('option').remove().end();
	window.parent.$("#popupUserTeamSelect").append($('<option>', {
												value: '',
												text: ''
											}));
	for(var i=0;i<userAndTeams.length;i++){		 
		window.parent.$("#popupUserTeamSelect").append($('<option>', {
												value: userAndTeams[i].resourceId,
												text: userAndTeams[i].resourceName
											}));
	} 	
	console.log(decisionReasons);
	window.parent.$("#initialMessage").val(decisionReasons);
	window.parent.$("#popupUserTeamSelect").val("");
	
	// Display the popup
	window.parent.document.getElementById('taskName').innerHTML = taskName;
	window.parent.document.getElementById('initiateNewTaskDiv').style.display='block';
	window.parent.document.getElementById('initialMessage').focus();
	
	// Selectize2 the select
	window.parent.$("#popupUserTeamSelect").select2({
			  /*minimumResultsForSearch: Infinity,*/
			  dropdownAutoWidth : 'true',
			  width: 'resolve'
			});
			
	// remove event handler to avoid double-click
	window.parent.$("#buCreateNewTask").off();
			
	// Add event listener to the create button
	window.parent.$("#buCreateNewTask").click(function() { 
		var selectedTeam = window.parent.$("#popupUserTeamSelect").val();
		if(selectedTeam == null ||selectedTeam=="") {
			alert("Please select a user / team");
			return;
		}
		
		// remove event handler to avoid double-click
		window.parent.$("#buCreateNewTask").off();
		
		// Save the values to KTA fields
		$("#E2FFAAB4038E43D69FF396C56D5C5B93-inputEl").val(taskName);	
		$("#C00A7C6E94A4464CB35D630534EB38B7-inputEl").val(task1DynamicScreenName);
		$("#5C5173D30CD7469B811D8AA4F4798397-inputEl").val(task1ExpectedDurationSeconds);
		$("#F346212E437C4D799B63A1746F0663C3-inputEl").val(task2Name);	
		$("#677ACA4B34C04D1AA933E69095CDE761-inputEl").val(task2DynamicScreenName);	
		$("#3086995C4E34427C8894EB4B51DC1BE4-inputEl").val(task2ExpectedDurationSeconds);  
		$("#29F2573135D44D188795FE7A13428871-inputEl").val(window.parent.$("#initialMessage").val());
		$("#4804E414A5AC467097546C7CBFA02FBD-inputEl").val(selectedTeam); 
		$("#8247ED5743E347A1A18ECAC907FA0519-inputEl").val(window.parent.$("#popupUserTeamSelect option:selected").text());
		
		// Call KTA action
		$("#EB442630088B40F3BA8AED64C6DEDBA8-btnEl").click();
		
		// Close the popup
		window.parent.document.getElementById("initiateNewTaskDiv").style.display="none"
	});
}

// Prepare popup to display Connect Screenshot
var closeOnClick='document.getElementById("initiateNewTaskDiv").style.display="none"';

var popupDiv = window.parent.document.createElement("div");
var htmlContent = " <div style='padding-left: 20%;padding-top: 10%;'> "; 
	htmlContent += " <table style='background-color:white; font-size:15; width:550px; height:210px;'> ";
	htmlContent += "    <tr style='background-color:lightgray;border-bottom:1pt solid black;'>";
	htmlContent +="          <td colspan='2'>";
	htmlContent +="             <span style='position:relative;left:10px;top:10px;float:left;font-size:16px;'><b>Initiate a new sub-task</b></span>";
	htmlContent += "            <span class='close' style='position:relative;right:10px;top:-5px;float:right;' onclick='"+closeOnClick+"'>&times;</span>";
	htmlContent += "         </td>";
	htmlContent += "    </tr>";
	htmlContent += "	<tr><td style='padding-left:5px; padding-top:20px;'><b>Task name : </td><td  style='padding-top:20px;'><div id='taskName'></b></div></td></tr> ";
	htmlContent += "	<tr><td style='padding-left:5px'><b>Message : </td><td><textarea type='text' id='initialMessage' rows='6' cols='60'></textarea></td></tr> "; 
	htmlContent += "	<tr><td style='padding-left:5px'><b>Send to : </td><td><div id='toDiv'><select id='popupUserTeamSelect'></select></div></td></tr> "; 
	htmlContent += "	<tr><td></td><td><input id='buCreateNewTask' type='button' value='Create'/></td></tr> "; 
	htmlContent += "	<tr><td></td><td>&nbsp;</td></tr> "; 
	htmlContent += " </table> ";
	htmlContent += " </div> ";

popupDiv.setAttribute("id", "initiateNewTaskDiv");
popupDiv.className = 'modal';
popupDiv.innerHTML  =  htmlContent;
popupDiv.style.display = 'none';
window.parent.document.getElementsByTagName('body')[0].appendChild(popupDiv);


// Prepare popup to display a sub-task
var closeOnClick2='document.getElementById("viewSubTaskDiv").style.display="none"';
var viewSubTaskDiv = window.parent.document.createElement("div");
var htmlContent2 =  " <div> "; 
	htmlContent2 += "    <span class='close' onclick='"+closeOnClick2+"'>&times;</span>";
	htmlContent2 += "    <div id='viewSubTaskDivContent' style='padding-left:10%;'/> ";
	htmlContent2 += " </div> ";

viewSubTaskDiv.setAttribute("id", "viewSubTaskDiv");
viewSubTaskDiv.className = 'modal';
viewSubTaskDiv.innerHTML  =  htmlContent2;
viewSubTaskDiv.style.display = 'none';
window.parent.document.getElementsByTagName('body')[0].appendChild(viewSubTaskDiv);


// Hide popup if escape
$(window.parent.document).keyup(function(e) {
	 console.log("key up " + e.keyCode);
     if ( e.keyCode == 27  ) {
      try {
		  window.parent.document.getElementById("initiateNewTaskDiv").style.display="none";
	  } catch(ex) {		  
	  }
	  try {
		  window.parent.document.getElementById("viewSubTaskDiv").style.display="none";
	  } catch(ex) {		  
	  }
    }
});
