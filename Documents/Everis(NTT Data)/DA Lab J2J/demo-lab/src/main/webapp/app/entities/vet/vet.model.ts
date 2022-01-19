import { IVetSpecialtie } from 'app/entities/vet-specialtie/vet-specialtie.model';

export interface IVet {
  id?: number;
  firstName?: string;
  lastName?: string;
  specialties?: IVetSpecialtie[] | null;
}

export class Vet implements IVet {
  constructor(public id?: number, public firstName?: string, public lastName?: string, public specialties?: IVetSpecialtie[] | null) {}
}

export function getVetIdentifier(vet: IVet): number | undefined {
  return vet.id;
}
