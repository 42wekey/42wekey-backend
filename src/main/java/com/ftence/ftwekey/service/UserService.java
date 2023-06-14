package com.ftence.ftwekey.service;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.dto.response.*;
import com.ftence.ftwekey.entity.*;
import com.ftence.ftwekey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final ProjectRepository projectRepository;
    private final SubjectRepository subjectRepository;

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

    public List<String> getReviewedSubjects(User user) {

        return commentRepository.getUserComments(user.getId())
                .stream()
                .map(comment -> comment.getSubject().getName())
                .collect(Collectors.toList());
    }

    public List<UnreviewedSubjectDTO> getUnreviewedSubjects(String intraId) {

        User user = userRepository.findByIntraId(intraId);
        Project project = projectRepository.findByUser(user);

        List<String> completeSubjects = getCompleteSubjects(project);
        List<String> reviewedSubjects = getReviewedSubjects(user);


        for (String s : completeSubjects) {

            if (reviewedSubjects.contains(s))
                completeSubjects.remove(s);
        }

        try {
            return completeSubjects
                    .stream()
                    .map(this::convertEntityToUnreviewedSubjectDto)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {

            return null;
        }
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

    private List<String> getCompleteSubjects(Project project) {

        List<String> list = new ArrayList<>();

        // 0 circle
        if (project.getLibft())
            list.add("Libft");
        // 1 circle
        if (project.getFt_printf())
            list.add("ft_printf");
        if (project.getGet_next_line())
            list.add("get_next_line");
        if (project.getBorn2beroot())
            list.add("Born2beroot");
        // 2 circle
        if (project.getMinitalk())
            list.add("minitalk");
        if (project.getPipex())
            list.add("pipex");
        if (project.getSo_long())
            list.add("so_long");
        if (project.getFdf())
            list.add("FdF");
        if (project.getFract_ol())
            list.add("fract-ol");
        if (project.getPush_swap())
            list.add("push_swap");
        // 3 circle
        if (project.getMinishell())
            list.add("minishell");
        if (project.getPhilosopher())
            list.add("Philosophers");
        // 4 circle
        if (project.getNetpractice())
            list.add("NetPractice");
        if (project.getCub3d())
            list.add("cub3d");
        if (project.getMinirt())
            list.add("miniRT");
        if (project.getCpp00())
            list.add("CPP Module 00");
        if (project.getCpp01())
            list.add("CPP Module 01");
        if (project.getCpp02())
            list.add("CPP Module 02");
        if (project.getCpp03())
            list.add("CPP Module 03");
        if (project.getCpp04())
            list.add("CPP Module 04");
        // 5 circle
        if (project.getCpp05())
            list.add("CPP Module 05");
        if (project.getCpp06())
            list.add("CPP Module 06");
        if (project.getCpp07())
            list.add("CPP Module 07");
        if (project.getCpp08())
            list.add("CPP Module 08");
        if (project.getCpp09())
            list.add("CPP Module 09");
        if (project.getInception())
            list.add("Inception");
        if (project.getWebserv())
            list.add("webserv");
        if (project.getFt_irc())
            list.add("ft_irc");
        // 6 circle
        if (project.getFt_transcendence())
            list.add("ft_transcendence");

        return list;
    }

    private UnreviewedSubjectDTO convertEntityToUnreviewedSubjectDto(String subjectName) {

        // todo 예외처리
        Subject subject = subjectRepository.findByName(subjectName);
        return new UnreviewedSubjectDTO(subject.getName(), subject.getCircle());
    }
}
