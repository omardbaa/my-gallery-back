package com.mygallery.enities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Data
@Getter
@Setter
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
