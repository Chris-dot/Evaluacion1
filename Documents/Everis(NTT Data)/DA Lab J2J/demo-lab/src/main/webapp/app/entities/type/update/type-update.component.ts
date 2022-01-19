import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IType, Type } from '../type.model';
import { TypeService } from '../service/type.service';

@Component({
  selector: 'app-type-update',
  templateUrl: './type-update.component.html',
})
export class TypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(80)]],
  });

  constructor(protected typeService: TypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ type }) => {
      this.updateForm(type);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const type = this.createFromForm();
    if (type.id !== undefined) {
      this.subscribeToSaveResponse(this.typeService.update(type));
    } else {
      this.subscribeToSaveResponse(this.typeService.create(type));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IType>>): void {
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

  protected updateForm(type: IType): void {
    this.editForm.patchValue({
      id: type.id,
      name: type.name,
    });
  }

  protected createFromForm(): IType {
    return {
      ...new Type(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
