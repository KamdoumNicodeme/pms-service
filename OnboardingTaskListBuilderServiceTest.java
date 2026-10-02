const current: IChangeClientInformation =
  this.pendingChangeClientInformation()
    ? structuredClone(this.pendingChangeClientInformation()!)
    : this.changeService.createWorkingCopy(data);
