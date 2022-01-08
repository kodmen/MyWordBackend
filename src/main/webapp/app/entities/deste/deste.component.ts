import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeste } from 'app/shared/model/deste.model';
import { DesteService } from './deste.service';
import { DesteDeleteDialogComponent } from './deste-delete-dialog.component';

@Component({
  selector: 'jhi-deste',
  templateUrl: './deste.component.html',
})
export class DesteComponent implements OnInit, OnDestroy {
  destes?: IDeste[];
  eventSubscriber?: Subscription;

  constructor(protected desteService: DesteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.desteService.query().subscribe((res: HttpResponse<IDeste[]>) => (this.destes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDestes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeste): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDestes(): void {
    this.eventSubscriber = this.eventManager.subscribe('desteListModification', () => this.loadAll());
  }

  delete(deste: IDeste): void {
    const modalRef = this.modalService.open(DesteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deste = deste;
  }
}
