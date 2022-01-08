import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKart } from 'app/shared/model/kart.model';
import { KartService } from './kart.service';
import { KartDeleteDialogComponent } from './kart-delete-dialog.component';

@Component({
  selector: 'jhi-kart',
  templateUrl: './kart.component.html',
})
export class KartComponent implements OnInit, OnDestroy {
  karts?: IKart[];
  eventSubscriber?: Subscription;

  constructor(protected kartService: KartService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.kartService.query().subscribe((res: HttpResponse<IKart[]>) => (this.karts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKarts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKart): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKarts(): void {
    this.eventSubscriber = this.eventManager.subscribe('kartListModification', () => this.loadAll());
  }

  delete(kart: IKart): void {
    const modalRef = this.modalService.open(KartDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kart = kart;
  }
}
