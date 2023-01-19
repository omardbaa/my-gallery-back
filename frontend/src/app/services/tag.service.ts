import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { FolderModule } from '../modules/folder/folder.module';
import { FileFolder } from '../modules/fileFolder/FileFolder';
import { BASE_URL } from '../Constants';
import { FileModule } from '../modules/file/file.module';
import { Tag } from '../modules/Tag/tag';
import { USER_KEY } from './storage.service';

@Injectable({
  providedIn: 'root',
})
export class TagService {
  private baseURL = `${BASE_URL}/tag`;

  constructor(private httpClient: HttpClient) {}

  getTagList(): Observable<Tag[]> {
    return this.httpClient.get<Tag[]>(`${this.baseURL}`, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  createTag(tag: Tag): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, tag, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  getTagById(id: number): Observable<Tag> {
    return this.httpClient.get<Tag>(`${this.baseURL}/${id}`, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  updateTag(id: number, tag: Tag): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, tag, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  deleteTag(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }

  getAllFiles(id: number): Observable<Tag> {
    return this.httpClient.get<Tag>(`${this.baseURL}/${id}/files`, {
      headers: {
        Authorization: `Bearer ${
          JSON.parse(window.localStorage.getItem(USER_KEY) ?? '{}')?.token
        }`,
      },
    });
  }
}
