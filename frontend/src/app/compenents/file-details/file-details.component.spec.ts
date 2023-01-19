import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileDetailsComponent } from './file-details.component';

describe('FileDetailsComponent', () => {
  let component: FileDetailsComponent;
  let fixture: ComponentFixture<FileDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FileDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
