package com.mygallery.dtos;

import lombok.Data;

@Data
public class TagFile {

    private String tageName;
    private String fileName;

    private long tagId;
    private String fileId;


}
