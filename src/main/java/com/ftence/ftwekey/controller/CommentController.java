package com.ftence.ftwekey.controller;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.response.CommentDTO;
import com.ftence.ftwekey.dto.response.RecentCommentDTO;
import com.ftence.ftwekey.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/recent")
    public List<RecentCommentDTO> recentComment() {

        return commentService.getRecentComment();
    }

    @GetMapping("/{subjectName}")
    public List<CommentDTO> getSubjectComments(@AuthenticationPrincipal PrincipalDetails user,
                                               @PathVariable String subjectName) {

        return commentService.getSubjectComments(user, subjectName);
    }
}
