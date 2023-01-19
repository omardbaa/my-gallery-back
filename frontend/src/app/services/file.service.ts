import { Injectable } from '@angular/core';
import { catchError, map, Observable, of, switchMap, tap } from 'rxjs';

import {
  HttpClient,
  HttpEvent,
  HttpRequest,
  HttpHeaders,
  HttpResponse,
} from '@angular/common/http';
import { PaginatedData } from '../modules/FilePage/PaginatedData';
import { FileModule } from '../modules/file/file.module';
import { BASE_URL } from '../Constants';
import { USER_KEY } from './storage.service';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  private baseURL = `${BASE_URL}/file`;

  constructor(private httpClient: HttpClient) {}

  upload(file: File) {
    const formData = new FormData();
    formData.append('file', file);

    const headers = new HttpHeaders({
      Authorization: `Bearer ${
        JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
      }`,
    });

    const req = new HttpRequest('POST', `${this.baseURL}/upload`, formData, {
      headers,
      responseType: 'text' as 'json',
    });
    return this.httpClient.request(req);
  }

  getFiles(): Observable<PaginatedData<FileModule>> {
    return this.httpClient.get<PaginatedData<FileModule>>(
      `${this.baseURL}/files`,
      {
        headers: {
          Authorization: `Bearer ${
            JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
          }`,
        },
      }
    );
  }

  getAllFiles(pageNo: number): Observable<PaginatedData<FileModule>> {
    return this.httpClient.get<PaginatedData<FileModule>>(
      `${this.baseURL}/files?pageNo` + pageNo,
      {
        responseType: 'text' as 'json',
        headers: {
          Authorization: `Bearer ${
            JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
          }`,
        },
      }
    );
  }

  getFilesByPageNumber(pageNo: number): Observable<PaginatedData<FileModule>> {
    return this.httpClient.get<PaginatedData<FileModule>>(
      `${this.baseURL}/files`,
      {
        params: { pageNo },

        headers: {
          Authorization: `Bearer ${
            JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
          }`,
        },
      }
    );
  }

  file!: FileModule;

  getFileById(id: string): Observable<FileModule> {
    return this.httpClient.get<FileModule>(`${this.baseURL}/files/${id}`, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  downloadFile(name: string, extension: string): Observable<FileModule> {
    return this.httpClient
      .get(`${this.baseURL}/download/` + name + '.' + extension, {
        headers: {
          Authorization: `Bearer ${
            JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
          }`,
        },
        responseType: 'blob',
      })
      .pipe(
        tap((res) => saveAs(res, name)),
        catchError((error) => {
          console.log(error);
          alert('Error while trying to download the file!');
          return of(error);
        })
      );
  }

  renderFile(name: string, extension: string): Observable<Blob> {
    return this.httpClient
      .get(`${this.baseURL}/display/` + name + '.' + extension, {
        headers: {
          Authorization: `Bearer ${
            JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
          }`,
        },
        responseType: 'blob',
      })
      .pipe(
        catchError((error) => {
          console.log(error);
          alert('Error while trying to display the file!');
          return of(error);
        })
      );
  }

  updateFile(id: string, file: FileModule): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, file, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  deleteFile(id: string): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  // renderFile(fileName: string, token: string): Observable<Blob> {
  //   const headers = new HttpHeaders({
  //     Authorization: `Bearer ${token}`,
  //   });
  //   return this.httpClient.get(`${this.baseURL}/${fileName}`, {
  //     headers,
  //     responseType: 'blob',
  //   });
  // }

  deleteTag(fileId: String, tagId: number): Observable<Object> {
    return this.httpClient.delete(
      `${this.baseURL}/deleteTag/${fileId}/tags/${tagId}`,
      {
        headers: {
          Authorization: `Bearer ${
            JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
          }`,
        },
      }
    );
  }

  /*getAllFilesOfFolder(id: number): Observable<FileModule>{
  return this.httpClient.get<FileModule>(`${this.baseURL}/${id}`+'/files');
}*/

  getTags(id: string): Observable<FileModule> {
    return this.httpClient.get<FileModule>(`${this.baseURL}/${id}` + '/tags', {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  addTagToFile(fileId: string, tagId: number) {
    return this.httpClient.put(`${this.baseURL}/${fileId}/tags/${tagId}`, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  // createTagAndAddToFile(fileId: string, tagName: string) {
  //   const headers = new HttpHeaders({
  //     Authorization: `Bearer ${
  //       JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
  //     }`,
  //   });
  //   return this.httpClient
  //     .post<Tag>(`${this.baseURL}/tag`, { name: tagName }, { headers: headers })
  //     .pipe(
  //       switchMap((tag) => {
  //         return this.httpClient.put(
  //           `${this.baseURL}/files/${fileId}/tags/${tag.id}`,
  //           {},
  //           { headers: headers }
  //         );
  //       })
  //     );
  // }
}
