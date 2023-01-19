import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SafePipe } from './compenents/file-details/Safe.pipe';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FileDetailsComponent } from './compenents/file-details/file-details.component';
import { FileListsComponent } from './compenents/file-lists/file-lists.component';
import { HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { UpdateFolderComponent } from './compenents/update-folder/update-folder.component';
import { CommonModule } from '@angular/common';
import { FolderDetailsComponent } from './compenents/folder-details/folder-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { LoginCompenentComponent } from './compenents/login-compenent/login-compenent.component';
import { RegisterCompenentComponent } from './compenents/register-compenent/register-compenent.component';
import { SearchComponent } from './compenents/search/search.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ProfileComponent } from './compenents/profile/profile.component';
import { HttpRequestInterceptor } from './_helpers/http.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { FolderListComponent } from './compenents/folder-list/folder-list.component';
@NgModule({
  declarations: [
    AppComponent,
    FileDetailsComponent,
    FileListsComponent,
    FolderListComponent,
    UpdateFolderComponent,
    FolderDetailsComponent,
    LoginCompenentComponent,
    RegisterCompenentComponent,
    SafePipe,
    SearchComponent,
    ProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxPaginationModule,
    Ng2SearchPipeModule,
    ReactiveFormsModule,
    CommonModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatTableModule,
    MatCardModule,
    MatProgressSpinnerModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
