package com.example.demo.src.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String email;
    private String password;
    private String nickname;
    private String location;
}
