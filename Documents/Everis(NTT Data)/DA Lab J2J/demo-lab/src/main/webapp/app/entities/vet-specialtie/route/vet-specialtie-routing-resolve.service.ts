import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVetSpecialtie, VetSpecialtie } from '../vet-specialtie.model';
import { VetSpecialtieService } from '../service/vet-specialtie.service';

@Injectable({ providedIn: 'root' })
export class VetSpecialtieRoutingResolveService implements Resolve<IVetSpecialtie> {
  constructor(protected service: VetSpecialtieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVetSpecialtie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vetSpecialtie: HttpResponse<VetSpecialtie>) => {
          if (vetSpecialtie.body) {
            return of(vetSpecialtie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VetSpecialtie());
  }
}
