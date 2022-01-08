import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyWordsTestModule } from '../../../test.module';
import { DesteDetailComponent } from 'app/entities/deste/deste-detail.component';
import { Deste } from 'app/shared/model/deste.model';

describe('Component Tests', () => {
  describe('Deste Management Detail Component', () => {
    let comp: DesteDetailComponent;
    let fixture: ComponentFixture<DesteDetailComponent>;
    const route = ({ data: of({ deste: new Deste(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyWordsTestModule],
        declarations: [DesteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DesteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DesteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deste on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deste).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
