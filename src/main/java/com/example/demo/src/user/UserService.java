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
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserService {
  final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final UserDao userDao;
  private final UserProvider userProvider;
  private final JwtService jwtService;

  @Autowired
  public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
    this.userDao = userDao;
    this.userProvider = userProvider;
    this.jwtService = jwtService;
  }
  // 회원가입
  public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
    if (userProvider.checkEmail(postUserReq.getEmail()) == 1) {
      throw new BaseException(POST_USERS_EXISTS_EMAIL);
    }
    String pwd;
    try {
      pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
      postUserReq.setPassword(pwd);
    } catch (Exception ignored) {
      throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
    }
    try {
      int userIdx = userDao.createUser(postUserReq);
      String jwt = jwtService.createJwt(userIdx);
      return new PostUserRes(userIdx, jwt);
    } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
      throw new BaseException(DATABASE_ERROR);
    }
  }

  // 회원정보 수정(Patch)
  public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
    try {
      int result = userDao.modifyUserName(patchUserReq);
      if (result == 0) {
        throw new BaseException(MODIFY_FAIL_USERNAME);
      }
    } catch (Exception exception) {
      throw new BaseException(DATABASE_ERROR);
    }
  }
}
