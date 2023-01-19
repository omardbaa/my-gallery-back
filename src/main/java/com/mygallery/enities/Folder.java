package com.mygallery.enities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "folders")

@JsonIdentityInfo(scope = Folder.class, generator = ObjectIdGenerators.PropertyGenerator.class,

        property = "folderId")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long folderId;

    @Column(unique = true, nullable = false)
    private String folderName;

    @ManyToMany(mappedBy = "folder", targetEntity = File.class, fetch = FetchType.LAZY)
//@JsonIgnore
    private Collection<File> files;


}
