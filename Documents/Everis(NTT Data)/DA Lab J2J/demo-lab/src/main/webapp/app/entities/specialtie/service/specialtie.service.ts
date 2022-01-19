import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpecialtie, getSpecialtieIdentifier } from '../specialtie.model';

export type EntityResponseType = HttpResponse<ISpecialtie>;
export type EntityArrayResponseType = HttpResponse<ISpecialtie[]>;

@Injectable({ providedIn: 'root' })
export class SpecialtieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/specialties');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(specialtie: ISpecialtie): Observable<EntityResponseType> {
    return this.http.post<ISpecialtie>(this.resourceUrl, specialtie, { observe: 'response' });
  }

  update(specialtie: ISpecialtie): Observable<EntityResponseType> {
    return this.http.put<ISpecialtie>(`${this.resourceUrl}/${getSpecialtieIdentifier(specialtie) as number}`, specialtie, {
      observe: 'response',
    });
  }

  partialUpdate(specialtie: ISpecialtie): Observable<EntityResponseType> {
    return this.http.patch<ISpecialtie>(`${this.resourceUrl}/${getSpecialtieIdentifier(specialtie) as number}`, specialtie, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecialtie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecialtie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSpecialtieToCollectionIfMissing(
    specialtieCollection: ISpecialtie[],
    ...specialtiesToCheck: (ISpecialtie | null | undefined)[]
  ): ISpecialtie[] {
    const specialties: ISpecialtie[] = specialtiesToCheck.filter(isPresent);
    if (specialties.length > 0) {
      const specialtieCollectionIdentifiers = specialtieCollection.map(specialtieItem => getSpecialtieIdentifier(specialtieItem)!);
      const specialtiesToAdd = specialties.filter(specialtieItem => {
        const specialtieIdentifier = getSpecialtieIdentifier(specialtieItem);
        if (specialtieIdentifier == null || specialtieCollectionIdentifiers.includes(specialtieIdentifier)) {
          return false;
        }
        specialtieCollectionIdentifiers.push(specialtieIdentifier);
        return true;
      });
      return [...specialtiesToAdd, ...specialtieCollection];
    }
    return specialtieCollection;
  }
}
