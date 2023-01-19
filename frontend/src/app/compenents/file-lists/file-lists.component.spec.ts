import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileListsComponent } from './file-lists.component';

describe('FileListsComponent', () => {
  let component: FileListsComponent;
  let fixture: ComponentFixture<FileListsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FileListsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FileListsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
