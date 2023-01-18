package com.mygallery.repositories;

import com.mygallery.dtos.FileTag;
import com.mygallery.enities.File;
import com.mygallery.enities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileTagRepository extends JpaRepository<FileTag, Long> {


    FileTag findByFileAndTag(File file, Tag tag);

    List<FileTag> findByTag(Tag tag);

    List<FileTag> findByFile(File file);
}