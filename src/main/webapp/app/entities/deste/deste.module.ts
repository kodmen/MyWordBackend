import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyWordsSharedModule } from 'app/shared/shared.module';
import { DesteComponent } from './deste.component';
import { DesteDetailComponent } from './deste-detail.component';
import { DesteUpdateComponent } from './deste-update.component';
import { DesteDeleteDialogComponent } from './deste-delete-dialog.component';
import { desteRoute } from './deste.route';

@NgModule({
  imports: [MyWordsSharedModule, RouterModule.forChild(desteRoute)],
  declarations: [DesteComponent, DesteDetailComponent, DesteUpdateComponent, DesteDeleteDialogComponent],
  entryComponents: [DesteDeleteDialogComponent],
})
export class MyWordsDesteModule {}
