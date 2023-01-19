import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileModule } from 'src/app/modules/file/file.module';
import { FileService } from 'src/app/services/file.service';
import { BASE_URL } from 'src/app/Constants';
import { Tag } from 'src/app/modules/Tag/tag';

@Component({
  selector: 'app-file-details',
  templateUrl: './file-details.component.html',
  styleUrls: ['./file-details.component.css'],
})
export class FileDetailsComponent implements OnInit {
  tags: any = [];

  iconHover = false;
  tagName!: string;
  constructor(
    private fileService: FileService,
    private route: ActivatedRoute
  ) {}
  fileId!: string;
  tagId!: number;
  files: FileModule[] = [];

  id!: string;
  file: FileModule = new FileModule();
  tag: Tag = new Tag();

  fileUrl!: string;
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.file = new FileModule();
    this.fileService.getFileById(this.id).subscribe((data) => {
      this.file = data;
      console.log(data);
    });
    this.getTags();
  }

  private getTags() {
    this.fileService.getTags(this.id).subscribe((data) => {
      let allTags = [];
      let datalist: any = data;
      for (let a of datalist) {
        if (a.id) {
          allTags.push(a);
        }
      }
      this.tags = allTags;
    });
  }
  readFile(id: string, extension: string) {
    this.fileService.renderFile(id, extension).subscribe();
  }

  filesOfTag(tagId: string) {
    this.tagId;
  }
  deleteTag(fileId: string, tagId: number) {
    if (window.confirm('Are you sure you want to delete this tag?')) {
      this.fileService.deleteTag(fileId, tagId).subscribe((data) => {
        console.log(data);
        this.getTags();
      });
    }
  }

  addTagToFile() {
    this.fileService.addTagToFile(this.fileId, this.tagId).subscribe(
      () => {
        // handle success response
        console.log('Tag added to file successfully');
      },
      (error) => {
        // handle error response
        console.error('Error adding tag to file', error);
      }
    );
  }
}
