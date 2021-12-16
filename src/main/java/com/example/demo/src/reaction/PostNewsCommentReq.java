package com.example.demo.src.reaction;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostNewsCommentReq {
    private int newsIdx;
    private int userIdx;
    private String content;
}
