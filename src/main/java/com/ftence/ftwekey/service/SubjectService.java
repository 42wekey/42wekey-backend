package com.ftence.ftwekey.service;

import com.ftence.ftwekey.constant.CommentProperties;
import com.ftence.ftwekey.constant.SubjectProperties;
import com.ftence.ftwekey.dto.response.*;
import com.ftence.ftwekey.entity.Project;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.repository.ProjectRepository;
import com.ftence.ftwekey.repository.RatingRepository;
import com.ftence.ftwekey.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ProjectRepository projectRepository;
    private final RatingRepository ratingRepository;

    public List<SubjectRankDTO> getRank() {

        List<SubjectRankDTO> list = new ArrayList<>();

        list.add(SubjectRankDTO.builder()
                .title(CommentProperties.Rating_Rank)
                .rank(makeSubjectRank(subjectRepository.getRatingRank()))
                .build());
        list.add(SubjectRankDTO.builder()
                .title(CommentProperties.REVIEW_RANK)
                .rank(makeSubjectRank(subjectRepository.getReviewRank()))
                .build());

        return list;
    }

    public List<SubjectListDTO> getList() {

        List<SubjectListDTO> list = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            list.add(SubjectListDTO.builder()
                    .circle(i)
                    .subjectInfo(makeSubjectInfoList(subjectRepository.findByCircle(i)))
                    .build()
            );
        }

        return list;
    }

    public SubjectDescriptionDTO getDescription(String subjectName, User user) {

        Subject subject = subjectRepository.findByName(subjectName);
        Project project = projectRepository.findByUser(user);


        return new SubjectDescriptionDTO(subject.getDescription(), checkSubjectSuccess(subjectName, project));
    }

    private boolean checkSubjectSuccess(String subjectName, Project project) {

        // 0 circle
        if (subjectName.equals(SubjectProperties.LIBFT))
            return project.getLibft();
        // 1 circle
        if (subjectName.equals(SubjectProperties.FT_PRINTF))
            return project.getFt_printf();
        if (subjectName.equals(SubjectProperties.GET_NEXT_LINE))
            return project.getGet_next_line();
        if (subjectName.equals(SubjectProperties.BORN2BEROOT))
            return project.getBorn2beroot();
        // 2circle
        if (subjectName.equals(SubjectProperties.MINITALK))
            return project.getMinitalk();
        if (subjectName.equals(SubjectProperties.PIPEX))
            return project.getPipex();
        if (subjectName.equals(SubjectProperties.SO_LONG))
            return project.getSo_long();
        if (subjectName.equals(SubjectProperties.FDF))
            return project.getFdf();
        if (subjectName.equals(SubjectProperties.FRACT_OL))
            return project.getFract_ol();
        if (subjectName.equals(SubjectProperties.PUSH_SWAP))
            return project.getPush_swap();
        // 3 circle
        if (subjectName.equals(SubjectProperties.MINISHELL))
            return project.getMinishell();
        if (subjectName.equals(SubjectProperties.PHILOSOPHERS))
            return project.getPhilosopher();
        // 4 circle
        if (subjectName.equals(SubjectProperties.NETPRACTICE))
            return project.getNetpractice();
        if (subjectName.equals(SubjectProperties.CUB3D))
            return project.getCub3d();
        if (subjectName.equals(SubjectProperties.MINIRT))
            return project.getMinirt();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_00))
            return project.getCpp00();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_01))
            return project.getCpp01();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_02))
            return project.getCpp02();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_03))
            return project.getCpp03();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_04))
            return project.getCpp04();
        // 5 circle
        if (subjectName.equals(SubjectProperties.CPP_MODULE_05))
            return project.getCpp05();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_06))
            return project.getCpp06();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_07))
            return project.getCpp07();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_08))
            return project.getCpp08();
        if (subjectName.equals(SubjectProperties.CPP_MODULE_09))
            return project.getCpp09();
        if (subjectName.equals(SubjectProperties.INCEPTION))
            return project.getInception();
        if (subjectName.equals(SubjectProperties.WEBSERV))
            return project.getWebserv();
        if (subjectName.equals(SubjectProperties.FT_IRC))
            return project.getFt_irc();
        // 6 circle
        if (subjectName.equals(SubjectProperties.FT_TRANSCENDENCE))
            return project.getFt_transcendence();

        return false;
    }

    private List<SubjectRankInfoDTO> makeSubjectRank(List<Subject> rank) {

        List<SubjectRankInfoDTO> list = new ArrayList<>();


        for (Subject s : rank) {

            Double starRating = ratingRepository.starRatingAvg(s.getId());
            
            if (starRating == null)
                starRating = 0D;

            list.add(SubjectRankInfoDTO.builder()
                    .subjectName(s.getName())
                    .circle(s.getCircle())
                    .starRating(starRating)
                    .build());
        }

        return list;
    }

    private List<SubjectInfoDTO> makeSubjectInfoList(List<Subject> subjects) {

        List<SubjectInfoDTO> list = new ArrayList<>();

        for (Subject s : subjects) {
            list.add(SubjectInfoDTO.builder()
                    .name(s.getName())
                    .info(s.getInfo())
                    .build());
        }

        return list;
    }
}
