import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MyWordsTestModule } from '../../../test.module';
import { DesteUpdateComponent } from 'app/entities/deste/deste-update.component';
import { DesteService } from 'app/entities/deste/deste.service';
import { Deste } from 'app/shared/model/deste.model';

describe('Component Tests', () => {
  describe('Deste Management Update Component', () => {
    let comp: DesteUpdateComponent;
    let fixture: ComponentFixture<DesteUpdateComponent>;
    let service: DesteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyWordsTestModule],
        declarations: [DesteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DesteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DesteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DesteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Deste(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Deste();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
