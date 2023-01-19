package com.mygallery.dtos;

import lombok.Data;

@Data
public class FolderDto {

    private Long id;
    private String folderName;

    private FileDto File;

}
