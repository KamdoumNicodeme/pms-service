<section class="holder">

  <aside class="holder__nav">
    <profiling-nav
      [sections]="sections"
      [activeId]="activeId()"
      (select)="goTo($event)"
    />
  </aside>

  <section class="holder__main">

    <comparison-toolbar
      class="holder__toolbar"
      [counters]="totals()"
      [filter]="filter()"
      (filterChange)="setFilter($event)"
    />

    <section class="holder__sections">

      @for (section of sections; track section.id) {

        <profiling-panel
          [attr.data-section]="section.id"
          [title]="section.title"
          [open]="isOpen(section.id)"
          [counters]="countersFor(section.id)"
          (toggle)="toggleSection(section.id)"
        >

          <!-- Section content will be displayed here -->

        </profiling-panel>

      }

    </section>

  </section>

</section>
