import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileModule } from '../file/file.module';

@NgModule({
  declarations: [],
  imports: [CommonModule],
})
export class Tag {
  id!: number;
  tagName!: string;
}
