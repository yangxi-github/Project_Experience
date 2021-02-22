import { Component, OnInit } from '@angular/core';
import { SessionService } from '../services/session.service';
import { NavController } from '@ionic/angular';
import { StorageService } from '../services/storage.service';
import { ActionSheetController } from '@ionic/angular';
import { Customer } from '../Entity/customer';
import { CustomerService } from '../services/customer.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-customer-setting',
  templateUrl: './customer-setting.page.html',
  styleUrls: ['./customer-setting.page.scss'],
})

export class CustomerSettingPage implements OnInit {
  submitted: boolean;
  newCustomer: Customer;
  password: String;
  isEdit = false;
  infoMessage: string;
  errorMessage: string;
  registerError: boolean;

  constructor(public sessionService: SessionService, public navController: NavController,
    public storage: StorageService, public actionSheetController: ActionSheetController, private customerService: CustomerService) {
    this.submitted = false;
    this.newCustomer = this.storage.get('customer');
    this.newCustomer.password = this.sessionService.getPassword();
    console.log(this.newCustomer);
  }


  ngOnInit() {
  }

  //To log out the customer. At the same time also clear the shopping cart.
  customerLogout(): void {
    this.sessionService.setIsLogin(false);
    this.sessionService.setCurrentCustomer(null);
    this.storage.set('cartList', null);
    this.goBack();
  }

  //For use to exit the edit mode of personal information
  cancel() {
    this.isEdit = !this.isEdit;
    this.newCustomer = this.storage.get('customer');
  }

  //Utilize ActionSheet, it pops up for user the select gender. Better user interation
  async showGender() {
    const actionSheet = await this.actionSheetController.create({
      header: 'Gender',
      buttons: [{
        text: 'Male',
        handler: () => {
          this.newCustomer.gender = "Male";
        }
      },
      {
        text: 'Female',
        handler: () => {
          this.newCustomer.gender = "Female";
        }
      },
      {
        text: 'Cancel',
        role: 'cancel',
        handler: () => {
          console.log('Cancel clicked');
        }
      }]
    });
    await actionSheet.present();
  }


  //To update the customer
  //Wrap the customer information in the wrapper class and subscribe to the RWS services to send to sessionbean for updating customer.
  create(customerUpdateForm: NgForm) {
    this.submitted = true;

    if (customerUpdateForm.valid) {
      this.newCustomer.birthday = this.newCustomer.birthday.split('T')[0];
      this.customerService.customerUpdate(this.newCustomer).subscribe(
        response => {
          this.infoMessage = 'You have successfully updated';
          this.errorMessage = null;
          this.storage.set('customer', this.newCustomer);
        },
        error => {
          this.infoMessage = null;
          this.errorMessage = error;
        }
      );
    }
  }

  //Clear the form
  clear() {
    this.submitted = false;
    this.newCustomer = new Customer();
  }

  //Redirect back the tab4
  goBack() {
    this.navController.navigateBack('/tabs/tab4');
  }



}
