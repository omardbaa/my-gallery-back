package com.mygallery.dtos;

import lombok.Data;

@Data
public class TagDto {

    private Long id;
    private String tagName;

    private FileDto File;

}
