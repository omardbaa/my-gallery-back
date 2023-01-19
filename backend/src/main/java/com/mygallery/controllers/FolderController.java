package com.mygallery.controllers;


import com.mygallery.dtos.FileFolder;
import com.mygallery.enities.File;
import com.mygallery.enities.Folder;
import com.mygallery.repositories.FileRepository;
import com.mygallery.services.FileService;
import com.mygallery.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/folder")
public class FolderController {
    @Autowired
    private final FolderService service;
    @Autowired
    private final FileService fileService;
    @Autowired
    private final FileRepository fileRepository;


    public FolderController(FolderService service, FileService fileService, FileRepository fileRepository) {
        this.service = service;
        this.fileService = fileService;
        this.fileRepository = fileRepository;

    }

    //Create new folder
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Folder save(@RequestBody Folder folder) {
        service.save(folder);
        return folder;
    }


    // Update Folder
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Folder> update(@PathVariable Long id, @RequestBody Folder folder) {
        Folder newFolder = service.findById(id);
        newFolder.setFolderName(folder.getFolderName());
        service.save(newFolder);
        return new ResponseEntity<>(newFolder, HttpStatus.OK);
    }


    //	Get All Folders
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Folder>> getAll() {
        List<Folder> folders = service.getAll();
        return new ResponseEntity<>(folders, HttpStatus.OK);
    }


    //Get all files of folder
    @GetMapping("/{id}/files")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<File> getAllFilesOfFolder(@PathVariable("id") Long folderId) {
        return this.fileService.getAllFilesOfFolder(folderId);
    }


    //	 Get Folder by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Folder> findById(@PathVariable("id") Long id) {
        Folder folder = service.findById(id);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }


    //Delete folder
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    //remove file from folder
    @DeleteMapping("/deleteFile/{fileId}/{folderId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> deleteFile(@PathVariable("fileId") String fileId, @PathVariable("folderId") Long folderId) {
        service.deleteFile(fileId, folderId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


/*
    @PostMapping("/addFile")
    public ResponseEntity<Map<String, Boolean>> addFile(@RequestBody String fileId, Long folderId) {
        service.addFile(fileId,folderId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("added", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }*/


    //need to fixing
    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public File uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.Upload(file);
    }


    // Link File  To Folder By Name
    @PostMapping("/fileFoldername")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Collection<Folder> AsignFileToFolder(@RequestBody FileFolder fileFolder) {
        File file = fileService.loadFileByName(fileFolder.getFileName());
        Folder folder = service.findByName(fileFolder.getFolderName());
        Collection<Folder> folders = file.getFolder();
        folders.add(folder);
        file.setFolder(folders);
        fileRepository.save(file);
        return file.getFolder();
    }


    //Link file to folder By Id
    @PostMapping("/fileToFolder")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Collection<Folder> AddFileFolder(@RequestBody FileFolder fileFolder) {
        File file = fileService.FindFileById(fileFolder.getFileId());
        Folder folder = service.findFolderById(fileFolder.getFolderId());
        Collection<Folder> folders = file.getFolder();
        folders.add(folder);
        file.setFolder(folders);
        fileRepository.save(file);
        return file.getFolder();
    }


}
