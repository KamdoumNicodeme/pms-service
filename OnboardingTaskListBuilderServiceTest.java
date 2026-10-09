const allowedTypes = ['STRING', 'DATE', 'DATETIME', 'NUMBER'];

const metadata = Object.entries(request.metadata).map(([key, value]) => {
  const type = request.metadataTypes[key];

  if (!type || !allowedTypes.includes(type)) {
    throw new Error(`Invalid metadata type for ${key}: ${type}`);
  }

  return {
    key,
    type,
    value
  };
});

if (metadata.length > 0) {
  formData.append('metadata', JSON.stringify(metadata));
}
