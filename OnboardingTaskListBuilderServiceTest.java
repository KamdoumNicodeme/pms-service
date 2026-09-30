<button
  nz-button
  nz-dropdown
  [nzDropdownMenu]="documentMenu"
  nzTrigger="click"
  class="documents__menu-button"
>
  <span nz-icon nzType="more" nzTheme="outline"></span>
</button>

<nz-dropdown-menu #documentMenu="nzDropdownMenu">
  <ul nz-menu>
    <li nz-menu-item>
      <span nz-icon nzType="download"></span>
      Download
    </li>

    <li nz-menu-item>
      <span nz-icon nzType="edit"></span>
      Edit
    </li>

    <li nz-menu-item>
      <span nz-icon nzType="close-circle"></span>
      Reject
    </li>

    <li nz-menu-item>
      <span nz-icon nzType="check-circle"></span>
      Validate
    </li>

    <li nz-menu-divider></li>

    <li nz-menu-item nzDanger>
      <span nz-icon nzType="delete"></span>
      Delete
    </li>
  </ul>
</nz-dropdown-menu>
