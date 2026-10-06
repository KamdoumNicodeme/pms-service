test('should download the document', async () => {
  MOCK_CASES_SERVICE.findAllDocuments.mockReturnValue(of(DOCUMENTS));

  const blob = new Blob(['pdf content'], {
    type: 'application/pdf',
  });

  MOCK_CASES_SERVICE.downloadDocument.mockReturnValue(of(blob));

  await setup();

  // JSDOM does not implement these methods
  Object.defineProperty(URL, 'createObjectURL', {
    writable: true,
    value: jest.fn().mockReturnValue('blob:test-url'),
  });

  Object.defineProperty(URL, 'revokeObjectURL', {
    writable: true,
    value: jest.fn(),
  });

  const click = jest.fn();

  jest.spyOn(window.document, 'createElement')
    .mockReturnValue({
      href: '',
      download: '',
      click,
    } as unknown as HTMLAnchorElement);

  component.download(DOCUMENTS[0]);

  expect(MOCK_CASES_SERVICE.downloadDocument).toHaveBeenCalledWith(
    DOCUMENTS[0].caseBusinessIdentifier,
    DOCUMENTS[0].documentId
  );

  expect(URL.createObjectURL).toHaveBeenCalledWith(blob);

  expect(click).toHaveBeenCalled();

  expect(URL.revokeObjectURL).toHaveBeenCalledWith('blob:test-url');
});
