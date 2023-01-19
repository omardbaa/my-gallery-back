package com.mygallery.controllers;


import com.mygallery.dtos.TagFile;
import com.mygallery.enities.File;
import com.mygallery.enities.Tag;
import com.mygallery.repositories.FileRepository;
import com.mygallery.services.FileService;
import com.mygallery.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private final TagService service;
    @Autowired
    private final FileService fileService;
    @Autowired
    private final FileRepository fileRepository;


    public TagController(TagService service, FileService fileService, FileRepository fileRepository) {
        this.service = service;
        this.fileService = fileService;
        this.fileRepository = fileRepository;

    }

    //Create new tag
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Tag save(@RequestBody Tag tag) {
        service.save(tag);
        return tag;
    }


    // Update Tag
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Tag> update(@PathVariable Long id, @RequestBody Tag tag) {
        Tag newTag = service.findById(id);
        newTag.setTagName(tag.getTagName());
        service.save(newTag);
        return new ResponseEntity<>(newTag, HttpStatus.OK);
    }


    //	Get All tags

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Tag>> getAll() {
        List<Tag> tags = service.getAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }


    //get all files of tag
    @GetMapping("/{id}/files")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<File> getAllTagsOfFile(@PathVariable("id") Long id) {
        return this.fileService.getAllFilesOfTag(id);

    }


    //	 Get Tag by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Tag> findById(@PathVariable("id") Long id) {
        Tag tag = service.findById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);

    }


    //Delete tag byId
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    //Link tag with file By Id
    @PostMapping("/tagToFile")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Collection<Tag> AddTagToFile(@RequestBody TagFile tagFile) {
        File file = fileService.FindFileById(tagFile.getFileId());
        Tag tag = service.findById(tagFile.getTagId());
        Set<Tag> tags = file.getTags();
        tags.add(tag);
        file.setTags(tags);
        fileRepository.save(file);
        return file.getTags();
    }

}
