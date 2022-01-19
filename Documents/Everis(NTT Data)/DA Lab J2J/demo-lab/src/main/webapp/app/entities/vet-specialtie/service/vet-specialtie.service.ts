import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVetSpecialtie, getVetSpecialtieIdentifier } from '../vet-specialtie.model';

export type EntityResponseType = HttpResponse<IVetSpecialtie>;
export type EntityArrayResponseType = HttpResponse<IVetSpecialtie[]>;

@Injectable({ providedIn: 'root' })
export class VetSpecialtieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vet-specialties');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vetSpecialtie: IVetSpecialtie): Observable<EntityResponseType> {
    return this.http.post<IVetSpecialtie>(this.resourceUrl, vetSpecialtie, { observe: 'response' });
  }

  update(vetSpecialtie: IVetSpecialtie): Observable<EntityResponseType> {
    return this.http.put<IVetSpecialtie>(`${this.resourceUrl}/${getVetSpecialtieIdentifier(vetSpecialtie) as number}`, vetSpecialtie, {
      observe: 'response',
    });
  }

  partialUpdate(vetSpecialtie: IVetSpecialtie): Observable<EntityResponseType> {
    return this.http.patch<IVetSpecialtie>(`${this.resourceUrl}/${getVetSpecialtieIdentifier(vetSpecialtie) as number}`, vetSpecialtie, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVetSpecialtie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVetSpecialtie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVetSpecialtieToCollectionIfMissing(
    vetSpecialtieCollection: IVetSpecialtie[],
    ...vetSpecialtiesToCheck: (IVetSpecialtie | null | undefined)[]
  ): IVetSpecialtie[] {
    const vetSpecialties: IVetSpecialtie[] = vetSpecialtiesToCheck.filter(isPresent);
    if (vetSpecialties.length > 0) {
      const vetSpecialtieCollectionIdentifiers = vetSpecialtieCollection.map(
        vetSpecialtieItem => getVetSpecialtieIdentifier(vetSpecialtieItem)!
      );
      const vetSpecialtiesToAdd = vetSpecialties.filter(vetSpecialtieItem => {
        const vetSpecialtieIdentifier = getVetSpecialtieIdentifier(vetSpecialtieItem);
        if (vetSpecialtieIdentifier == null || vetSpecialtieCollectionIdentifiers.includes(vetSpecialtieIdentifier)) {
          return false;
        }
        vetSpecialtieCollectionIdentifiers.push(vetSpecialtieIdentifier);
        return true;
      });
      return [...vetSpecialtiesToAdd, ...vetSpecialtieCollection];
    }
    return vetSpecialtieCollection;
  }
}
