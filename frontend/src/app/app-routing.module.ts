import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FileDetailsComponent } from './compenents/file-details/file-details.component';
import { FileListsComponent } from './compenents/file-lists/file-lists.component';
import { FolderDetailsComponent } from './compenents/folder-details/folder-details.component';
import { FolderListComponent } from './compenents/folder-list/folder-list.component';
import { LoginCompenentComponent } from './compenents/login-compenent/login-compenent.component';
import { ProfileComponent } from './compenents/profile/profile.component';
import { RegisterCompenentComponent } from './compenents/register-compenent/register-compenent.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginCompenentComponent },
  { path: 'register', component: RegisterCompenentComponent },
  { path: 'files', component: FileListsComponent },
  { path: 'folders', component: FolderListComponent },
  { path: 'profile', component: ProfileComponent },

  { path: 'folder-details/:id', component: FolderDetailsComponent },
  { path: 'file-details/:id', component: FileDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
