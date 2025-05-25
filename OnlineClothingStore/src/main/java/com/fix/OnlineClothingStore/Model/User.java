package com.fix.OnlineClothingStore.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String roles = "USER";

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}
