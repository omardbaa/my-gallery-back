package com.mygallery.enities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@JsonIdentityInfo(scope = User.class, generator = ObjectIdGenerators.PropertyGenerator.class,

        property = "id")

@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)


    private Long id;


    private String fullName;


    @NotBlank
    @Size(max = 20)
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new HashSet<>();


    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /*public void save(Role role) {
        this.roles.add(role);
    }*/
}
