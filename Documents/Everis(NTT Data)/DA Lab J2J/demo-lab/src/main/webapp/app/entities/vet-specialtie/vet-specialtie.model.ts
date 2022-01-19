import { IVet } from 'app/entities/vet/vet.model';
import { ISpecialtie } from 'app/entities/specialtie/specialtie.model';

export interface IVetSpecialtie {
  id?: number;
  vet?: IVet;
  specialtie?: ISpecialtie;
}

export class VetSpecialtie implements IVetSpecialtie {
  constructor(public id?: number, public vet?: IVet, public specialtie?: ISpecialtie) {}
}

export function getVetSpecialtieIdentifier(vetSpecialtie: IVetSpecialtie): number | undefined {
  return vetSpecialtie.id;
}
