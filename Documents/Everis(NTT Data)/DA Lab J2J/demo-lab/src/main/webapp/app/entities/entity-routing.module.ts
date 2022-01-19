import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vet',
        data: { pageTitle: 'petclinicApp.vet.home.title' },
        loadChildren: () => import('./vet/vet.module').then(m => m.VetModule),
      },
      {
        path: 'specialtie',
        data: { pageTitle: 'petclinicApp.specialtie.home.title' },
        loadChildren: () => import('./specialtie/specialtie.module').then(m => m.SpecialtieModule),
      },
      {
        path: 'vet-specialtie',
        data: { pageTitle: 'petclinicApp.vetSpecialtie.home.title' },
        loadChildren: () => import('./vet-specialtie/vet-specialtie.module').then(m => m.VetSpecialtieModule),
      },
      {
        path: 'type',
        data: { pageTitle: 'petclinicApp.type.home.title' },
        loadChildren: () => import('./type/type.module').then(m => m.TypeModule),
      },
      {
        path: 'owner',
        data: { pageTitle: 'petclinicApp.owner.home.title' },
        loadChildren: () => import('./owner/owner.module').then(m => m.OwnerModule),
      },
      {
        path: 'pet',
        data: { pageTitle: 'petclinicApp.pet.home.title' },
        loadChildren: () => import('./pet/pet.module').then(m => m.PetModule),
      },
      {
        path: 'visit',
        data: { pageTitle: 'petclinicApp.visit.home.title' },
        loadChildren: () => import('./visit/visit.module').then(m => m.VisitModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
