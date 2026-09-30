protected metadataEntries(
  metadata: Record<string, string> | null | undefined
): [string, string][] {
  return Object.entries(metadata ?? {});
}

<div class="documents__metadata">
  @for (entry of metadataEntries(document.metadata); track entry[0]) {
    <span class="documents__metadata-entry">
      <strong>{{ entry[0] }}:</strong>
      {{ entry[1] }}
    </span>
  } @empty {
    <span>-</span>
  }
</div>
