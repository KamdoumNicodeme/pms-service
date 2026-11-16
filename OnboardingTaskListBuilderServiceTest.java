readonly rejectionItems = computed(() => {
  const reasons = this.rejectionReason();

  const tasks = [
    {
      type: TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
      label: 'Change Client Information Sign Off'
    },
    {
      type: TaskType.CCI_MANUAL_CHANGE_SIGNOFF,
      label: 'CCI Manual Change Sign Off'
    },
    {
      type: TaskType.CCI_ERROR_MANAGEMENT_SIGNOFF,
      label: 'CCI Error Management Sign Off'
    }
  ];

  return tasks.flatMap(({ type, label }) => {
    const reason = reasons[type];

    return reason?.trim()
      ? [{ type, label, reason }]
      : [];
  });
});
