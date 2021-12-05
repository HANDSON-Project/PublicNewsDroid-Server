package com.example.demo.src.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.news.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/news")
public class NewsController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final NewsProvider newsProvider;
    @Autowired
    private final NewsService newsService;
    @Autowired
    private final JwtService jwtService;

    public NewsController(NewsProvider newsProvider, NewsService newsService, JwtService jwtService) {
        this.newsProvider = newsProvider;
        this.newsService = newsService;
        this.jwtService = jwtService;
    }
    
}
