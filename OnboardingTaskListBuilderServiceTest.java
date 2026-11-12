async function createComponent(): Promise<void> {
  fixture = TestBed.createComponent(CciManualChangeComponent);
  component = fixture.componentInstance;

  const injectedService = fixture.debugElement.injector.get(
    COMPARISON_DATA_SERVICE
  );

  expect(injectedService).toBe(comparisonDataServiceMock);
  expect(injectedService.setReferences).toEqual(
    expect.any(Function)
  );

  fixture.componentRef.setInput('currentCase', currentCase);
  fixture.componentRef.setInput('currentTask', currentTask);
  fixture.componentRef.setInput('policy', policy);
  fixture.componentRef.setInput(
    'clientProfilingReferences',
    references
  );

  fixture.detectChanges();
  await fixture.whenStable();
}
