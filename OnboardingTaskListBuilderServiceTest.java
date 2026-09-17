export const POLICY_PROFILING_SECTIONS = [
    {
        id: 'sending-address-communication',
        label: 'Sending address and Communication Preferences',
    },
    {
        id: 'opt-in-out',
        label: 'Opt In/Out',
    },
] as const;



export class PolicyProfiling {

    readonly policyNumber = input.required<string>();

    protected readonly sections = POLICY_PROFILING_SECTIONS;

    private readonly openSections =
        signal<ReadonlySet<string>>(
            new Set(POLICY_PROFILING_SECTIONS.map(section => section.id))
        );

    protected readonly activeId =
        signal<string | null>(
            POLICY_PROFILING_SECTIONS[0]?.id ?? null
        );

    protected readonly filter =
        signal<ComparisonFilter>('all');

    protected isOpen(sectionId: string): boolean {
        return this.openSections().has(sectionId);
    }

    protected toggleSection(sectionId: string): void {
        this.openSections.update(current => {
            const next = new Set(current);

            if (next.has(sectionId)) {
                next.delete(sectionId);
            } else {
                next.add(sectionId);
            }

            return next;
        });
    }

    protected goTo(sectionId: string): void {
        this.openSections.update(
            current => new Set(current).add(sectionId)
        );

        this.activeId.set(sectionId);
    }
}
