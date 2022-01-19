import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVetSpecialtie, VetSpecialtie } from '../vet-specialtie.model';
import { VetSpecialtieService } from '../service/vet-specialtie.service';
import { IVet } from 'app/entities/vet/vet.model';
import { VetService } from 'app/entities/vet/service/vet.service';
import { ISpecialtie } from 'app/entities/specialtie/specialtie.model';
import { SpecialtieService } from 'app/entities/specialtie/service/specialtie.service';

@Component({
  selector: 'app-vet-specialtie-update',
  templateUrl: './vet-specialtie-update.component.html',
})
export class VetSpecialtieUpdateComponent implements OnInit {
  isSaving = false;

  vetsSharedCollection: IVet[] = [];
  specialtiesSharedCollection: ISpecialtie[] = [];

  editForm = this.fb.group({
    id: [],
    vet: [null, Validators.required],
    specialtie: [null, Validators.required],
  });

  constructor(
    protected vetSpecialtieService: VetSpecialtieService,
    protected vetService: VetService,
    protected specialtieService: SpecialtieService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vetSpecialtie }) => {
      this.updateForm(vetSpecialtie);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vetSpecialtie = this.createFromForm();
    if (vetSpecialtie.id !== undefined) {
      this.subscribeToSaveResponse(this.vetSpecialtieService.update(vetSpecialtie));
    } else {
      this.subscribeToSaveResponse(this.vetSpecialtieService.create(vetSpecialtie));
    }
  }

  trackVetById(index: number, item: IVet): number {
    return item.id!;
  }

  trackSpecialtieById(index: number, item: ISpecialtie): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVetSpecialtie>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vetSpecialtie: IVetSpecialtie): void {
    this.editForm.patchValue({
      id: vetSpecialtie.id,
      vet: vetSpecialtie.vet,
      specialtie: vetSpecialtie.specialtie,
    });

    this.vetsSharedCollection = this.vetService.addVetToCollectionIfMissing(this.vetsSharedCollection, vetSpecialtie.vet);
    this.specialtiesSharedCollection = this.specialtieService.addSpecialtieToCollectionIfMissing(
      this.specialtiesSharedCollection,
      vetSpecialtie.specialtie
    );
  }

  protected loadRelationshipsOptions(): void {
    this.vetService
      .query()
      .pipe(map((res: HttpResponse<IVet[]>) => res.body ?? []))
      .pipe(map((vets: IVet[]) => this.vetService.addVetToCollectionIfMissing(vets, this.editForm.get('vet')!.value)))
      .subscribe((vets: IVet[]) => (this.vetsSharedCollection = vets));

    this.specialtieService
      .query()
      .pipe(map((res: HttpResponse<ISpecialtie[]>) => res.body ?? []))
      .pipe(
        map((specialties: ISpecialtie[]) =>
          this.specialtieService.addSpecialtieToCollectionIfMissing(specialties, this.editForm.get('specialtie')!.value)
        )
      )
      .subscribe((specialties: ISpecialtie[]) => (this.specialtiesSharedCollection = specialties));
  }

  protected createFromForm(): IVetSpecialtie {
    return {
      ...new VetSpecialtie(),
      id: this.editForm.get(['id'])!.value,
      vet: this.editForm.get(['vet'])!.value,
      specialtie: this.editForm.get(['specialtie'])!.value,
    };
  }
}
