import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GmailWidgetComponent } from './gmail-widget.component';

describe('GmailWidgetComponent', () => {
  let component: GmailWidgetComponent;
  let fixture: ComponentFixture<GmailWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GmailWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GmailWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
