<div class="row__body">

  @if (entry().children?.length) {

    <div class="row__children">

      @for (child of entry().children; track child.id) {

        <comparison-row
          [row]="child"
        />

      }

    </div>

  } @else {

    <comparison-result
      [resolution]="entry().resolution"
      [needsAttention]="entry().needsAttention"
      [isOverride]="entry().isOverride"
      [expanded]="expanded()"
      [emptyMeansDropped]="true"
      [kind]="kind()"
      (edit)="toggle.emit()"
    />

    <comparison-annotations
      [annotations]="annotations()"
    />

  }

</div>
