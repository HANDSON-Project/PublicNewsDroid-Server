package com.example.demo.src.reaction;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.reaction.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReactionService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReactionDao reactionDao;
    private final ReactionProvider reactionProvider;
    private final JwtService jwtService;

    @Autowired
    public ReactionService(ReactionDao reactionDao, ReactionProvider reactionProvider, JwtService jwtService) {
        this.reactionDao = reactionDao;
        this.reactionProvider = reactionProvider;
        this.jwtService = jwtService;
    }

    // 뉴스 좋아요 생성
    public void createNewsLike(PostNewsLikeReq postNewsLikeReq) throws BaseException {
        try {
            reactionDao.createNewsLike(postNewsLikeReq);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 뉴스 좋아요 취소
    public void deleteNewsLike(DeleteNewsLikeReq deleteNewsLikeReq) throws BaseException {
        try {
            reactionDao.deleteNewsLike(deleteNewsLikeReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 뉴스 댓글 생성
    public void createNewsComment(PostNewsCommentReq postNewsCommentReq) throws BaseException {
        try {
            reactionDao.createNewsCommment(postNewsCommentReq);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 뉴스 댓글 삭제
    public void deleteNewsCommment(DeleteNewsCommentReq deleteNewsCommentReq) throws BaseException {
        try {
            reactionDao.deleteNewsCommment(deleteNewsCommentReq);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 뉴스 신고
    public void createNewsReport(PostNewsReportReq postNewsReportReq) throws BaseException {
        try {
            reactionDao.createNewsReport(postNewsReportReq);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
