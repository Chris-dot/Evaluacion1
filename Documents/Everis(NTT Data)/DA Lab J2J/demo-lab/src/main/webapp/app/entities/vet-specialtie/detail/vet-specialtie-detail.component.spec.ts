import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VetSpecialtieDetailComponent } from './vet-specialtie-detail.component';

describe('VetSpecialtie Management Detail Component', () => {
  let comp: VetSpecialtieDetailComponent;
  let fixture: ComponentFixture<VetSpecialtieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VetSpecialtieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ vetSpecialtie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VetSpecialtieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VetSpecialtieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vetSpecialtie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.vetSpecialtie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
