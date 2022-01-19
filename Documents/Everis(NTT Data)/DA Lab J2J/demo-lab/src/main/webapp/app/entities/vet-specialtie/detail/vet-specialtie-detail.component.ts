import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVetSpecialtie } from '../vet-specialtie.model';

@Component({
  selector: 'app-vet-specialtie-detail',
  templateUrl: './vet-specialtie-detail.component.html',
})
export class VetSpecialtieDetailComponent implements OnInit {
  vetSpecialtie: IVetSpecialtie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vetSpecialtie }) => {
      this.vetSpecialtie = vetSpecialtie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
