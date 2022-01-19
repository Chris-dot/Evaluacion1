import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SpecialtieService } from '../service/specialtie.service';
import { ISpecialtie, Specialtie } from '../specialtie.model';

import { SpecialtieUpdateComponent } from './specialtie-update.component';

describe('Specialtie Management Update Component', () => {
  let comp: SpecialtieUpdateComponent;
  let fixture: ComponentFixture<SpecialtieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let specialtieService: SpecialtieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SpecialtieUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SpecialtieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpecialtieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    specialtieService = TestBed.inject(SpecialtieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const specialtie: ISpecialtie = { id: 456 };

      activatedRoute.data = of({ specialtie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(specialtie));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specialtie>>();
      const specialtie = { id: 123 };
      jest.spyOn(specialtieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialtie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specialtie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(specialtieService.update).toHaveBeenCalledWith(specialtie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specialtie>>();
      const specialtie = new Specialtie();
      jest.spyOn(specialtieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialtie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specialtie }));
      saveSubject.complete();

      // THEN
      expect(specialtieService.create).toHaveBeenCalledWith(specialtie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specialtie>>();
      const specialtie = { id: 123 };
      jest.spyOn(specialtieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specialtie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(specialtieService.update).toHaveBeenCalledWith(specialtie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
