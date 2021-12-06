package com.example.demo.src.news.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class News {
    private int userIdx;
    private int newsIdx;
    private String title;
    private String context;
    private String image;
    private String location;
}
