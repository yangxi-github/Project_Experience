import { Component, ViewChild } from '@angular/core';
import { NavController } from '@ionic/angular';
import { CommonService } from '../services/common.service';
import { AlertController } from '@ionic/angular';
import { Router } from '@angular/router';


@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss']
})
export class Tab1Page {

  config: any = {};

  @ViewChild('slide1', null) slide1;

  //Sldier settings
  slidesOpts = {
    speed: 400,
    autoplay: {
      delay: 2000,
    },
    loop: true
  }

  sliderList: any[] = [];
  popularList: any[] = [];


  constructor(public navController: NavController, public common: CommonService,
    public alertController: AlertController, public router: Router) {
    this.config = this.common.config;
    this.getPopularList();
    this.getSliderList();
  }


  //Retrieve a list of products with tag - Slider
  getSliderList() {
    var api = "Game/filterGamesByTags?tagId=" + '5';
    this.common.ajaxGet(api).then((response: any) => {
      console.log('********* DONE')
      console.log(response.games)
      this.sliderList = response.games;
    })
  }

  //Retrieve a list of products with tag - Popular 
  getPopularList() {
    var api = "Game/filterGamesByTags?tagId=" + '1';
    this.common.ajaxGet(api).then((response: any) => {
      console.log('********* DONE')
      console.log(response.games)
      this.popularList = response.games;
    })
  }


  //Check if the tapped product has parentAdvisory. 
  //It will prompt an alert to confirm age, will only direct to the product details page when user tap confirm.
  async parentAdvisoryConfirm(productId: Number, parentAdvisory: Boolean) {

    if (parentAdvisory == true) {

      const alert = await this.alertController.create({
        header: 'WARNING!',
        message: 'THIS GAME MAY CONTAIN CONTENT NOT APPROPRIATE FOR ALL AGES. ARE YOU ABOVE AGE 21?',
        buttons: [
          {
            text: 'No',
            role: 'cancel',
            cssClass: 'secondary',
            handler: (blah) => {
              console.log('Confirm Cancel: blah');
            }
          }, {
            text: 'Yes',
            handler: () => {
              console.log(productId);
              console.log('Confirm Okay');
              this.router.navigate(['/product-details'], {
                queryParams: { id: productId }
              });
            }
          }
        ]
      });

      await alert.present();
    } else {
      this.router.navigate(['/product-details'], {
        queryParams: { id: productId }
      });
    }
  }


  //When finish touching on the slider, the slider will auto player again
  slideTouchEnd() {
    this.slide1.startAutoplay();
  }


  //Redirect to search page
  goToSearchPage() {
    this.navController.navigateForward('/search');
  }


}
