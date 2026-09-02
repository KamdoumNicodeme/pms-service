export interface PairedEntry {
  readonly key: string;
  readonly label: string;

  readonly values: Readonly<
    Record<ComparisonSourceId, string | null>
  >;

  readonly fields: Readonly<
    Record<
      ComparisonSourceId,
      readonly ComparisonEntryFieldDto[] | null
    >
  >;
}
