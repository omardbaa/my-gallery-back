import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [],
  imports: [CommonModule],
})
export class PaginatedData<T> {
  content!: T[];
  pageNo!: number;
  pageSize!: number;
  totalElements!: number;
  totalPages!: number;
  last!: boolean;
}
