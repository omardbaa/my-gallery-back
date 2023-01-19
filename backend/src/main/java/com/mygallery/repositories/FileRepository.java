package com.mygallery.repositories;

import com.mygallery.enities.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

    @Query(value = "SELECT name FROM Files f WHERE f.id=?", nativeQuery = true)
    String getName(String id);

    @Query(value = "SELECT type FROM Files f WHERE f.id LIKE %?%", nativeQuery = true)
    String getType(String id);

    File findByName(String fileName);

    File findFileById(String Id);


    //Remove tag from file
    @Modifying
    @Transactional
    @Query(value = " DELETE FROM file_tag\n" + "    WHERE file_id LIKE %?% AND tag_id=?;\n", nativeQuery = true)
    void deleteTag(String fileId, Long tagId);


    @Query(value = "SELECT * FROM files WHERE name LIKE %?1%" + " OR extension LIKE %?1%" + " OR type LIKE %?1%" + " OR CONCAT(size, '') LIKE %?1%", nativeQuery = true)
    List<File> search(String keyword);


    @Query(value = "SELECT * FROM files WHERE name LIKE %?1%" + " OR extension LIKE %?1%" + " OR type LIKE %?1%" + " OR CONCAT(size, '') LIKE %?1% ", nativeQuery = true)
    Page<File> findAll(Pageable pageable, String keyword);


}
