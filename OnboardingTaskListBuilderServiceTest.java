
it('should use Italian town labels for the Italian birth town field', async () => {
  await render('001');

  fixture.componentRef.setInput(
    'fieldId',
    'italian-birth-town-code'
  );

  fixture.detectChanges();

  // Trigger computed evaluation
  expect(component.displayValue()).toBe('Rome');

  // Verify the service was called
  expect(
    comparisonServiceMock.getItalianBirthTownLabels
  ).toHaveBeenCalled();
});
