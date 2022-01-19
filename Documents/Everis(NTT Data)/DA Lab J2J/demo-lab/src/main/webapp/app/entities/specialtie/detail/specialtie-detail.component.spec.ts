import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpecialtieDetailComponent } from './specialtie-detail.component';

describe('Specialtie Management Detail Component', () => {
  let comp: SpecialtieDetailComponent;
  let fixture: ComponentFixture<SpecialtieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecialtieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ specialtie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SpecialtieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SpecialtieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load specialtie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.specialtie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
