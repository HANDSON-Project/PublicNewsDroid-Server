package com.example.demo.src.news;


import com.example.demo.src.news.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NewsDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // News 등록
    public int createNews(PostNewsReq postNewsReq) {
        String createNewsQuery = "insert into News (userIdx, title, context, image, location) VALUES (?,?,?,?,?)";
        Object[] createNewsParams = new Object[]{postNewsReq.getUserIdx(), postNewsReq.getTitle(), postNewsReq.getContext(),postNewsReq.getImage(),postNewsReq.getLocation()};
        this.jdbcTemplate.update(createNewsQuery, createNewsParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // News 정보 변경
    public int modifyNews(News news) {
        String modifyNewsQuery = "update News set title=?, context=?, image=?,location = ? where newsIdx = ? ";
        Object[] modifyNewsParams = new Object[]{news.getTitle(), news.getContext(), news.getImage(), news.getLocation(), news.getNewsIdx()};
        return this.jdbcTemplate.update(modifyNewsQuery, modifyNewsParams);
    }

    // 모든 News 정보 조회
    public List<GetNewsRes> getAllNews() {
        String getAllNewsQuery = "select * from News";
        return this.jdbcTemplate.query(getAllNewsQuery,
                (rs, rowNum) -> new GetNewsRes(
                        rs.getInt("userIdx"),
                        rs.getInt("newsIdx"),
                        rs.getString("title"),
                        rs.getString("context"),
                        rs.getString("image"),
                        rs.getString("location"),
                        rs.getInt("viewNum"),
                        rs.getTimestamp("createdAt"))
        );
    }

    // 해당 지역의 news 조회
    public List<GetNewsRes> getNewsesByLocation(String location) {
        String getNewsesByLocationQuery = "select * from News where location =?";
        String getNewsesByLocationParams = location;
        return this.jdbcTemplate.query(getNewsesByLocationQuery,
                (rs, rowNum) -> new GetNewsRes(
                        rs.getInt("userIdx"),
                        rs.getInt("newsIdx"),
                        rs.getString("title"),
                        rs.getString("context"),
                        rs.getString("image"),
                        rs.getString("location"),
                        rs.getInt("viewNum"),
                        rs.getTimestamp("createdAt")),
                getNewsesByLocationParams);
    }


    // 해당 newsIdx를 갖는 news 조회
    public GetNewsRes getNews(int newsIdx) {
        String getNewsQuery = "select * from News where newsIdx = ?";
        int getNewsParams = newsIdx;
        return this.jdbcTemplate.queryForObject(getNewsQuery,
                (rs, rowNum) -> new GetNewsRes(
                        rs.getInt("userIdx"),
                        rs.getInt("newsIdx"),
                        rs.getString("title"),
                        rs.getString("context"),
                        rs.getString("image"),
                        rs.getString("location"),
                        rs.getInt("viewNum"),
                        rs.getTimestamp("createdAt")),
                getNewsParams);
    }
}
