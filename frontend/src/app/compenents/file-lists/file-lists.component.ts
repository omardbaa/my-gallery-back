import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FileModule } from 'src/app/modules/file/file.module';
import { PaginatedData } from 'src/app/modules/FilePage/PaginatedData';
import { FileService } from 'src/app/services/file.service';
import { SearchComponent } from '../search/search.component';
import { catchError, of, tap } from 'rxjs';
import * as saveAs from 'file-saver';

@Component({
  selector: 'app-file-lists',

  templateUrl: './file-lists.component.html',
  styleUrls: ['./file-lists.component.css'],
})
export class FileListsComponent implements OnInit {
  files: FileModule[] = [];

  // name=this.file.extension;

  name = '';
  size = '';
  extension = '';
  folderName = '';
  id = '';
  tag = '';
  pageSize!: number;
  pageNo!: number;

  totalElements!: number;
  totalPages!: number;
  last!: boolean;

  types: any = {
    png: {
      icon: 'fa fa-light fa-image text-info',
      class: 'info',
    },

    jpg: {
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

    docx: {
      icon: 'fa fa-file-text-o text-secondary',
      class: 'gold',
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

    mp3: {
      icon: 'fa fa-file-audio-o text-dark',
      class: 'dark',
    },

    rar: {
      icon: 'fa fa-file-video-o text-dark',
      class: 'dark',
    },
  };

  data!: PaginatedData<FileModule>;

  file?: FileModule;

  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';

  constructor(private fileSrvice: FileService, private router: Router) {}

  ngOnInit(): void {
    this.getFiles();
  }

  private getFiles() {
    this.fileSrvice.getFiles().subscribe((data) => {
      this.files = data.content;
      this.pageNo = data.pageNo;
      this.pageSize = data.pageSize;
      this.totalElements = data.totalElements;
      this.totalPages = data.totalPages;
      this.last = data.last;

      console.log('data', this.files);
    });
  }

  deleteFile(id: string) {
    if (window.confirm('Are you sure you want to delete this file?')) {
      this.fileSrvice.deleteFile(id).subscribe((data) => {
        console.log(data);
        this.getFiles();
      });
    }
  }

  download(id: string, extension: string) {
    this.fileSrvice.downloadFile(id, extension).subscribe();
  }

  readFile(id: string, extension: string) {
    this.fileSrvice.renderFile(id, extension).subscribe();
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  upload(): void {
    this.progress = 0;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;
        this.fileSrvice.upload(this.currentFile).subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round((100 * event.loaded) / event.total);
              this.getFiles();
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
            } else {
              this.progress = 0;
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
            this.progress = 0;
            this.currentFile = undefined;
          },
        });
      }

      this.selectedFiles = undefined;
    }
  }

  fileDetails(id: string) {
    this.router.navigate(['file-details', id]);
    console.log();
  }
  fetchNextPage(e: any) {
    this.fileSrvice.getFilesByPageNumber(e - 1).subscribe((data) => {
      //@ts-ignore

      this.files = data.content;
      this.pageNo = data.pageNo;
      this.pageSize = data.pageSize;
      this.totalElements = data.totalElements;
      this.totalPages = data.totalPages;
      this.last = data.last;
      //@ts-ignore

      console.log('data ', this.files);
    });
    console.log('e', e);
  }
}
