import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';

import { NgForm } from '@angular/forms';
import { SessionService } from '../services/session.service';
import { CustomerService } from '../services/customer.service';
import { EventService } from '../services/event.service';
import { Customer } from '../Entity/customer';
import { StorageService } from '../services/storage.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})

export class LoginPage implements OnInit {

  submitted: boolean;
  username: string;
  password: string;
  loginError: boolean;
  errorMessage: string;

  constructor(public navController: NavController, public sessionService: SessionService,
    private customerService: CustomerService, public eventService: EventService, public storage: StorageService) {
    this.submitted = false;
  }

  ngOnInit() {
  }

  clear() {
    this.username = "";
    this.password = "";
  }


  //To login the customer by subscribe the RWS to the backend for authentification.
  customerLogin(customerLoginForm: NgForm) {
    this.submitted = true;

    if (customerLoginForm.valid) {
      this.sessionService.setUsername(this.username);
      this.sessionService.setPassword(this.password);

      this.customerService.customerLogin(this.username, this.password).subscribe(
        response => {
          let customer: Customer = response.customer;

          if (customer != null) {
            this.sessionService.setIsLogin(true);
            this.sessionService.setCurrentCustomer(customer);
            this.storage.set('customer', customer);
            this.loginError = false;
            this.eventService.event.emit('useraction');
            this.navController.navigateRoot('/tabs/tab4');
          }
          else {
            this.loginError = true;
          }
        },
        error => {
          this.loginError = true;
          this.errorMessage = error
        }
      );
    }
    else {
    }
  }

  //To logout the customer
  customerLogout(): void {
    this.sessionService.setIsLogin(false);
    this.sessionService.setCurrentCustomer(null);
  }

  goBack() {
    this.navController.navigateBack('/tabs/tab4');
  }

}
