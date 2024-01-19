package com.ftence.ftwekey.controller;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.request.CommentRequestDTO;
import com.ftence.ftwekey.dto.response.CommentDTO;
import com.ftence.ftwekey.dto.response.RecentCommentDTO;
import com.ftence.ftwekey.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/recent")
    public List<RecentCommentDTO> recentComment() {

        return commentService.getRecentComment();
    }

    @GetMapping("/{subjectName}")
    public List<CommentDTO> getSubjectComments(@AuthenticationPrincipal PrincipalDetails user,
                                               @PathVariable String subjectName) {

        log.info("Received a request to get Subject's Comments : subjectName={}, user={}", subjectName, user.getName());

        return commentService.getSubjectComments(user, subjectName);
    }

    @PostMapping(value = "/{commentId}/like")
    public ResponseEntity<String> setCommentLike(@AuthenticationPrincipal PrincipalDetails user,
                                                 @PathVariable Long commentId) {

        log.info("Received a request to set Likes: commentID={}, user={}", commentId, user.getName());

        return commentService.setCommentLike(user, commentId);
    }

    @PostMapping("/create")
    public void createComment(@AuthenticationPrincipal PrincipalDetails user,
                              @RequestParam("subject-name") String subjectName,
                              @RequestBody CommentRequestDTO commentRequestDTO) {

        log.info("Received a request to create Comment : user={}, subjectName={}, {}", user.getName(), subjectName, commentRequestDTO);

        commentService.createComment(subjectName, commentRequestDTO, user);
    }

    @PostMapping("/{commentId}/edit")
    public void editComment(@AuthenticationPrincipal PrincipalDetails user,
                            @PathVariable Long commentId,
                            @RequestBody CommentRequestDTO commentRequestDTO) {

        log.info("Received a request to edit Comment by ID : {}, user={}, {}", commentId, user.getName(), commentRequestDTO);

        commentService.editComment(commentId, commentRequestDTO, user);
    }
}
