import { MatMenuModule } from '@angular/material/menu';
import { MatDialog, MatDialogModule } from '@angular/material';
import { SharedService } from 'src/app/services/shared.service';
import { TranslateService, TranslateModule } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';

import { WelcomePageComponent } from './welcome-page.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('WelcomePageComponent', () => {
  let component: WelcomePageComponent;
  let fixture: ComponentFixture<WelcomePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot(), MatDialogModule, MatMenuModule],
      providers: [SharedService],
      declarations: [ WelcomePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should inject TranslateService', inject([TranslateService], (translate: TranslateService) => {
    expect(translate).toBeTruthy();
  }))

  it('should inject SharedService', inject([SharedService], (shared: SharedService) => {
    expect(shared).toBeTruthy();
  }))

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
