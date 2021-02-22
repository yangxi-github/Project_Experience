import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CustomerSettingPage } from './customer-setting.page';

const routes: Routes = [
  {
    path: '',
    component: CustomerSettingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CustomerSettingPageRoutingModule {}
