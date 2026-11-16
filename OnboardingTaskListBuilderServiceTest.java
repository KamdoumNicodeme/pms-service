private loadRejectionReasons(caseId: string): void {
  this.clientProfilingDataService.getCaseData(caseId).subscribe({
    next: (data: IClientProfilingData): void => {
      this.rejectionReasons.set({
        [TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF]:
          data.changeClientInformationSignOff?.rejectionReason ?? null,

        [TaskType.CCI_MANUAL_CHANGE_SIGNOFF]:
          data.manualChangeSignOff?.rejectionReason ?? null,

        [TaskType.CCI_ERROR_MANAGEMENT_SIGNOFF]:
          data.errorManagementSignOff?.rejectionReason ?? null
      });
    }
  });
}
