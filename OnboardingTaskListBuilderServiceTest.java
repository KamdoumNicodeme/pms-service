@for (rejection of rejectionItems(); track rejection.type) {
  <div class="notes-list__notes__note__event">
    <span class="notes-list__notes__note__event__type">
      {{ rejection.label }} rejected
    </span>

    <span class="notes-list__notes__note__event__details">
      {{ rejection.reason }}
    </span>
  </div>
}
