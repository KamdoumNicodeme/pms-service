protected readonly comparisonDataService =
  inject(CoreComparisonDataService);

protected searchItalianBirthTownCodes = (search: string): void => {
  this.searchItalianBirthTownCodesApi()(search, 0, 20)
    .subscribe({
      next: (response: IPageable<IReference>) => {
        this.comparisonDataService.setItalianBirthTownCodes(
          response.content
        );
      }
    });
};
