<section class="lia-rejection">
  <form
    class="lia-rejection__form"
    [formGroup]="form"
    (ngSubmit)="submit(form.getRawValue())"
  >
    <label for="rejection" class="lia-rejection__form__label">
      Rejection reason:
    </label>

    <textarea
      nz-input
      id="rejection"
      class="lia-rejection__form__textarea"
      placeholder="This task is getting rejected because..."
      formControlName="rejectionReason"
      rows="5"
    ></textarea>

    <section class="lia-rejection__form__actions">
      <button nz-button nzType="default" type="button" (click)="close()">
        Cancel
      </button>

      <button nz-button nzType="primary" [disabled]="form.invalid" type="submit">
        Confirm
      </button>
    </section>
  </form>
</section>

  .lia-rejection {
  width: 100%;

  &__form {
    display: flex;
    flex-direction: column;
    width: 100%;
    gap: 12px;

    &__label {
      display: block;
      width: 100%;
      font-weight: 400;
    }

    &__textarea {
      display: block;
      width: 100%;
      box-sizing: border-box;
      min-height: 100px;
      resize: vertical;
    }

    &__actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 1rem;
      margin-top: 6px;
    }
  }
}
