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
}
