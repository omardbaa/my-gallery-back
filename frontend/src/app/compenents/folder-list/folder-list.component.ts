import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { FolderModule } from 'src/app/modules/folder/folder.module';
import { FolderService } from 'src/app/services/folder.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { FileService } from 'src/app/services/file.service';
import { FileModule } from 'src/app/modules/file/file.module';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-folder-list',
  templateUrl: './folder-list.component.html',
  styleUrls: ['./folder-list.component.css'],
})
export class FolderListComponent {
  folderId!: number;
  folders: FolderModule[] = [];
  folder: FolderModule = new FolderModule();
  allFiles: any = [];
  file: FileModule = new FileModule();
  id!: number;

  pageSize = 0;
  perPage = 6;
  p: number = 1;
  name = '';
  size = '';
  extension = '';
  folderName = '';
  idFile = '';

  @ViewChild(MatSidenav)
  details!: MatSidenav;

  constructor(
    private folderService: FolderService,
    private router: Router,

    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getFolders();
  }

  saveFolder() {
    this.folderService.createFolder(this.folder).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => console.log(error)
    );
    this.getFolders;
  }

  getFolders() {
    this.folderService.getFolderList().subscribe((data) => {
      this.folders = data;

      console.log('data ', this.folders);
    });
  }

  folderDetails(folderId: number) {
    this.router.navigate(['/folder-details', folderId]);

    console.log('folderId:', folderId);
  }

  onSubmit() {
    console.log(this.folder);
    this.saveFolder();
    this.getFolders();
  }

  deleteFolder(id: number) {
    if (window.confirm('Are you sure you want to delete this folder?')) {
      this.folderService.deleteFolder(id).subscribe((data) => {
        console.log(data);
        this.getFolders();
      });
    }
  }

  types: any = {
    png: {
      icon: 'fa fa-light fa-image text-info',
      class: 'info',
    },
    pdf: {
      icon: 'fa fa-file-pdf-o text-danger',
      class: 'danger',
    },
    csv: {
      icon: 'fa fa-file-excel-o text-success',
      class: 'success',
    },
    txt: {
      icon: 'fa fa-file-text-o text-secondary',
      class: 'gold',
    },
    pptx: {
      icon: 'fa fa-file-powerpoint-o text-warning',
      class: 'warning',
    },
    mp4: {
      icon: 'fa fa-file-video-o text-dark',
      class: 'dark',
    },

    rar: {
      icon: 'fa fa-file-video-o text-dark',
      class: 'dark',
    },
  };

  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  upload(folderId: number, folderName: string): void {
    this.progress = 0;
    console.log('folderId', folderId);
    console.log('folderName', folderName);
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.folderService.upload(this.currentFile).subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round((100 * event.loaded) / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
            }
          },
          error: (err: any) => {
            console.log(err);
            this.progress = 0;

            if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Could not upload the file!';
            }

            this.currentFile = undefined;
          },
        });
      }

      this.selectedFiles = undefined;
    }
  }
}
