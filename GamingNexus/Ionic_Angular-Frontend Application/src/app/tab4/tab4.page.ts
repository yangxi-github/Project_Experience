import { Component, OnInit } from '@angular/core';
import { NavController, IonContent } from '@ionic/angular';
import { Customer } from "../Entity/Customer";
import { StorageService } from '../services/storage.service';

import { EventService } from '../services/event.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-tab4',
  templateUrl: './tab4.page.html',
  styleUrls: ['./tab4.page.scss'],
})

export class Tab4Page implements OnInit {

  customer: Customer;
  login: boolean;

  constructor(public storage: StorageService, public eventService: EventService,
    public sessionService: SessionService, public navController: NavController) {
    this.login = false;
    var customer = this.storage.get('customer');
    if (customer && customer.username) {
      this.customer = customer;
      this.login = this.sessionService.getIsLogin();
    }
  }

//Init. To get the signed in customer.
  ngOnInit() {
    this.eventService.event.on('useraction', () => {
      var customer = this.storage.get('customer');
      if (customer && customer.username) {
        this.customer = customer;
        this.login = this.sessionService.getIsLogin();
      }
    })
  }

//When customer tap on View SalesTranscation, if customer is not logged in, it will redirect to login page
  redirectToViewOrderPage() {
    if (this.login != true) {
      this.navController.navigateForward('/login');
    } else if (this.login == true) {
      this.navController.navigateForward('/view-order');
    }
  }

}
