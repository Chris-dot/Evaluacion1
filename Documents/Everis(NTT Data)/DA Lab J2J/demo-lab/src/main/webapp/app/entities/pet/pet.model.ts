import dayjs from 'dayjs/esm';
import { IOwner } from 'app/entities/owner/owner.model';
import { IType } from 'app/entities/type/type.model';
import { IVisit } from 'app/entities/visit/visit.model';

export interface IPet {
  id?: number;
  name?: string;
  birthDate?: dayjs.Dayjs;
  owner?: IOwner;
  type?: IType;
  visits?: IVisit[] | null;
}

export class Pet implements IPet {
  constructor(
    public id?: number,
    public name?: string,
    public birthDate?: dayjs.Dayjs,
    public owner?: IOwner,
    public type?: IType,
    public visits?: IVisit[] | null
  ) {}
}

export function getPetIdentifier(pet: IPet): number | undefined {
  return pet.id;
}
