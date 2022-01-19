import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISpecialtie, Specialtie } from '../specialtie.model';
import { SpecialtieService } from '../service/specialtie.service';

@Component({
  selector: 'app-specialtie-update',
  templateUrl: './specialtie-update.component.html',
})
export class SpecialtieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(80)]],
  });

  constructor(protected specialtieService: SpecialtieService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialtie }) => {
      this.updateForm(specialtie);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specialtie = this.createFromForm();
    if (specialtie.id !== undefined) {
      this.subscribeToSaveResponse(this.specialtieService.update(specialtie));
    } else {
      this.subscribeToSaveResponse(this.specialtieService.create(specialtie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialtie>>): void {
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

  protected updateForm(specialtie: ISpecialtie): void {
    this.editForm.patchValue({
      id: specialtie.id,
      name: specialtie.name,
    });
  }

  protected createFromForm(): ISpecialtie {
    return {
      ...new Specialtie(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
