import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Checkout } from '../Entity/checkout';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  url: string;

  constructor(private httpClient: HttpClient) { 
    this.url = "http://localhost:8080/GamingNexusRws/Resources/SaleTransaction/createSaleTransaction";
  }

  createSaleTransacntion(createSaleTransactionReq: Checkout) {

    return this.httpClient.put<any>(this.url, createSaleTransactionReq, httpOptions).pipe
      (
        catchError(this.handleError)
      );
  }


  private handleError(error: HttpErrorResponse) {
    let errorMessage: string = '';

    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An unknown error has occurred: ' + error.error.message;
    }
    else {
      errorMessage = 'An HTTP error has occurred: ' + `HTTP ${error.status}: ${error.error.message}`;
    }

    return throwError(errorMessage)
  }
}
