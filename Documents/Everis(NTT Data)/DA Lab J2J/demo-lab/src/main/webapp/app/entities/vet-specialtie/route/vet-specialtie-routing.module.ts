import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VetSpecialtieComponent } from '../list/vet-specialtie.component';
import { VetSpecialtieDetailComponent } from '../detail/vet-specialtie-detail.component';
import { VetSpecialtieUpdateComponent } from '../update/vet-specialtie-update.component';
import { VetSpecialtieRoutingResolveService } from './vet-specialtie-routing-resolve.service';

const vetSpecialtieRoute: Routes = [
  {
    path: '',
    component: VetSpecialtieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VetSpecialtieDetailComponent,
    resolve: {
      vetSpecialtie: VetSpecialtieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VetSpecialtieUpdateComponent,
    resolve: {
      vetSpecialtie: VetSpecialtieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VetSpecialtieUpdateComponent,
    resolve: {
      vetSpecialtie: VetSpecialtieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vetSpecialtieRoute)],
  exports: [RouterModule],
})
export class VetSpecialtieRoutingModule {}
