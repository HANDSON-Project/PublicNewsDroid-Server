package com.example.demo.src.reaction;


import com.example.demo.src.reaction.model.DeleteNewsCommentReq;
import com.example.demo.src.reaction.model.GetNewsCommentRes;
import com.example.demo.src.reaction.model.PostNewsCommentReq;
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

    // 뉴스 댓글 생성
    public void createNewsCommment(PostNewsCommentReq postNewsCommentReq) {
    }

    // 뉴스 댓글 조회
    // 해당 지역에 속한 유저들의 정보 조회 API
    public List<GetNewsCommentRes> getCommments(int newsIdx) {

        return null;
    }

    // 뉴스 댓글 삭제
    public void deleteNewsCommment(DeleteNewsCommentReq deleteNewsCommentReq) {
    }
}
