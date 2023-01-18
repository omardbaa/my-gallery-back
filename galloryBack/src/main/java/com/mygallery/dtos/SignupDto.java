package com.mygallery.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Set;


@Getter
@Setter
public class SignupDto {


    private String fullName;


    @Column(unique = true, nullable = false)
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Column(unique = true, nullable = false)
    private String email;


    private Set<String> role;


}
