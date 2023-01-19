import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class FolderModule {

  folderId!:   number;
  folderName!: string;
  files!:      File[];
}
