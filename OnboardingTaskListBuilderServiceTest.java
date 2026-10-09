protected readonly selectedMetadata = computed((): MetadataConfiguration[] => {
  const documentType = this.documentTypeValue();

  if (!documentType) {
    return [];
  }

  return this.metadataConfiguration()[documentType] ?? [];
});
