import { Component, OnInit } from '@angular/core';

import { Customer } from "../Entity/Customer";
import { StorageService } from '../services/storage.service';
import { CommonService } from '../services/common.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.page.html',
  styleUrls: ['./view-order.page.scss'],
})
export class ViewOrderPage implements OnInit {

  customer: Customer;
  saleTransactionList: any;
  saleTransactionLineItems: any;

  constructor(public common: CommonService, public storage: StorageService, public sessionService: SessionService) {
    var customer = this.storage.get('customer');
    if (customer && customer.username) {
      this.customer = customer;
    }
  }

  ngOnInit() {
    var api = "SaleTransaction/retrieveAllSaleTransactionByUsernameAndPassword?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword();
      this.common.ajaxGet(api).then((response: any) => {
        console.log(response.saleTransactions);
        this.saleTransactionList = response.saleTransactions;
      })
  }


}
