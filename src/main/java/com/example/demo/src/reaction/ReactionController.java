package com.example.demo.src.reaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.reaction.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/news")
public class ReactionController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final ReactionProvider reactionProvider;
    @Autowired
    private final ReactionService reactionService;
    @Autowired
    private final JwtService jwtService;

    public ReactionController(ReactionProvider reactionProvider, ReactionService reactionService, JwtService jwtService) {
        this.reactionProvider = reactionProvider;
        this.reactionService = reactionService;
        this.jwtService = jwtService;
    }

}
