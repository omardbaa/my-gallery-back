import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FolderDetailsComponent } from './folder-details.component';

describe('FolderDetailsComponent', () => {
  let component: FolderDetailsComponent;
  let fixture: ComponentFixture<FolderDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FolderDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FolderDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
