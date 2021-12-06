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

    // News 정보를 조회
    public List<GetNewsRes> getAllNews() throws BaseException {
        try {
            List<GetNewsRes> getNewsRes = newsDao.getAllNews();
            return getNewsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 지역에 속한 News들의 정보 조회
    public List<GetNewsRes> getNewsesByLocation(String location) throws BaseException {
        try {
            List<GetNewsRes> getNewsesRes = newsDao.getNewsesByLocation(location);
            return getNewsesRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 해당 newsIdx를 갖는 News의 정보 조회
    public GetNewsRes getNews(int newsIdx) throws BaseException {
        try {
            GetNewsRes getNewsRes = newsDao.getNews(newsIdx);
            return getNewsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
