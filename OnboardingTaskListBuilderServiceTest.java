readonly draft = signal('');

readonly addError = signal<string | null>(null);

submit(): void {
  const value = this.draft().trim();

  if (value === '') {
    return;
  }

  const duplicate = this.row().entries.some(entry => {
    const valuesToCheck = [
      entry.entryKey,
      entry.label,
      entry.values.core,
      entry.values.kyc,
      entry.values.digital,
      entry.resolution.value,
      entry.fallback.value,
    ];

    return valuesToCheck.some(existingValue =>
      this.isSameValue(
        existingValue,
        value
      )
    );
  });

  if (duplicate) {
    this.addError.set(
      `This ${this.row().entryNoun} already exists`
    );

    return;
  }

  this.addError.set(null);

  this.addEntry.emit(value);

  this.cancelAdd();
}

private isSameValue(
  first: string | null | undefined,
  second: string | null | undefined
): boolean {

  if (!first || !second) {
    return false;
  }

  let a = first.trim();
  let b = second.trim();

  const fieldKey = this.row().key.toLowerCase();

  // ==========================================
  // NATIONALITY
  // DE === Germany
  // ==========================================

  if (
    fieldKey === 'nationality' ||
    fieldKey === 'nationalities'
  ) {
    const options = this.row().options;

    const optionA = options.find(option =>
      option.value.toLowerCase() === a.toLowerCase() ||
      option.label.toLowerCase() === a.toLowerCase()
    );

    const optionB = options.find(option =>
      option.value.toLowerCase() === b.toLowerCase() ||
      option.label.toLowerCase() === b.toLowerCase()
    );

    a = optionA?.value ?? a;
    b = optionB?.value ?? b;

    return a.toUpperCase() === b.toUpperCase();
  }

  // ==========================================
  // EMAIL
  // ==========================================

  if (
    fieldKey === 'email' ||
    fieldKey === 'emails'
  ) {
    return a.toLowerCase() === b.toLowerCase();
  }

  // ==========================================
  // PHONE
  // ==========================================

  if (
    fieldKey === 'phone' ||
    fieldKey === 'phones' ||
    fieldKey === 'phone-number' ||
    fieldKey === 'phone-numbers'
  ) {
    const normalizePhone = (phone: string) =>
      phone.replace(/[\s().-]/g, '');

    return normalizePhone(a) === normalizePhone(b);
  }

  return a.toLowerCase() === b.toLowerCase();
}

<button
  type="button"
  class="list__add__confirm"
  [disabled]="draft().trim() === ''"
  (click)="submit()"
>
  Add
</button>

@if (addError(); as error) {
  <span class="list__add__error">
    {{ error }}
  </span>
}

<button
  type="button"
  class="list__add__cancel"
  (click)="cancelAdd()"
>
  Cancel
</button>

    cancelAdd(): void {
  this.adding.set(false);
  this.draft.set('');
  this.structureDraft.set({});
  this.addError.set(null);
}
