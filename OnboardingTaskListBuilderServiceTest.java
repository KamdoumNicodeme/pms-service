<section class="holder">

  <aside class="holder__nav">
    <profiling-nav
      [sections]="sections"
      [activeId]="activeId()"
      (select)="goTo($event)"
    />
  </aside>

  <section class="holder__main">

    @if (section(); as data) {

      <profiling-panel
        [attr.data-section]="data.id"
        [title]="data.title"
        [open]="isOpen(data.id)"
        (toggle)="toggleSection(data.id)"
      >

        <data-comparison
          [section]="data"
          [filter]="filter()"
          (changeChanges)="onPolicyChanges({
            sectionId: data.id,
            changes: $event.changes
          })"
        />

      </profiling-panel>

    }

  </section>

</section>
