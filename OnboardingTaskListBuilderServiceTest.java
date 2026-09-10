readonly expandedId =
  input<string | null>(null);

readonly focusedId =
  input<string | null>(null);

readonly toggleChild =
  output<string>();

readonly applyChild =
  output<{
    id: string;
    patch: ResolutionPatch;
  }>();

readonly resetChild =
  output<string>();
