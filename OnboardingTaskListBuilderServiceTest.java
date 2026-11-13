      expect(toggleSpy).not.toHaveBeenCalled();
    });

    it('should ignore clicks on a non-editable row', () => {
      render(makeRow({ editable: false }));
      const toggleSpy = jest.fn();
      component.toggle.subscribe(toggleSpy);

      component.onClick({} as MouseEvent);

      expect(toggleSpy).not.toHaveBeenCalled();
    });

    it('should forward keyboard events to the resolution panel', () => {
      render();
      const panelMock = { onKeydown: jest.fn() };
      // The actual template is stubbed, so viewChild() has no child. Replace
      // the query signal locally for this unit test of delegation only.
      Object.defineProperty(component, 'panel', {
        configurable: true,
        value: () => panelMock
      });
      const event = new KeyboardEvent('keydown', { key: 'Escape' });

      component.onKeydown(event);

      expect(panelMock.onKeydown).toHaveBeenCalledWith(event);
    });
  });

  describe('focus effect', () => {
    it('should not focus the host while focused=false', () => {
      const focusSpy = jest.spyOn(fixture.nativeElement as HTMLElement, 'focus');
      render();
      expect(focusSpy).not.toHaveBeenCalled();
    });

    it('should focus the host when focused becomes true', () => {
      const focusSpy = jest.spyOn(fixture.nativeElement as HTMLElement, 'focus')
        .mockImplementation(() => {});
      render();

      fixture.componentRef.setInput('focused', true);
      fixture.detectChanges();

      expect(focusSpy).toHaveBeenCalledWith({ preventScroll: false });
    });
  });

  describe('Italian birth town reference loading', () => {
    it('should not request references for ordinary rows', () => {
      render(makeRow({
        values: { core: 'A001', clip: 'B002' },
        resolution: { value: 'C003', reason: '', comment: '' }
      }));

      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();
    });

    it('should request each distinct non-empty town code', () => {
      render(italianRow('A001', 'B002', 'C003'));

      expect(referenceServiceMock.findReferencesByDomains).toHaveBeenCalledTimes(3);
      for (const code of ['A001', 'B002', 'C003']) {
        expect(referenceServiceMock.findReferencesByDomains)
          .toHaveBeenCalledWith('IT_BIRTH_CODE', code);
      }
    });

    it('should not request the same code more than once in a single row', () => {
      render(italianRow('A001', 'A001', 'A001'));

      expect(referenceServiceMock.findReferencesByDomains).toHaveBeenCalledTimes(1);
      expect(referenceServiceMock.findReferencesByDomains)
        .toHaveBeenCalledWith('IT_BIRTH_CODE', 'A001');
    });

    it('should ignore empty codes', () => {
      render(italianRow('', null, null));
      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();
    });

    it('should skip codes which already have labels', () => {
      italianLabels.set([{ value: 'A001', label: 'Already loaded' }]);
      render(italianRow('A001', '', null));

      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();
    });

    it('should add the references returned by the lookup', () => {
      const reference = { value: 'A001', label: 'Town A001' };
      referenceServiceMock.findReferencesByDomains.mockReturnValue(of(reference));
      render(italianRow('A001', '', null));

      expect(comparisonDataMock.addItalianBirthTownOptions)
        .toHaveBeenCalledWith([reference]);
    });

    it('should not add a reference when the lookup emits nothing', () => {
      referenceServiceMock.findReferencesByDomains.mockReturnValue(EMPTY);
      render(italianRow('A001', '', null));

      expect(comparisonDataMock.addItalianBirthTownOptions).not.toHaveBeenCalled();
    });

    it('should start loading when a normal row becomes an Italian birth-town row', () => {
      render();
      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();

      updateRow(italianRow('A001', '', null));
      expect(referenceServiceMock.findReferencesByDomains)
        .toHaveBeenCalledWith('IT_BIRTH_CODE', 'A001');
    });

    it('should use loaded reference labels for Italian birth-town annotations', () => {
      italianLabels.set([{ value: 'A001', label: 'Town A001' }]);
      render(italianRow('A001', '', null));
      comparisonDataMock.getItalianBirthTownLabels.mockClear();

      const annotations = component.annotations();

      expect(Array.isArray(annotations)).toBe(true);
      expect(comparisonDataMock.getItalianBirthTownLabels).toHaveBeenCalled();
    });

    it('should use row options for annotations on other fields', () => {
      render(makeRow({
        options: [{ value: 'BE', label: 'Belgium' }]
