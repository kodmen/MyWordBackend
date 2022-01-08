import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyWordsTestModule } from '../../../test.module';
import { KartDetailComponent } from 'app/entities/kart/kart-detail.component';
import { Kart } from 'app/shared/model/kart.model';

describe('Component Tests', () => {
  describe('Kart Management Detail Component', () => {
    let comp: KartDetailComponent;
    let fixture: ComponentFixture<KartDetailComponent>;
    const route = ({ data: of({ kart: new Kart(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyWordsTestModule],
        declarations: [KartDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KartDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KartDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kart on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kart).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
