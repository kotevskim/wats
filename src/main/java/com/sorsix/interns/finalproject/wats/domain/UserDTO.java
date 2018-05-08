package com.sorsix.interns.finalproject.wats.domain;

public class UserDTO {
    private Long id;
    private String name;
    private String username;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
