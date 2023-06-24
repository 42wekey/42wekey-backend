package com.ftence.ftwekey.service;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.constant.SubjectProperties;
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

        User user = userRepository.findByIntraId(intraId);

        return commentRepository.getUserComments(user.getId())
                .stream()
                .map(this::convertEntityToUserCommentDto)
                .collect(Collectors.toList());
    }

    public List<LikeCommentDTO> getUserLikeComments(PrincipalDetails user) {

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
        List<String> unreviewedSubjects = new ArrayList<>();


        for (String s : completeSubjects) {

            if (!reviewedSubjects.contains(s))
                unreviewedSubjects.add(s);
        }

        try {
            return unreviewedSubjects
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
            list.add(SubjectProperties.LIBFT);
        // 1 circle
        if (project.getFt_printf())
            list.add(SubjectProperties.FT_PRINTF);
        if (project.getGet_next_line())
            list.add(SubjectProperties.GET_NEXT_LINE);
        if (project.getBorn2beroot())
            list.add(SubjectProperties.BORN2BEROOT);
        // 2 circle
        if (project.getMinitalk())
            list.add(SubjectProperties.MINITALK);
        if (project.getPipex())
            list.add(SubjectProperties.PIPEX);
        if (project.getSo_long())
            list.add(SubjectProperties.SO_LONG);
        if (project.getFdf())
            list.add(SubjectProperties.FDF);
        if (project.getFract_ol())
            list.add(SubjectProperties.FRACT_OL);
        if (project.getPush_swap())
            list.add(SubjectProperties.PUSH_SWAP);
        // 3 circle
        if (project.getMinishell())
            list.add(SubjectProperties.MINISHELL);
        if (project.getPhilosopher())
            list.add(SubjectProperties.PHILOSOPHERS);
        // 4 circle
        if (project.getNetpractice())
            list.add(SubjectProperties.NETPRACTICE);
        if (project.getCub3d())
            list.add(SubjectProperties.CUB3D);
        if (project.getMinirt())
            list.add(SubjectProperties.MINIRT);
        if (project.getCpp00())
            list.add(SubjectProperties.CPP_MODULE_00);
        if (project.getCpp01())
            list.add(SubjectProperties.CPP_MODULE_01);
        if (project.getCpp02())
            list.add(SubjectProperties.CPP_MODULE_02);
        if (project.getCpp03())
            list.add(SubjectProperties.CPP_MODULE_03);
        if (project.getCpp04())
            list.add(SubjectProperties.CPP_MODULE_04);
        // 5 circle
        if (project.getCpp05())
            list.add(SubjectProperties.CPP_MODULE_05);
        if (project.getCpp06())
            list.add(SubjectProperties.CPP_MODULE_06);
        if (project.getCpp07())
            list.add(SubjectProperties.CPP_MODULE_07);
        if (project.getCpp08())
            list.add(SubjectProperties.CPP_MODULE_08);
        if (project.getCpp09())
            list.add(SubjectProperties.CPP_MODULE_09);
        if (project.getInception())
            list.add(SubjectProperties.INCEPTION);
        if (project.getWebserv())
            list.add(SubjectProperties.WEBSERV);
        if (project.getFt_irc())
            list.add(SubjectProperties.FT_IRC);
        // 6 circle
        if (project.getFt_transcendence())
            list.add(SubjectProperties.FT_TRANSCENDENCE);

        return list;
    }

    private UnreviewedSubjectDTO convertEntityToUnreviewedSubjectDto(String subjectName) {

        Subject subject = subjectRepository.findByName(subjectName);

        return new UnreviewedSubjectDTO(subject.getName(), subject.getCircle());
    }
}
