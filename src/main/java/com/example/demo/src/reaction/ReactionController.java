package com.example.demo.src.reaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.reaction.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/news")
public class ReactionController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final ReactionProvider reactionProvider;
    @Autowired
    private final ReactionService reactionService;
    @Autowired
    private final JwtService jwtService;

    public ReactionController(ReactionProvider reactionProvider, ReactionService reactionService, JwtService jwtService) {
        this.reactionProvider = reactionProvider;
        this.reactionService = reactionService;
        this.jwtService = jwtService;
    }

    // 뉴스 좋아요
    @ResponseBody
    @PostMapping("/likes")
    public BaseResponse<String> createNewsLike(@RequestBody PostNewsLikeReq postNewsLikeReq) {
        // PostNewsLikeReq
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(postNewsLikeReq.getUserIdx()!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            try {
                reactionService.createNewsLike(postNewsLikeReq);
                String result = "뉴스 좋아요 성공했습니다.";
                return new BaseResponse<>(result);
            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 뉴스 좋아요 취소
    @ResponseBody
    @DeleteMapping("/likes")
    public BaseResponse<String> deleteNewsLike(@RequestBody DeleteNewsLikeReq deleteNewsLikeReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(deleteNewsLikeReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            reactionService.deleteNewsLike(deleteNewsLikeReq);
            String result = "좋아요가 취소되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 뉴스 댓글 생성
    @ResponseBody
    @PostMapping("/comments")
    public BaseResponse<String> createNewsComment(@RequestBody PostNewsCommentReq postNewsCommentReq) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(postNewsCommentReq.getUserIdx()!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (postNewsCommentReq.getContent() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            try {
                reactionService.createNewsComment(postNewsCommentReq);
                String result = "댓글이 생성되었습니다.";
                return new BaseResponse<>(result);

            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 뉴스 댓글 조회
    @ResponseBody
    @GetMapping("/comments/{newsIdx}")
    public BaseResponse<List<GetNewsCommentRes>> getNewsComment(@PathVariable("newsIdx") int newsIdx) {
        try {
            List<GetNewsCommentRes> getNewCommentRes = reactionProvider.getNewsComment(newsIdx);
            return new BaseResponse<>(getNewCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 뉴스 댓글 삭제
    @ResponseBody
    @DeleteMapping("/comments")
    public BaseResponse<String> deleteNewsComment(@RequestBody DeleteNewsCommentReq deleteNewsCommentReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(deleteNewsCommentReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            reactionService.deleteNewsComment(deleteNewsCommentReq);
            String result = "댓글을 삭제했습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 뉴스 신고
    @ResponseBody
    @PostMapping("/report")
    public BaseResponse<String> createNewsReport(@RequestBody PostNewsReportReq postNewsReportReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(postNewsReportReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            try {
                reactionService.createNewsReport(postNewsReportReq);
                String result = "뉴스를 신고했습니다.";
                return new BaseResponse<>(result);
            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
