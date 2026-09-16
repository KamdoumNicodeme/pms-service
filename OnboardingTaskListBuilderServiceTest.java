private readonly phoneRegex = /^\+\d+$/;

private readonly emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

readonly validationError = computed(() => {
  const value = this.draft().trim();

  if (!value) {
    return '';
  }

  if (this.row().key === 'phone-numbers') {
    return this.phoneRegex.test(value)
      ? ''
      : 'Phone number must start with + and contain only digits';
  }

  if (this.row().key === 'emails') {
    return this.emailRegex.test(value)
      ? ''
      : 'Please enter a valid email address';
  }

  return '';
});



submit(): void {
  const value = this.draft().trim();

  if (value === '') {
    return;
  }

  if (this.validationError() !== '') {
    return;
  }

  this.addEntry.emit(value);

  this.cancelAdd();
}


<button
  type="button"
  class="list__add__confirm"
  [disabled]="draft().trim() === '' || validationError() !== ''"
  (click)="submit()"
>
  Add
</button>

  @if (validationError()) {
  <div class="list__add__error">
    {{ validationError() }}
  </div>
}

    .list__add__error {
  margin-top: 4px;
  font-size: 12px;
  color: #d4380d;
}

private readonly phoneRegex = /^\+\d{3,}$/;
