import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VetSpecialtieService } from '../service/vet-specialtie.service';
import { IVetSpecialtie, VetSpecialtie } from '../vet-specialtie.model';
import { IVet } from 'app/entities/vet/vet.model';
import { VetService } from 'app/entities/vet/service/vet.service';
import { ISpecialtie } from 'app/entities/specialtie/specialtie.model';
import { SpecialtieService } from 'app/entities/specialtie/service/specialtie.service';

import { VetSpecialtieUpdateComponent } from './vet-specialtie-update.component';

describe('VetSpecialtie Management Update Component', () => {
  let comp: VetSpecialtieUpdateComponent;
  let fixture: ComponentFixture<VetSpecialtieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vetSpecialtieService: VetSpecialtieService;
  let vetService: VetService;
  let specialtieService: SpecialtieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VetSpecialtieUpdateComponent],
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
      .overrideTemplate(VetSpecialtieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VetSpecialtieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vetSpecialtieService = TestBed.inject(VetSpecialtieService);
    vetService = TestBed.inject(VetService);
    specialtieService = TestBed.inject(SpecialtieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Vet query and add missing value', () => {
      const vetSpecialtie: IVetSpecialtie = { id: 456 };
      const vet: IVet = { id: 31355 };
      vetSpecialtie.vet = vet;

      const vetCollection: IVet[] = [{ id: 28843 }];
      jest.spyOn(vetService, 'query').mockReturnValue(of(new HttpResponse({ body: vetCollection })));
      const additionalVets = [vet];
      const expectedCollection: IVet[] = [...additionalVets, ...vetCollection];
      jest.spyOn(vetService, 'addVetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vetSpecialtie });
      comp.ngOnInit();

      expect(vetService.query).toHaveBeenCalled();
      expect(vetService.addVetToCollectionIfMissing).toHaveBeenCalledWith(vetCollection, ...additionalVets);
      expect(comp.vetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Specialtie query and add missing value', () => {
      const vetSpecialtie: IVetSpecialtie = { id: 456 };
      const specialtie: ISpecialtie = { id: 95098 };
      vetSpecialtie.specialtie = specialtie;

      const specialtieCollection: ISpecialtie[] = [{ id: 55419 }];
      jest.spyOn(specialtieService, 'query').mockReturnValue(of(new HttpResponse({ body: specialtieCollection })));
      const additionalSpecialties = [specialtie];
      const expectedCollection: ISpecialtie[] = [...additionalSpecialties, ...specialtieCollection];
      jest.spyOn(specialtieService, 'addSpecialtieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vetSpecialtie });
      comp.ngOnInit();

      expect(specialtieService.query).toHaveBeenCalled();
      expect(specialtieService.addSpecialtieToCollectionIfMissing).toHaveBeenCalledWith(specialtieCollection, ...additionalSpecialties);
      expect(comp.specialtiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const vetSpecialtie: IVetSpecialtie = { id: 456 };
      const vet: IVet = { id: 4313 };
      vetSpecialtie.vet = vet;
      const specialtie: ISpecialtie = { id: 41022 };
      vetSpecialtie.specialtie = specialtie;

      activatedRoute.data = of({ vetSpecialtie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(vetSpecialtie));
      expect(comp.vetsSharedCollection).toContain(vet);
      expect(comp.specialtiesSharedCollection).toContain(specialtie);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VetSpecialtie>>();
      const vetSpecialtie = { id: 123 };
      jest.spyOn(vetSpecialtieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vetSpecialtie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vetSpecialtie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(vetSpecialtieService.update).toHaveBeenCalledWith(vetSpecialtie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VetSpecialtie>>();
      const vetSpecialtie = new VetSpecialtie();
      jest.spyOn(vetSpecialtieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vetSpecialtie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vetSpecialtie }));
      saveSubject.complete();

      // THEN
      expect(vetSpecialtieService.create).toHaveBeenCalledWith(vetSpecialtie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VetSpecialtie>>();
      const vetSpecialtie = { id: 123 };
      jest.spyOn(vetSpecialtieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vetSpecialtie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vetSpecialtieService.update).toHaveBeenCalledWith(vetSpecialtie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackVetById', () => {
      it('Should return tracked Vet primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVetById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSpecialtieById', () => {
      it('Should return tracked Specialtie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSpecialtieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
