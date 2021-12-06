package com.example.demo.src.news.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchNewsReq {
    private int userIdx;
    private String title;
    private String context;
    private String image;
    private String location;
}
