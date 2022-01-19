export interface ISpecialtie {
  id?: number;
  name?: string;
}

export class Specialtie implements ISpecialtie {
  constructor(public id?: number, public name?: string) {}
}

export function getSpecialtieIdentifier(specialtie: ISpecialtie): number | undefined {
  return specialtie.id;
}
