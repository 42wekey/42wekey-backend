package com.ftence.ftwekey.service;

import com.ftence.ftwekey.dto.response.RecentCommentDTO;
import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public List<RecentCommentDTO> getRecentComment() {

        List<RecentCommentDTO> list = new ArrayList<>();
        List<Comment> comments = commentRepository.getRecentComment();

        for (Comment c : comments) {
            list.add(RecentCommentDTO.builder()
                    .subjectName(c.getSubject().getName())
                    .starRating(c.getRating().getStarRating())
                    .content(c.getContent())
                    .createTime(c.getCreateTime())
                    .build());
        }
        return list;
    }
}
