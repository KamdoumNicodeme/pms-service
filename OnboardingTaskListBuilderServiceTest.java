export class CCIManualChangeSignOffForm extends AbstractFormGroup<Controls> {

  constructor(
    initialData?: IManualChange | null,
    needCommPreferences = false
  ) {
    super({
      checklist: new ChecklistForm(initialData, needCommPreferences)
    });

    this.disable({ emitEvent: false });
  }
}
