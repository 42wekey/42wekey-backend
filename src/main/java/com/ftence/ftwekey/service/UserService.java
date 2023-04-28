package com.ftence.ftwekey.service;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.response.LikeCommentDTO;
import com.ftence.ftwekey.dto.response.UserCommentDTO;
import com.ftence.ftwekey.dto.response.UserInfoDTO;
import com.ftence.ftwekey.dto.response.UserMeInfoDTO;
import com.ftence.ftwekey.entity.*;
import com.ftence.ftwekey.repository.CommentRepository;
import com.ftence.ftwekey.repository.HeartRepository;
import com.ftence.ftwekey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;

    public UserMeInfoDTO getMyInfo(PrincipalDetails user) {

        return UserMeInfoDTO.builder()
                .intraId(user.getUsername())
                .level(user.getLevel())
                .build();
    }

    public UserInfoDTO getUserInfo(String intraId) {

        // todo 예외 처리
        User user = userRepository.findByIntraId(intraId);

        int cntComment = commentRepository.getUserCommentCnt(user.getId());
        int cntLikes = heartRepository.getUserLikesCnt(user.getId());

        return UserInfoDTO.builder()
                .userLevel(user.getLevel())
                .cntComment(cntComment)
                .cntLikes(cntLikes)
                .build();
    }

    public List<UserCommentDTO> getUserComments(String intraId) {

        // todo 예외 처리
        User user = userRepository.findByIntraId(intraId);

        return commentRepository.getUserComments(user.getId())
                .stream()
                .map(this::convertEntityToUserCommentDto)
                .collect(Collectors.toList());
    }

    public List<LikeCommentDTO> getUserLikeComments(PrincipalDetails user) {

        // todo 예외 처리

        return heartRepository.getUserLikeComments(user.getUser().getId())
                .stream()
                .map(this::convertEntityToLikeCommentDto)
                .collect(Collectors.toList());
    }

    public List<String> getReviewedSubjects(PrincipalDetails user) {

        return commentRepository.getUserComments(user.getUser().getId())
                .stream()
                .map(comment -> comment.getSubject().getName())
                .collect(Collectors.toList());
    }

    private UserCommentDTO convertEntityToUserCommentDto(Comment comment) {

        Subject subject = comment.getSubject();
        Rating rating = comment.getRating();

        return UserCommentDTO.builder()
                .circle(subject.getCircle())
                .subjectName(subject.getName())
                .commentId(comment.getId())
                .content(comment.getContent())
                .updateTime(comment.getUpdateTime())
                .starRating(rating.getStarRating())
                .timeTaken(rating.getTimeTaken())
                .amountStudy(rating.getAmountStudy())
                .bonus(rating.getBonus())
                .difficulty(rating.getDifficulty())
                .likeNum(comment.getLikeCnt())
                .build();
    }

    private LikeCommentDTO convertEntityToLikeCommentDto(Heart heart) {

        // todo 예외처리
        Comment comment = heart.getComment();
        Subject subject = comment.getSubject();
        Rating rating = comment.getRating();

        return LikeCommentDTO.builder()
                .intraId(comment.getUser().getIntraId())
                .userLevel(comment.getUserLevel())
                .circle(subject.getCircle())
                .subjectName(subject.getName())
                .commentId(comment.getId())
                .content(comment.getContent())
                .updateTime(comment.getUpdateTime())
                .starRating(rating.getStarRating())
                .timeTaken(rating.getTimeTaken())
                .amountStudy(rating.getAmountStudy())
                .bonus(rating.getBonus())
                .difficulty(rating.getDifficulty())
                .likeCnt(comment.getLikeCnt())
                .build();
    }
}
