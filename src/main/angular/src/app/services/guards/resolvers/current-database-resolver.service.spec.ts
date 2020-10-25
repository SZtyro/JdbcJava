import { TestBed } from '@angular/core/testing';

import { CurrentDatabaseResolverService } from './current-database-resolver.service';

describe('CurrentDatabaseResolverService', () => {
  let service: CurrentDatabaseResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CurrentDatabaseResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
