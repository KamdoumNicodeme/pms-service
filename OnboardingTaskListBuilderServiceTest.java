<div class="documents__actions">

  <button
    nz-button
    nz-dropdown
    nzTrigger="click"
    [nzDropdownMenu]="documentMenu"
    class="documents__menu-button"
    type="button"
  >
    <nz-icon nzType="more" />
  </button>

  <nz-dropdown-menu #documentMenu="nzDropdownMenu">
    <ul nz-menu>

      <li
        nz-menu-item
        (click)="onAction('download', document)"
      >
        <nz-icon nzType="download" />
        Download
      </li>

      <li
        nz-menu-item
        (click)="onAction('edit', document)"
      >
        <nz-icon nzType="edit" />
        Edit
      </li>

      <li
        nz-menu-item
        (click)="onAction('reject', document)"
      >
        <nz-icon nzType="close-circle" />
        Reject
      </li>

      <li
        nz-menu-item
        (click)="onAction('validate', document)"
      >
        <nz-icon nzType="check-circle" />
        Validate
      </li>

      <li nz-menu-divider></li>

      <li
        nz-menu-item
        nzDanger
        (click)="onAction('delete', document)"
      >
        <nz-icon nzType="delete" />
        Delete
      </li>

    </ul>
  </nz-dropdown-menu>

</div>

  &--header {
  min-height: 2.8rem;

  // Header du tableau volontairement plus foncé
  background: #96752f;
  color: #fff;

  font-size: 0.8rem;
  font-weight: 600;

  > div {
    color: #fff;
    border-right: 1px solid rgba(255, 255, 255, 0.65);
  }
}
