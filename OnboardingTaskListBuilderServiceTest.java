private applyPhysicalPersonChanges(

  client: IPhysicalPerson,

  changes: ReadonlyMap<string, Resolution>

): void {

  changes.forEach(

    (resolution: Resolution, id: string): void => {

      const value: string | null = resolution.value;

      // ========================================================

      // MANUAL NATIONALITY

      // ========================================================

      if (id.startsWith('nationalities:manual-')) {

        this.applyManualNationalityChange(

          client,

          id,

          resolution

        );

        return;

      }

      // ========================================================

      // EXISTING CORE NATIONALITY

      // ========================================================

      if (id.startsWith('nationalities:')) {

        this.applyNationalityChange(

          client,

          id,

          resolution

        );

        return;

      }

      // ========================================================

      // EMAIL

      // ========================================================

      if (id.startsWith('emails:')) {

        this.applyEmailChange(

          client,

          id,

          resolution

        );

        return;

      }

      // ========================================================

      // PHONE

      // ========================================================

      if (id.startsWith('phone-numbers:')) {

        this.applyPhoneChange(

          client,

          id,

          resolution

        );

        return;

      }

      // ========================================================

      // TAX

      // ========================================================

      if (id.startsWith('tax-information:')) {

        this.applyTaxInformationChange(

          client,

          id,

          resolution

        );

        return;

      }
