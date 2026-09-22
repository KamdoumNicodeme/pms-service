cleanForSave<T>(value: T): T {
  return this.removeEmptyValues(structuredClone(value)) as T;
}

private removeEmptyValues(value: unknown): unknown {
  if (Array.isArray(value)) {
    return value
      .map(item => this.removeEmptyValues(item))
      .filter(item => item !== undefined);
  }

  if (value !== null && typeof value === 'object') {
    const cleaned = Object.entries(value)
      .map(([key, val]) => [key, this.removeEmptyValues(val)] as const)
      .filter(([, val]) => val !== undefined);

    if (cleaned.length === 0) {
      return undefined;
    }

    return Object.fromEntries(cleaned);
  }

  if (value === null || value === undefined) {
    return undefined;
  }

  return value;
}
