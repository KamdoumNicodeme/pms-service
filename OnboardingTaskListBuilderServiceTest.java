export interface ComparisonEntryRow {
  readonly id: string;
  readonly fieldKey: string;
  readonly entryKey: string;
  readonly label: string;

  readonly values: Readonly<
    Record<ComparisonSourceId, string | null>
  >;

  readonly fields?: Readonly<
    Record<
      ComparisonSourceId,
      readonly ComparisonEntryFieldDto[] | null
    >
  >;

  readonly status: ComparisonStatus;
  readonly resolution: Resolution;
  readonly fallback: Resolution;
  readonly needsAttention: boolean;
  readonly isOverride: boolean;
  readonly isManual: boolean;
}
