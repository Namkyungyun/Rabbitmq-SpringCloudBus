package com.example.repositoryservice.r2dbc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("USER")
public class User {

    @Id
    private Long id;
    private String name;

    private User(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public static User updateUser(Long id, String name) {
        return new User(id,name);
    }


}