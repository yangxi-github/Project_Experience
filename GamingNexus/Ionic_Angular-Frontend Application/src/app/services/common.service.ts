import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  public config: any = {
    domain: 'http://127.0.0.1:8080/GamingNexusRws/Resources/'
  }

  constructor(public http: HttpClient) {

  }

 
  ajaxGet(url: String) {
    var api = this.config.domain + url;
    return new Promise((resove, reject) => {
      this.http.get(api).subscribe((response) => {
        resove(response);
      }, (error) => {
        reject(error);
      })
    })
  }

  
  ajaxPost(url: String, json: Object) {
    var api = this.config.domain + url;
    return new Promise((resove, reject) => {
      this.http.post(api, json).subscribe((response) => {
        resove(response);
      }, (error) => {
        reject(error);
      })
    })
  }
}
