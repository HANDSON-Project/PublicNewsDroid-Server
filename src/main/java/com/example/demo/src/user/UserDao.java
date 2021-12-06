package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {
  private JdbcTemplate jdbcTemplate;
  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  // 회원가입
  public int createUser(PostUserReq postUserReq) {
    String createUserQuery = "insert into User (email, password, nickname,location) VALUES (?,?,?,?)";
    Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getNickname(),postUserReq.getLocation()};
    this.jdbcTemplate.update(createUserQuery, createUserParams);

    String lastInserIdQuery = "select last_insert_id()";
    return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
  }

  // 이메일 확인
  public int checkEmail(String email) {
    String checkEmailQuery = "select exists(select email from User where email = ?)";
    String checkEmailParams = email;
    return this.jdbcTemplate.queryForObject(checkEmailQuery,
      int.class,
      checkEmailParams);
  }

  // 회원정보 변경(위치 변경)
  public int modifyUserName(PatchUserReq patchUserReq) {
    String modifyUserNameQuery = "update User set location = ? where userIdx = ? ";
    Object[] modifyUserNameParams = new Object[]{patchUserReq.getLocation(), patchUserReq.getUserIdx()};
    return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
  }

  // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
  public User getPwd(PostLoginReq postLoginReq) {
    String getPwdQuery = "select userIdx, password,email,nickname,location from User where email = ?";
    String getPwdParams = postLoginReq.getEmail();

    return this.jdbcTemplate.queryForObject(getPwdQuery,
      (rs, rowNum) -> new User(
        rs.getInt("userIdx"),
        rs.getString("email"),
        rs.getString("password"),
        rs.getString("nickname"),
        rs.getString("location")
      ),
      getPwdParams
    );
  }

  // 해당 지역에 속한 유저들의 정보 조회 API
  public List<GetUserRes> getUsersByNickname(String location) {
    String getUsersByNicknameQuery = "select * from User where location =?";
    String getUsersByNicknameParams = location;
    return this.jdbcTemplate.query(getUsersByNicknameQuery,
      (rs, rowNum) -> new GetUserRes(
        rs.getInt("userIdx"),
        rs.getString("nickname"),
        rs.getString("Email"),
        rs.getString("password"),
        rs.getString("location")),
      getUsersByNicknameParams);
  }



  // (Admin용)User 테이블에 존재하는 전체 유저들의 정보 조회
  public List<GetUserRes> getUsers() {
    String getUsersQuery = "select * from User";
    return this.jdbcTemplate.query(getUsersQuery,
      (rs, rowNum) -> new GetUserRes(
        rs.getInt("userIdx"),
        rs.getString("nickname"),
        rs.getString("Email"),
        rs.getString("password"),
        rs.getString("location"))
    );
  }

  // 해당 userIdx를 갖는 유저조회
  public GetUserRes getUser(int userIdx) {
    String getUserQuery = "select * from User where userIdx = ?";
    int getUserParams = userIdx;
    return this.jdbcTemplate.queryForObject(getUserQuery,
      (rs, rowNum) -> new GetUserRes(
        rs.getInt("userIdx"),
        rs.getString("nickname"),
        rs.getString("Email"),
        rs.getString("password"),
        rs.getString("location")),
      getUserParams);
  }
}
