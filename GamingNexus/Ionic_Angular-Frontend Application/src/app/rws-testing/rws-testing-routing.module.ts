import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RwsTestingPage } from './rws-testing.page';

const routes: Routes = [
  {
    path: '',
    component: RwsTestingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RwsTestingPageRoutingModule {}
