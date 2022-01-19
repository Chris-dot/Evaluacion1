import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialtie } from '../specialtie.model';

@Component({
  selector: 'app-specialtie-detail',
  templateUrl: './specialtie-detail.component.html',
})
export class SpecialtieDetailComponent implements OnInit {
  specialtie: ISpecialtie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialtie }) => {
      this.specialtie = specialtie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
