private contactDetails(
  holder: IThirdParty
): ComparisonSectionDto {

  return {
    id: 'contact-details',
    title: 'Contact details',
    sources: [this.coreSource],

    fields: [
      this.listField(
        'emails',
        'Emails',
        'email',
        holder.emails.map(email => email.email)
      ),

      this.listField(
        'phone-numbers',
        'Phone numbers',
        'phone number',
        holder.phoneNumbers.map(phone => phone.phoneNumber)
      ),

      this.scalarField(
        'preferred-channel',
        'Preferred channel',
        holder.preferredChannel
      ),

      this.addressGroup(
        'legal-address',
        'Legal address',
        holder.legalAddress
      ),

      this.addressGroup(
        'sending-address',
        'Sending address',
        holder.sendingAddress
      ),
    ],
  };
}
