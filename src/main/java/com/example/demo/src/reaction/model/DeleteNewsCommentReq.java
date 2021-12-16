package com.example.demo.src.reaction.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteNewsCommentReq {
    private int userIdx;
    private int commentIdx;
}
