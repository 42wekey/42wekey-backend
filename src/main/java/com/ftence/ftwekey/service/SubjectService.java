package com.ftence.ftwekey.service;

import com.ftence.ftwekey.dto.response.*;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

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

    public SubjectDescriptionDTO getDescription(String subjectName) {

        Subject subject = subjectRepository.findByName(subjectName);

        return new SubjectDescriptionDTO(subject.getDescription());
    }

    private List<SubjectRankInfoDTO> makeSubjectRank(List<Subject> rank) {

        List<SubjectRankInfoDTO> list = new ArrayList<>();

        for (Subject s : rank) {

            list.add(SubjectRankInfoDTO.builder()
                    .subjectName(s.getName())
                    .circle(s.getCircle())
                    .starRating(s.getRating())
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
