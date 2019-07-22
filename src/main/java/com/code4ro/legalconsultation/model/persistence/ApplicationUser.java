package com.code4ro.legalconsultation.model.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "applicationusers")
public class ApplicationUser extends BaseEntity {

    @NotBlank
    @Size(max = 40)
    @Column(unique = true)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @MapsId
    private User user;

    public ApplicationUser() {

    }

    public ApplicationUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
