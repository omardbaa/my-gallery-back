package com.mygallery.services;


import com.mygallery.enities.Folder;
import com.mygallery.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    @Autowired
    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }


    public void save(Folder folder) {

        folderRepository.save(folder);
    }


    public void update(Folder folder) {
        folderRepository.save(folder);
    }


    public void delete(Long id) {
        folderRepository.deleteById(id);
    }


    public void deleteFile(String fileId, Long folderId) {

        folderRepository.deleteFile(fileId, folderId);
    }


    /*public void addFile (String fileId, Long folderId){

        folderRepository.addFile(fileId,folderId);
    }*/

    public Folder findById(Long id) {
        return folderRepository.findById(id).get();
    }


    public List<Folder> getAll() {
        return folderRepository.findAll();
    }

    public Folder findByName(String folderName) {
        return folderRepository.findByFolderName(folderName);
    }


    public Folder findFolderById(Long folderId) {
        return folderRepository.findByFolderId(folderId);
    }


}
