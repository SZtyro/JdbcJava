import { TestBed } from '@angular/core/testing';

import { GmailService } from './gmail.service';

describe('GmailService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GmailService = TestBed.get(GmailService);
    expect(service).toBeTruthy();
  });
});
