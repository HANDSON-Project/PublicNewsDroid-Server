package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
public class UserController {
  final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private final UserProvider userProvider;
  @Autowired
  private final UserService userService;
  @Autowired
  private final JwtService jwtService;

  public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
    this.userProvider = userProvider;
    this.userService = userService;
    this.jwtService = jwtService;
  }

  /**
   * 회원가입 API
   * [POST] /users/sign-up
   */
  // Body
  @ResponseBody
  @PostMapping("/sign-up")
  public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
    // email 값이 null인지 체크
    if (postUserReq.getEmail() == null) {
      return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
    }
    //이메일 형식 검사
    if (!isRegexEmail(postUserReq.getEmail())) {
      return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
    }
    try {
      PostUserRes postUserRes = userService.createUser(postUserReq);
      return new BaseResponse<>(postUserRes);
    } catch (BaseException exception) {
      return new BaseResponse<>((exception.getStatus()));
    }
  }

  /**
   * 로그인 API
   * [POST] /users/log-in
   */
  @ResponseBody
  @PostMapping("/log-in")
  public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
    try {
      PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
      return new BaseResponse<>(postLoginRes);
    } catch (BaseException exception) {
      return new BaseResponse<>(exception.getStatus());
    }
  }


  /**
   * 모든 회원들의  조회 API
   * [GET] /users
   *
   * 또는
   *
   * 해당 지역에 속한 유저들의 정보 조회 API
   * [GET] /users? location=
   */
  @ResponseBody
  @GetMapping("")
  public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String location) {
    try {
      if (location == null) {
        List<GetUserRes> getUsersRes = userProvider.getUsers();
        return new BaseResponse<>(getUsersRes);
      }
      List<GetUserRes> getUsersRes = userProvider.getUsersByNickname(location);
      return new BaseResponse<>(getUsersRes);
    } catch (BaseException exception) {
      return new BaseResponse<>((exception.getStatus()));
    }
  }

  /**
   * 회원 1명 조회 API
   * [GET] /users/:userIdx
   */
  @ResponseBody
  @GetMapping("/{userIdx}")
  public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
    try {
      int userIdxByJwt = jwtService.getUserIdx();
      if(userIdx != userIdxByJwt){
        return new BaseResponse<>(INVALID_USER_JWT);
      }
      GetUserRes getUserRes = userProvider.getUser(userIdx);
      return new BaseResponse<>(getUserRes);
    } catch (BaseException exception) {
      return new BaseResponse<>((exception.getStatus()));
    }
  }

  /**
   * 유저정보변경(위치변경) API
   * [PATCH] /users/:userIdx
   */
  @ResponseBody
  @PatchMapping("/{userIdx}")
  public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
    try {
      int userIdxByJwt = jwtService.getUserIdx();
      if(userIdx != userIdxByJwt){
        return new BaseResponse<>(INVALID_USER_JWT);
      }
      PatchUserReq patchUserReq = new PatchUserReq(userIdx, user.getLocation());
      userService.modifyUserName(patchUserReq);
      String result = "회원정보가 수정되었습니다.";
      return new BaseResponse<>(result);
    } catch (BaseException exception) {
      return new BaseResponse<>((exception.getStatus()));
    }
  }
}
