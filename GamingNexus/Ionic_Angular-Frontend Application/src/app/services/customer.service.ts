import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Customer } from '../Entity/customer';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  baseUrl: string;

  constructor(private httpClient: HttpClient,
    private sessionService: SessionService) {
    this.baseUrl = this.sessionService.getRootPath() + 'Customer';
  }

  customerLogin(username: string, password: string): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/customerLogin?username=" + username + "&password=" + password).pipe
      (
        catchError(this.handleError)
      );
  }

  customerRegister(newCustomer: Customer): Observable<any> {
    let customerRegisterReq = { 'newCustomer': newCustomer };

    return this.httpClient.put<any>(this.baseUrl + "/customerRegister", customerRegisterReq, httpOptions).pipe
      (
        catchError(this.handleError)
      );

  }

  customerUpdate(newCustomer: Customer): Observable<any> {
    let updateCustomerReq = {
      'username': this.sessionService.getUsername(),
      'password': this.sessionService.getPassword(),
      'customer': newCustomer
    };

    return this.httpClient.post<any>(this.baseUrl + "/customerUpdate", updateCustomerReq, httpOptions).pipe
      (
        catchError(this.handleError)
      );

  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string = "";

    if (error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error has occurred: " + error.error.message;
    }
    else {
      errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
    }

    console.error(errorMessage);

    return throwError(errorMessage);
  }


}
