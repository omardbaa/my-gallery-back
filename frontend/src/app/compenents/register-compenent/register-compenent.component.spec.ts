import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterCompenentComponent } from './register-compenent.component';

describe('RegisterCompenentComponent', () => {
  let component: RegisterCompenentComponent;
  let fixture: ComponentFixture<RegisterCompenentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterCompenentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterCompenentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
