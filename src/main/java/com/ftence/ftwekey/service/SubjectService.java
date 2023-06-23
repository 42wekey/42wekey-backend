package com.ftence.ftwekey.service;

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
                .title("평점 높은")
                .rank(makeSubjectRank(subjectRepository.getRatingRank()))
                .build());
        list.add(SubjectRankDTO.builder()
                .title("리뷰 많은")
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
        if (subjectName.equals("Libft"))
            return project.getLibft();
        // 1 circle
        if (subjectName.equals("ft_printf"))
            return project.getFt_printf();
        if (subjectName.equals("get_next_line"))
            return project.getGet_next_line();
        if (subjectName.equals("Born2beroot"))
            return project.getBorn2beroot();
        // 2circle
        if (subjectName.equals("minitalk"))
            return project.getMinitalk();
        if (subjectName.equals("pipex"))
            return project.getPipex();
        if (subjectName.equals("so_long"))
            return project.getSo_long();
        if (subjectName.equals("FdF"))
            return project.getFdf();
        if (subjectName.equals("fract-ol"))
            return project.getFract_ol();
        if (subjectName.equals("push_swap"))
            return project.getPush_swap();
        // 3 circle
        if (subjectName.equals("minishell"))
            return project.getMinishell();
        if (subjectName.equals("Philosophers"))
            return project.getPhilosopher();
        // 4 circle
        if (subjectName.equals("NetPractice"))
            return project.getNetpractice();
        if (subjectName.equals("cub3d"))
            return project.getCub3d();
        if (subjectName.equals("miniRT"))
            return project.getMinirt();
        if (subjectName.equals("CPP Module 00"))
            return project.getCpp00();
        if (subjectName.equals("CPP Module 01"))
            return project.getCpp01();
        if (subjectName.equals("CPP Module 02"))
            return project.getCpp02();
        if (subjectName.equals("CPP Module 03"))
            return project.getCpp03();
        if (subjectName.equals("CPP Module 04"))
            return project.getCpp04();
        // 5 circle
        if (subjectName.equals("CPP Module 05"))
            return project.getCpp05();
        if (subjectName.equals("CPP Module 06"))
            return project.getCpp06();
        if (subjectName.equals("CPP Module 07"))
            return project.getCpp07();
        if (subjectName.equals("CPP Module 08"))
            return project.getCpp08();
        if (subjectName.equals("CPP Module 09"))
            return project.getCpp09();
        if (subjectName.equals("Inception"))
            return project.getInception();
        if (subjectName.equals("webserv"))
            return project.getWebserv();
        if (subjectName.equals("ft_irc"))
            return project.getFt_irc();
        // 6 circle
        if (subjectName.equals("ft_transcendence"))
            return project.getFt_transcendence();

        return false;
    }

    private List<SubjectRankInfoDTO> makeSubjectRank(List<Subject> rank) {

        List<SubjectRankInfoDTO> list = new ArrayList<>();

        for (Subject s : rank) {

            list.add(SubjectRankInfoDTO.builder()
                    .subjectName(s.getName())
                    .circle(s.getCircle())
                    .starRating(ratingRepository.starRatingAvg(s.getId()))
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
