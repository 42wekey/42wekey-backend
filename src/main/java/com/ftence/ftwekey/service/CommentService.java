package com.ftence.ftwekey.service;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.response.CommentDTO;
import com.ftence.ftwekey.dto.response.RecentCommentDTO;
import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.entity.Heart;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.repository.CommentRepository;
import com.ftence.ftwekey.repository.HeartRepository;
import com.ftence.ftwekey.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SubjectRepository subjectRepository;
    private final HeartRepository heartRepository;

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

    public List<CommentDTO> getSubjectComments(PrincipalDetails user, String subjectName) {

        Subject subject = subjectRepository.findByName(subjectName);

        List<CommentDTO> list = commentRepository.findBySubject(subject)
                .stream()
                .map(comment -> convertEntityToCommentDTO(comment, user.getUser(), subject))
                .collect(Collectors.toList());

        return list;
    }

    public void setCommentLike(PrincipalDetails user, Long commentId) {

        Comment comment = commentRepository.findById(commentId).get();
        // todo commentId 검증
        List<Heart> hearts = heartRepository.getUserLikedThisComment(comment.getId(), user.getUser().getId());

        if (hearts.size() > 0 ) {

            heartRepository.deleteAll(hearts);
        }
        else {

            heartRepository.save(new Heart(null, comment, user.getUser()));
        }
    }


    private CommentDTO convertEntityToCommentDTO(Comment comment, User user, Subject subject) {

        boolean isLiked;

        List<Heart> hearts = heartRepository.getUserLikedThisComment(comment.getId(), user.getId());

        isLiked = hearts.size() > 0;

        return CommentDTO.builder()
                .intraId(comment.getUser().getIntraId())
                .circle(subject.getCircle())
                .subjectName(subject.getName())
                .userLevel(comment.getUserLevel())
                .commentId(comment.getId())
                .content(comment.getContent())
                .updateTime(comment.getUpdateTime())
                .starRating(comment.getRating().getStarRating())
                .timeTaken(comment.getRating().getTimeTaken())
                .amountStudy(comment.getRating().getAmountStudy())
                .bonus(comment.getRating().getBonus())
                .difficulty(comment.getRating().getDifficulty())
                .likeNum(comment.getLikeCnt())
                .isLike(isLiked)
                .build();
    }
}
