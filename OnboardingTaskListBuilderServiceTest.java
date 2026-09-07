export interface ComparisonEntryFieldDefinition {
  readonly key: string;
  readonly label: string;
  readonly kind: 'text' | 'select' | 'checkbox' | 'date';
  readonly editable: boolean;
  readonly options?: readonly ComparisonOption[];
}
