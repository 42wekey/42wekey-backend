package com.ftence.ftwekey.service;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.request.CommentRequestDTO;
import com.ftence.ftwekey.dto.response.CommentDTO;
import com.ftence.ftwekey.dto.response.RecentCommentDTO;
import com.ftence.ftwekey.entity.*;
import com.ftence.ftwekey.repository.CommentRepository;
import com.ftence.ftwekey.repository.HeartRepository;
import com.ftence.ftwekey.repository.RatingRepository;
import com.ftence.ftwekey.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SubjectRepository subjectRepository;
    private final HeartRepository heartRepository;
    private final RatingRepository ratingRepository;

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

    public ResponseEntity<String> setCommentLike(PrincipalDetails user, Long commentId) {

        try {

            Comment comment = commentRepository.findById(commentId).get();
            List<Heart> hearts = heartRepository.getUserLikedThisComment(comment.getId(), user.getUser().getId());
            String body;

            if (hearts.size() > 0) {

                heartRepository.deleteAll(hearts);
                comment.setLikeCnt(comment.getLikeCnt() - 1);
                body = "delete likes";
            } else {

                heartRepository.save(new Heart(null, comment, user.getUser()));
                comment.setLikeCnt(comment.getLikeCnt() + 1);
                body = "add likes";
            }
            commentRepository.save(comment);

            return ResponseEntity.ok(body);

        } catch (NoSuchElementException e) {

            log.error("comment id not valid. id={}", commentId);
            return ResponseEntity.badRequest().body("Invalid data format");
        }
    }

    public void createComment(String subjectName, CommentRequestDTO commentRequestDTO, PrincipalDetails user) {

        Subject subject = subjectRepository.findByName(subjectName);

        // 서브젝트에 이미 후기를 작성했는지 확인
        if (commentRepository.checkAlreadyReviewed(user.getUser().getId(), subject.getId()) == 1)
            return; // todo 프론트에서 어떻게 처리할지 생각하기
        convertCommentRequestDtoToEntity(subject, commentRequestDTO, user);
    }

    public void editComment(Long commentId, CommentRequestDTO commentRequestDTO, PrincipalDetails user) {

        // todo 예외 처리
        // todo 1 작성자가 가 맞는지, 2 findbyid 예외처리
        Comment comment = commentRepository.findById(commentId).get();
        Rating rating = comment.getRating();

        rating.setStarRating(commentRequestDTO.getStarRating());
        rating.setTimeTaken(commentRequestDTO.getTimeTaken());
        rating.setAmountStudy(commentRequestDTO.getAmountStudy());
        rating.setBonus(commentRequestDTO.getBonus());
        rating.setDifficulty(commentRequestDTO.getDifficulty());

        ratingRepository.save(rating);

        comment.setContent(commentRequestDTO.getContent());
        comment.setUserLevel(user.getLevel());

        commentRepository.save(comment);
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

    private void convertCommentRequestDtoToEntity(Subject subject, CommentRequestDTO commentRequestDTO, PrincipalDetails user) {

        Rating rating = Rating.builder()
                .starRating(commentRequestDTO.getStarRating())
                .timeTaken(commentRequestDTO.getTimeTaken())
                .amountStudy(commentRequestDTO.getAmountStudy())
                .bonus(commentRequestDTO.getBonus())
                .difficulty(commentRequestDTO.getDifficulty())
                .build();
        ratingRepository.save(rating);

        Comment comment = Comment.builder()
                .user(user.getUser())
                .subject(subject)
                .rating(rating)
                .content(commentRequestDTO.getContent())
                .userLevel(user.getLevel())
                .likeCnt(0)
                .build();
        commentRepository.save(comment);

        subject.setCommentCnt(subject.getCommentCnt() + 1);
        subjectRepository.save(subject);
    }
}
