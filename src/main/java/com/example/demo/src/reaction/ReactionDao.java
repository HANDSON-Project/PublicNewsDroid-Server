package com.example.demo.src.reaction;


import com.example.demo.src.reaction.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReactionDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 뉴스 좋아요 생성
    public void createNewsLike(PostNewsLikeReq postNewsLikeReq) {
        String createNewsLikeQuery = "insert into NewsLike (userIdx, newsIdx) VALUES (?,?)";
        Object[] createNewsLikeParams = new Object[]{postNewsLikeReq.getUserIdx(),postNewsLikeReq.getNewsIdx()};
        this.jdbcTemplate.update(createNewsLikeQuery, createNewsLikeParams);
    }

    // 뉴스 좋아요 취소
    public void deleteNewsLike(DeleteNewsLikeReq deleteNewsLikeReq) {
        String deleteNewsLikeQuery = "delete from NewsLike where likeIdx = ?";
        Object[] deleteNewsLikeParams = new Object[]{deleteNewsLikeReq.getLikeIdx()};
        this.jdbcTemplate.update(deleteNewsLikeQuery, deleteNewsLikeParams);
    }

    // 뉴스 댓글 생성
    public void createNewsComment(PostNewsCommentReq postNewsCommentReq) {
        String createUserQuery = "insert into NewsComment (userIdx, newsIdx, context) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postNewsCommentReq.getUserIdx(), postNewsCommentReq.getNewsIdx(), postNewsCommentReq.getContent()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

    // 뉴스 댓글 조회
    // 해당 지역에 속한 유저들의 정보 조회 API
    public List<GetNewsCommentRes> getComments(int newsIdx) {
        String getCommentsQuery = "select N.commentIdx, U.userIdx, U.nickname, U.email,N.context, N.createdAt\n" +
                "from NewsComment N\n" +
                "join User U on U.userIdx = N.userIdx\n" +
                "where newsIdx =?";
        int getCommentsParams = newsIdx;
        return this.jdbcTemplate.query(getCommentsQuery,
                (rs, rowNum) -> new GetNewsCommentRes(
                        rs.getInt("commentIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("context"),
                        rs.getTimestamp("createdAt")),
                getCommentsParams);
    }

    // 뉴스 댓글 삭제
    public void deleteNewsComment(DeleteNewsCommentReq deleteNewsCommentReq) {
        String deleteNewsCommentQuery = "delete from NewsComment where commeentIdx = ?";
        Object[] deleteNewsCommentParams = new Object[]{deleteNewsCommentReq.getCommentIdx()};
        this.jdbcTemplate.update(deleteNewsCommentQuery, deleteNewsCommentParams);
    }

    // 뉴스 신고
    public void createNewsReport(PostNewsReportReq postNewsReportReq) {
        String createNewsReportQuery = "insert into NewsReport (userIdx, newsIdx) VALUES (?,?)";
        Object[] createNewsReportParams = new Object[]{postNewsReportReq.getUserIdx(), postNewsReportReq.getNewsIdx()};
        this.jdbcTemplate.update(createNewsReportQuery, createNewsReportParams);
    }
}
