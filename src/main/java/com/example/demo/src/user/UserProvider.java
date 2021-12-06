package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserProvider {


  private final UserDao userDao;
  private final JwtService jwtService;
  final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public UserProvider(UserDao userDao, JwtService jwtService) {
    this.userDao = userDao;
    this.jwtService = jwtService;
  }


  // 로그인(password 검사)
  public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
    User user = userDao.getPwd(postLoginReq);
    String password;
    try {
      password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
    } catch (Exception ignored) {
      throw new BaseException(PASSWORD_DECRYPTION_ERROR);
    }

    if (postLoginReq.getPassword().equals(password)) {
      int userIdx = userDao.getPwd(postLoginReq).getUserIdx();
      String jwt = jwtService.createJwt(userIdx);
      return new PostLoginRes(userIdx,jwt);
    } else {
      throw new BaseException(FAILED_TO_LOGIN);
    }
  }

  // 해당 이메일이 이미 User Table에 존재하는지 확인
  public int checkEmail(String email) throws BaseException {
    try {
      return userDao.checkEmail(email);
    } catch (Exception exception) {
      throw new BaseException(DATABASE_ERROR);
    }
  }


  // User들의 정보를 조회
  public List<GetUserRes> getUsers() throws BaseException {
    try {
      List<GetUserRes> getUserRes = userDao.getUsers();
      return getUserRes;
    } catch (Exception exception) {
      throw new BaseException(DATABASE_ERROR);
    }
  }

  // 해당 지역에 속한 유저들의 정보 조회 API
  public List<GetUserRes> getUsersByNickname(String location) throws BaseException {
    try {
      List<GetUserRes> getUsersRes = userDao.getUsersByNickname(location);
      return getUsersRes;
    } catch (Exception exception) {
      throw new BaseException(DATABASE_ERROR);
    }
  }


  // 해당 userIdx를 갖는 User의 정보 조회
  public GetUserRes getUser(int userIdx) throws BaseException {
    try {
      GetUserRes getUserRes = userDao.getUser(userIdx);
      return getUserRes;
    } catch (Exception exception) {
      throw new BaseException(DATABASE_ERROR);
    }
  }

}
