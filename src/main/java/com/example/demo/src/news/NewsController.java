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

    // news 생성
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostNewsRes> createNews(@PathVariable("userIdx") int userIdx, @RequestBody PostNewsReq postNewsReq) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (postNewsReq.getTitle() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if (postNewsReq.getContext() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            try {
                PostNewsRes postNewsRes = newsService.createNews(postNewsReq);
                return new BaseResponse<>(postNewsRes);
            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    // 모든 news들의  조회
    // 특정 지역의 news들 조회
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetNewsRes>> getNewses(@RequestParam(required = false) String location) {
        try {
            if (location == null) {
                List<GetNewsRes> getNewsRes = newsProvider.getAllNews();
                return new BaseResponse<>(getNewsRes);
            }
            List<GetNewsRes> getNewsRes = newsProvider.getNewsesByLocation(location);
            return new BaseResponse<>(getNewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // news 하나 조회
    @ResponseBody
    @GetMapping("/{newsIdx}")
    public BaseResponse<GetNewsRes> getNews(@PathVariable("newsIdx") int newsIdx) {
        try {
            GetNewsRes getNewsRes = newsProvider.getNews(newsIdx);
            return new BaseResponse<>(getNewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // news 정보 변경
    @ResponseBody
    @PatchMapping("/{userIdx}/{newsIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @PathVariable("newsIdx") int newsIdx, @RequestBody PatchNewsReq patchUserReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            News news = new News(userIdx, newsIdx,patchUserReq.getTitle(),patchUserReq.getContext(), patchUserReq.getImage(), patchUserReq.getLocation());
            newsService.modifyNews(news);
            String result = "뉴스정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
