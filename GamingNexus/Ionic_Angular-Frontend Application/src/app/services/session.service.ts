import { Injectable } from '@angular/core';
import { Platform } from '@ionic/angular';

import { Customer } from '../Entity/customer';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private platform: Platform) {
  }


  getRootPath(): string {
    console.log('this.platform.is("hybrid"): ' + this.platform.is('hybrid'));

    if (this.platform.is('hybrid')) {
      return "http://127.0.0.1:8080/GamingNexusRws/Resources/";
    }
    else {
      return "/api/";
    }
  }

  getIsLogin(): boolean {
    if (sessionStorage.isLogin == "true") {
      return true;
    }
    else {
      return false;
    }
  }

  setIsLogin(isLogin: boolean): void {
    sessionStorage.isLogin = isLogin;
  }


  getCurrentCustomer(): Customer {
    return JSON.parse(sessionStorage.currentCustomer);
  }


  setCurrentCustomer(currentCustomer: Customer): void {
    sessionStorage.currentCustomer = JSON.stringify(currentCustomer);
  }


  getUsername(): string {
    return sessionStorage.username;
  }


  setUsername(username: string): void {
    sessionStorage.username = username;
  }


  getPassword(): string {
    return sessionStorage.password;
  }

  setPassword(password: string): void {
    sessionStorage.password = password;
  }

}
