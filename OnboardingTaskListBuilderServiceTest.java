const DOCUMENTS: readonly ClientDocument[] = [
  // Holder 1 - Contact Details
  {
    id: 'contact-doc-1',
    name: 'p_o_r_00035698.jpg',
    type: 'Proof of residence',
    date: '2026-07-13',
    thirdPartyId: '0003310689',
    sectionId: 'contact-details'
  },

  // Holder 1 - Identity Documents
  {
    id: 'identity-doc-1',
    name: 'passport_19FR85214.pdf',
    type: 'ID document',
    date: '2026-06-18',
    thirdPartyId: '0003310689',
    sectionId: 'identity-documents'
  },

  // Holder 1 - Tax Information
  {
    id: 'tax-doc-1',
    name: 'aeoi_self_certification.pdf',
    type: 'AEOI Self Certification',
    date: '2026-06-20',
    thirdPartyId: '0003310689',
    sectionId: 'tax-information'
  },
  {
    id: 'tax-doc-2',
    name: 'w9.pdf',
    type: 'W9',
    date: '2026-06-21',
    thirdPartyId: '0003310689',
    sectionId: 'tax-information'
  },

  // Another holder - only to verify isolation
  {
    id: 'contact-doc-2',
    name: 'proof_of_residence_holder_2.pdf',
    type: 'Proof of residence',
    date: '2026-07-15',
    thirdPartyId: '0009999999',
    sectionId: 'contact-details'
  }
];
