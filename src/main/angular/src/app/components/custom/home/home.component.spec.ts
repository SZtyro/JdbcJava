import { RouterTestingModule } from '@angular/router/testing';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HttpDatabaseService } from './../../../modules/data-base/services/http-database.service';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { HttpClientService } from 'src/app/services/http-client.service';

import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { of } from 'rxjs';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let notifications = [{ author: 'test', message: 'test' }];

  let route = {
    data: of({
      notifications: notifications,
      companies: [{}]
    })
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'settings/company', redirectTo: '' }]),

      ],
      declarations: [HomeComponent],
      providers: [HttpClient, HttpDatabaseService, HttpHandler, {
        provide: ActivatedRoute, useValue: route
      }]

    })
      .compileComponents();

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should inject HttpClientService', inject([HttpClientService], (http: HttpClientService) => {
    expect(http).toBeTruthy();
  }))

  it('should inject Router', inject([Router], (router: Router) => {
    expect(router).toBeTruthy();
  }))

  it('should inject ActivatedRoute', inject([ActivatedRoute], (route: Router) => {
    expect(route).toBeTruthy();
  }))

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set notifications', () => {
   expect(component.notifications).toEqual(notifications);
  });

});
