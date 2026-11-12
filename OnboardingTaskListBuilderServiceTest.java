if (!('structuredClone' in globalThis)) {
  Object.defineProperty(globalThis, 'structuredClone', {
    configurable: true,
    writable: true,
    value: <T>(value: T): T =>
      deserialize(serialize(value)) as T
  });
}
