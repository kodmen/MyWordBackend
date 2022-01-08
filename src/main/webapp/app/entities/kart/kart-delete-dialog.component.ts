import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKart } from 'app/shared/model/kart.model';
import { KartService } from './kart.service';

@Component({
  templateUrl: './kart-delete-dialog.component.html',
})
export class KartDeleteDialogComponent {
  kart?: IKart;

  constructor(protected kartService: KartService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kartService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kartListModification');
      this.activeModal.close();
    });
  }
}
