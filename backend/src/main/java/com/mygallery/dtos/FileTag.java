package com.mygallery.dtos;

import com.mygallery.enities.File;
import com.mygallery.enities.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class FileTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private File file;

    @ManyToOne
    private Tag tag;

}