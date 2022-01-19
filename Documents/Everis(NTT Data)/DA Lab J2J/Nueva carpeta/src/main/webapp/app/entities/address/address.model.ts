export interface IAddress {
  id?: number;
  name?: string;
  number?: number;
  commune?: string;
}

export class Address implements IAddress {
  constructor(public id?: number, public name?: string, public number?: number, public commune?: string) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
