import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyWordsTestModule } from '../../../test.module';
import { DesteComponent } from 'app/entities/deste/deste.component';
import { DesteService } from 'app/entities/deste/deste.service';
import { Deste } from 'app/shared/model/deste.model';

describe('Component Tests', () => {
  describe('Deste Management Component', () => {
    let comp: DesteComponent;
    let fixture: ComponentFixture<DesteComponent>;
    let service: DesteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyWordsTestModule],
        declarations: [DesteComponent],
      })
        .overrideTemplate(DesteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DesteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DesteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Deste(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.destes && comp.destes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
