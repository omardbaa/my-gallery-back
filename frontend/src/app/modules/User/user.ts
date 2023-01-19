import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [],
  imports: [CommonModule],
})
export class User {
  id!: number;
  firstName!: string;
  lastName!: string;
  username!: string;
  password!: string;
  photo!: string;
  email!: string;
  phone!: string;
  birthday!: Date;
  country!: string;
  city!: string;
  address!: string;
  active!: boolean;

  roles!: String[];
}
