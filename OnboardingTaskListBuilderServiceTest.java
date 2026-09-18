<section class="holder">

  <aside class="holder__nav">
    <profiling-nav
      [sections]="navSections()"
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
          (toggle)="toggle(section.id)"
        >

          <!-- Policy content will go here -->

        </profiling-panel>

      }

    </section>

  </section>

</section>
