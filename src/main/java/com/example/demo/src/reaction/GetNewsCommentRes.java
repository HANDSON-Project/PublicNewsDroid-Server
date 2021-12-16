package com.example.demo.src.reaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetNewsCommentRes {
    private int commentIdx;
    private int userIdx;
    private String nickname;
    private String email;
    private String context;
    private Timestamp createdAt;
}
