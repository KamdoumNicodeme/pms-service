/***
 * Set an event handler for the click on the "Control" tab
 * This function is called by KTA when the screen has been rendered
*/
window.setControlTabEventListener = function() {
	$("a[id^='tab-']:contains('Overview')").on("click", function(event) {	      
		openTabAction(event, 'Overview');
	});
	$("a[id^='tab-']:contains('Controls')").on("click", function(event) {	      
		openTabAction(event, 'Controls');
	});
	$("a[id^='tab-']:contains('Documents')").on("click", function(event) {	      
		openTabAction(event, 'Documents');
	});
	$("a[id^='tab-']:contains('Tasks')").on("click", function(event) {	      
		openTabAction(event, 'Tasks');
	}); 
}

/**
Call a function inside the sub-form (iframe) to refresh the data
(there is no event handler in Kofax built-in actions)
*/
window.openTabAction = function(event, targetTab) { 
	var docsFrame=document.getElementById('BC3417756D5440FAB1F21273D715E458-iframe');
	var controlsFrame=document.getElementById('4E7CD7E605064DA8B2128683561DC0CE-iframe');
	var tasksFrame=document.getElementById('55788705768043DBAEA0A42718B2BF9B-iframe');
			
	// Trigger refresh action on the tab
	if(targetTab=='Tasks') { 	 
		if(tasksFrame!=null && typeof(tasksFrame.contentWindow.refreshTabPageContent)=="function" ){
			tasksFrame.contentWindow.refreshTabPageContent();			 
		}
    } else if(targetTab=='Documents') {  
		if(docsFrame!=null && typeof(docsFrame.contentWindow.refreshTabPageContent)=="function"){ 
			docsFrame.contentWindow.refreshTabPageContent(); 
		}
    } else if(targetTab=='Controls') {   
		if(controlsFrame!=null && typeof(controlsFrame.contentWindow.refreshTabPageContent)=="function"){ 
			controlsFrame.contentWindow.refreshTabPageContent(); 
		}
	 }
}

/**
 When overview change, tasks, documents and controls must be recalculated !
*/
window.markDependingTabsAsToBeRecalculated = function() {
	var docsFrame=document.getElementById('BC3417756D5440FAB1F21273D715E458-iframe');
	var controlsFrame=document.getElementById('4E7CD7E605064DA8B2128683561DC0CE-iframe');
	var tasksFrame=document.getElementById('55788705768043DBAEA0A42718B2BF9B-iframe'); 

	if(tasksFrame!=null && typeof(tasksFrame.contentWindow.setMustBeRecalculated)=="function"){
		tasksFrame.contentWindow.setMustBeRecalculated();
	}
	if(controlsFrame!=null && typeof(controlsFrame.contentWindow.setMustBeRecalculated)=="function"){
		controlsFrame.contentWindow.setMustBeRecalculated();
	}
	if(docsFrame!=null && typeof(docsFrame.contentWindow.setMustBeRecalculated)=="function"){
		docsFrame.contentWindow.setMustBeRecalculated();
	}
}

window.validateAllAndComplete = function() {
	if(window.validateAll()) {
		$("#483469CB25E342908D21F3E891D54F14-btnIconEl").click(); // complete action
	}
}

/**
Call a function inside the sub-form (iframe) to validate the data
*/
window.validateAll = function() { 
	var caseType = $("#DOCS_AND_CONTROLS-lblCaseType").html();
	var docsFrame=document.getElementById('BC3417756D5440FAB1F21273D715E458-iframe');
	var controlsFrame=document.getElementById('4E7CD7E605064DA8B2128683561DC0CE-iframe');
	var tasksFrame=document.getElementById('55788705768043DBAEA0A42718B2BF9B-iframe'); 

	if(window.docsAndControlsDisabled==true) {
		alert("Checklist overview is not completed/not saved ! ");
		return false;
	}
	
	if(tasksFrame!=null && typeof(tasksFrame.contentWindow.validateTasksTab)=="function"){
		if(!tasksFrame.contentWindow.validateTasksTab()) {
			return false;
		}	 
	} else {
		alert("Please open the tasks tab before completing the task.");
		return false;
	} 	

	if(docsFrame!=null && typeof(docsFrame.contentWindow.validateDocsTab)=="function") { 
		if(!docsFrame.contentWindow.validateDocsTab()) {
			return false;
		}	
	} else {
		alert("Please open the documents tab before completing the task.");
		return false;
	}		
	
	if (caseType != "DEALING_REQUEST") { // do not check controls for dealing
		if(controlsFrame!=null && typeof(controlsFrame.contentWindow.validateControlsTab)=="function"){
			if(!controlsFrame.contentWindow.validateControlsTab()) {
				return false;
			}	
		} else {
			alert("Please open the controls tab before completing the task.");
			return false;
		}		
	}
	return true;
}
 
