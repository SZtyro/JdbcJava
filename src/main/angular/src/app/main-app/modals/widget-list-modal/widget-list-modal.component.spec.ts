import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetListModalComponent } from './widget-list-modal.component';

describe('WidgetListModalComponent', () => {
  let component: WidgetListModalComponent;
  let fixture: ComponentFixture<WidgetListModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WidgetListModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetListModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
