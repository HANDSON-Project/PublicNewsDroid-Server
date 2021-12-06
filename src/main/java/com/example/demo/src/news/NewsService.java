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
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class NewsService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NewsDao newsDao;
    private final NewsProvider newsProvider;
    private final JwtService jwtService;

    @Autowired
    public NewsService(NewsDao newsDao, NewsProvider newsProvider, JwtService jwtService) {
        this.newsDao = newsDao;
        this.newsProvider = newsProvider;
        this.jwtService = jwtService;
    }

    // 뉴스 생성
    public PostNewsRes createNews(PostNewsReq postNewsReq) throws BaseException {
        try {
            int newsIdx = newsDao.createNews(postNewsReq);
            return new PostNewsRes(newsIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 뉴정보 수정(Patch)
    public void modifyNews(News news) throws BaseException {
        try {
            int result = newsDao.modifyNews(news);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
