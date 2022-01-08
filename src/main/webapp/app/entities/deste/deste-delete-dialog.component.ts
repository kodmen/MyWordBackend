import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeste } from 'app/shared/model/deste.model';
import { DesteService } from './deste.service';

@Component({
  templateUrl: './deste-delete-dialog.component.html',
})
export class DesteDeleteDialogComponent {
  deste?: IDeste;

  constructor(protected desteService: DesteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.desteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('desteListModification');
      this.activeModal.close();
    });
  }
}
