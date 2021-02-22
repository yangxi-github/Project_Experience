import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { ActionSheetController } from '@ionic/angular';

import { NgForm } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { Customer } from '../Entity/customer';



@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})

export class RegisterPage implements OnInit {

  submitted: boolean;
  newCustomer: Customer;
  infoMessage: string;
  errorMessage: string;
  registerError: boolean;

  constructor(public navController: NavController, private customerService: CustomerService,
    public actionSheetController: ActionSheetController) {
    this.submitted = false;
    this.newCustomer = new Customer();
    this.newCustomer.birthday = "2000-01-01";
  }

  ngOnInit() {
  }


  //Clear the sign up form
  clear() {
    this.submitted = false;
    this.newCustomer = new Customer();
  }


  //Register the customer through subscribing to the RWS and connect to the backend to create the data
  create(customerRegistrationForm: NgForm) {
    this.submitted = true;

    if (customerRegistrationForm.valid) {
      this.newCustomer.birthday = this.newCustomer.birthday.split('T')[0];
      this.customerService.customerRegister(this.newCustomer).subscribe(
        response => {
          this.infoMessage = 'You have successfully registered';
          this.errorMessage = null;
        },
        error => {
          this.infoMessage = null;
          this.errorMessage = error;
        }
      );
    }
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

  //Redirect to login page after successful sign up.
  goBack() {
    this.navController.navigateBack('/login');
  }

  //Parse the selected time so it only contains YYYY-MM-DD without time.
  changeDate() {
    this.newCustomer.birthday = this.newCustomer.birthday.split('T')[0];
  }

}
