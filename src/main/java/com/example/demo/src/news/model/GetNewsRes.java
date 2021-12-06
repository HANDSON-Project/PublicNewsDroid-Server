package com.example.demo.src.news.model;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetNewsRes {
    private int userIdx;
    private int newsIdx;
    private String title;
    private String context;
    private String image;
    private String location;
    private int viewNum;
    private Timestamp createdAt;
}
