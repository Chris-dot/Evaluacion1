import dayjs from 'dayjs/esm';
import { IPet } from 'app/entities/pet/pet.model';

export interface IVisit {
  id?: number;
  visitDate?: dayjs.Dayjs;
  description?: string;
  pet?: IPet;
}

export class Visit implements IVisit {
  constructor(public id?: number, public visitDate?: dayjs.Dayjs, public description?: string, public pet?: IPet) {}
}

export function getVisitIdentifier(visit: IVisit): number | undefined {
  return visit.id;
}
