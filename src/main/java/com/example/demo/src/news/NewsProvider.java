package com.example.demo.src.news;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.news.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class NewsProvider {


    private final NewsDao newsDao;
    private final JwtService jwtService;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public NewsProvider(NewsDao newsDao, JwtService jwtService) {
        this.newsDao = newsDao;
        this.jwtService = jwtService;
    }
    
}
