import { IAddress } from 'app/entities/address/address.model';

export interface IClient {
  id?: number;
  firstName?: string;
  lastName?: string;
  addressName?: string;
  cellphoneNumber?: number;
  email?: string;
  address?: IAddress;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public addressName?: string,
    public cellphoneNumber?: number,
    public email?: string,
    public address?: IAddress
  ) {}
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
