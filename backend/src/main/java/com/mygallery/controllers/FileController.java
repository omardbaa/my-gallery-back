package com.mygallery.controllers;


import com.mygallery.dtos.FileDto;
import com.mygallery.enities.File;
import com.mygallery.enities.FileResponse;
import com.mygallery.enities.PaginationConsts;
import com.mygallery.enities.Tag;
import com.mygallery.repositories.FileRepository;
import com.mygallery.response.ResponseMessage;
import com.mygallery.services.FileService;
import com.mygallery.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
@RestController

@RequestMapping("/api/v1/file/")

public class FileController {

    private final Path rootPath = Paths.get("uploads");
    @Autowired
    private final FileService fileService;
    @Autowired
    private final FileRepository fileRepository;
    @Autowired
    TagService tagService;

    public FileController(FileService fileService, FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    //Create new tag
    @PostMapping("/tag")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Tag save(@RequestBody Tag tag) {
        tagService.save(tag);
        return tag;
    }


    //Upload file
    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public File uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.Upload(file);
    }


    //Display file content
    @GetMapping("/display/{filename:.+}")
    // @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.getFile(filename);
        FileDto fileDto = new FileDto();
        String nameoffile = file.getFilename();
        String[] id = nameoffile.split("\\.");
        String[] types = fileRepository.getType(id[0]).split("/");
        MediaType contentType = new MediaType(types[0], types[1]);
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileDto.getDisplayName()).body(file);
    }

    //Download file

    @GetMapping("/download/{filename:.+}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        Resource file = fileService.getFile(filename);
        FileDto fileDto = new FileDto();
       /* String nameoffile = file.getFilename();
        String[] id = nameoffile.split("\\.");
        String[] types = fileRepository.getType(id[0]).split("/");
        MediaType contentType = new MediaType(types[0], types[1]);*/
        return ResponseEntity.ok()/*.contentType(contentType)*/.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getDisplayName()).body(file);
    }

    //Get all files
    @GetMapping("/files")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public FileResponse getAllFiles(@RequestParam(value = "pageNo", defaultValue = PaginationConsts.DEFAULT_PAGE_NUMBER, required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = PaginationConsts.DEFAULT_PAGE_SIZE, required = false) int pageSize, @RequestParam(value = "sortBy", defaultValue = PaginationConsts.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = PaginationConsts.DEFAULT_SORT_DIRECTION, required = false) String sortDir, @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword) {

        return fileService.getAllFiles(pageNo, pageSize, sortBy, sortDir, keyword);
    }

    //find file by id
    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<File> findById(@PathVariable("id") String id) {
        return fileService.getfilebyId(id);
    }


    //Delete file by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String id) {
        String message = "";

        try {
            fileService.delete(id);
            message = "Delete the file successfully: " + id;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete the file: " + id + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
        }
    }


    //Add tag to file
    @PutMapping("/{fileId}/tags/{tagId}")
    public void addTagToFile(@PathVariable String fileId, @PathVariable Long tagId) {
        fileService.addTagToFile(fileId, tagId);
    }

    // get tags of file

    @RequestMapping(value = "/{fileId}/tags", method = RequestMethod.GET)
    public List<Tag> getTagsByFile(@PathVariable String fileId) {
        return fileService.getTagsByFile(fileId);

    }


    //Remove tag from file by id
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/deleteTag/{fileId}/tags/{tagId}")
    public ResponseEntity<Map<String, Boolean>> removeTagFromFile(@PathVariable String fileId, @PathVariable Long tagId) {
        fileService.removeTagFromFile(fileId, tagId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }




  /*  @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            fileService.store(file);

            // Save the URL to the database
            // ...
            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (StorageException e) {
            return new ResponseEntity<>("Error uploading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


}
