test('should request the document download', async () => {
  MOCK_CASES_SERVICE.findAllDocuments.mockReturnValue(of(DOCUMENTS));

  const blob = new Blob(['pdf content'], {
    type: 'application/pdf'
  });

  MOCK_CASES_SERVICE.downloadDocument.mockReturnValue(of(blob));

  jest.spyOn(URL, 'createObjectURL')
    .mockReturnValue('blob:test-url');

  jest.spyOn(URL, 'revokeObjectURL')
    .mockImplementation(() => undefined);

  jest.spyOn(window.document, 'createElement')
    .mockReturnValue({
      href: '',
      download: '',
      click: jest.fn()
    } as unknown as HTMLAnchorElement);

  await setup();

  component.download(DOCUMENTS[0]);

  expect(MOCK_CASES_SERVICE.downloadDocument).toHaveBeenCalledWith(
    DOCUMENTS[0].caseBusinessIdentifier,
    DOCUMENTS[0].documentId
  );
});
