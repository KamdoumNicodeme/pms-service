@for (warning of warningMessages; track $index) {
  <li class="message-item">
    @if (warning.fieldContext) {
      <strong>{{ warning.fieldContext }}</strong>
    }
    {{ warning.message }}
  </li>
}
