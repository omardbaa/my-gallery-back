import { Component } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FileModule } from 'src/app/modules/file/file.module';
import { FolderModule } from 'src/app/modules/folder/folder.module';
import { FileService } from 'src/app/services/file.service';
import { FolderService } from 'src/app/services/folder.service';

@Component({
  selector: 'app-folder-details',
  templateUrl: './folder-details.component.html',
  styleUrls: ['./folder-details.component.css'],
})
export class FolderDetailsComponent {
  pageSize = 0;
  perPage = 6;
  p: number = 1;
  name = '';
  size = '';
  extension = '';
  folderName = '';
  idFile = '';

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

  allFiles: any = [];

  folderId!: number;
  folder: FolderModule = new FolderModule();

  file: FileModule = new FileModule();

  id!: number;

  constructor(
    private folderService: FolderService,
    private fileService: FileService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.folderId = this.route.snapshot.params['id'];
    this.folder = new FolderModule();
    this.folderService.getFolderById(this.folderId).subscribe((data) => {
      this.folder = data;

      this.getFiles();
    });
  }

  onSubmit() {
    console.log(this.folder);
    this.fileFolder();
    this.getFiles();
  }

  fileFolder() {
    this.folderService.fileFolder(this.folder, this.file).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => console.log(error)
    );
  }

  deleteFile(fileId: string, folderId: number) {
    if (window.confirm('Are you sure you want to delete this file?')) {
      this.folderService.deleteFile(fileId, folderId).subscribe((data) => {
        console.log(data);
        this.getFiles();
      });
    }
  }

  fileDetails(id: string) {
    this.router.navigate(['file-details', id]);
    console.log();
  }

  private getFiles() {
    this.folderService.getAllFiles(this.folderId).subscribe((data) => {
      let files = [];
      let datalist: any = data;
      for (let a of datalist) {
        if (a.id) {
          files.push(a);
        }
      }
      this.allFiles = files;
    });
  }

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
