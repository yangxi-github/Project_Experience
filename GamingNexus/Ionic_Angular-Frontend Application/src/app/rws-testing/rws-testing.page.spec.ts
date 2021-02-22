import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { RwsTestingPage } from './rws-testing.page';

describe('RwsTestingPage', () => {
  let component: RwsTestingPage;
  let fixture: ComponentFixture<RwsTestingPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RwsTestingPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(RwsTestingPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
