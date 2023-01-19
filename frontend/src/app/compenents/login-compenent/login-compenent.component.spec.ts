import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginCompenentComponent } from './login-compenent.component';

describe('LoginCompenentComponent', () => {
  let component: LoginCompenentComponent;
  let fixture: ComponentFixture<LoginCompenentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginCompenentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginCompenentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
