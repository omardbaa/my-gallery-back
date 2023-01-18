package com.mygallery.services;


import com.mygallery.enities.File;
import com.mygallery.enities.Tag;
import com.mygallery.repositories.FileRepository;
import com.mygallery.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private final TagRepository tagRepository;

    @Autowired
    private final FileRepository fileRepository;

    public TagService(TagRepository tagRepository, FileRepository fileRepository) {
        this.tagRepository = tagRepository;
        this.fileRepository = fileRepository;
    }


    public void save(Tag tag) {

        tagRepository.save(tag);
    }


    public void update(Tag tag) {
        tagRepository.save(tag);
    }


    public void delete(Long id) {
        tagRepository.deleteById(id);
    }


    public Tag findById(Long id) {
        return tagRepository.findById(id).get();
    }


    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag findByName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }


    public List<Tag> getFilesOfTag(String id) {

        File file = this.fileRepository.findFileById(id);

        List<Tag> tags = (List<Tag>) file.getTags();
        return tags;
    }
}
