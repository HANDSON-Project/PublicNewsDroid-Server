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
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReactionProvider {
    private final ReactionDao reactionDao;
    private final JwtService jwtService;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReactionProvider(ReactionDao reactionDao, JwtService jwtService) {
        this.reactionDao = reactionDao;
        this.jwtService = jwtService;
    }

    // User들의 정보를 조회
    public List<GetNewsCommentRes> getNewsComment(int newsIdx) throws BaseException {
        try {
            List<GetNewsCommentRes> getCommmentsRes = reactionDao.getCommments(newsIdx);
            return getCommmentsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
