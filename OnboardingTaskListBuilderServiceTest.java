@for (warning of warningMessages; track $index) {
  <div class="warning-item">
    <div class="warning-item__context">
      <nz-icon nzType="warning" nzTheme="outline" />

      <span class="warning-item__location">
        {{ warning.fieldContext }}
      </span>
    </div>

    <p class="warning-item__message">
      {{ warning.message }}
    </p>
  </div>
}

      .warning-item {
  padding: 12px 16px;
  background: #fffaf2;
  border-left: 3px solid #d98b21;
  border-radius: 4px;
  margin-bottom: 8px;

  &__context {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #8b641f;
    font-size: 12px;
    font-weight: 600;
    margin-bottom: 6px;
  }

  &__location {
    overflow-wrap: anywhere;
  }

  &__message {
    margin: 0 0 0 22px;
    font-size: 13px;
    color: #333;
    line-height: 1.6;
  }
}
