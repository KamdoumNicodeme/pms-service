private ensureSendingAddress(result: IChangeClientInformation): void {
  if (!result.policy.holder) {
    return;
  }

  result.policy.holder.sendingAddress ??= {} as SendingAddress;

  result.policy.holder.sendingAddress.address ??= {} as Address;
}


case 'street': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.streetName =
    this.stringValue(value);

  break;
}

case 'number': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.streetNumber =
    this.stringValue(value);

  break;
}

case 'house-name': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.residenceName =
    this.stringValue(value);

  break;
}

case 'apartment-number': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.apartmentNumber =
    this.stringValue(value);

  break;
}

case 'city': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.townName =
    this.stringValue(value);

  break;
}

case 'postcode': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.postCode =
    this.stringValue(value);

  break;
}

case 'country': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.country =
    this.stringValue(value);

  break;
}

case 'area': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.address!.area =
    this.stringValue(value);

  break;
}

case 'language': {
  this.ensureSendingAddress(result);

  result.policy.holder!.sendingAddress!.language =
    this.stringValue(value);

  break;
}
