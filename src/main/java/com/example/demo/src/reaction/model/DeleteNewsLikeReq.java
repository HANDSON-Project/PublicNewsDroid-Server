package com.example.demo.src.reaction.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteNewsLikeReq {
    private int userIdx;
    private int likeIdx;
}
