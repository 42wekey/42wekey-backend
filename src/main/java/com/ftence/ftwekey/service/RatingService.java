package com.ftence.ftwekey.service;

import com.ftence.ftwekey.dto.response.RatingValueDTO;
import com.ftence.ftwekey.dto.response.SubjectRatingDTO;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.repository.CommentRepository;
import com.ftence.ftwekey.repository.RatingRepository;
import com.ftence.ftwekey.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RatingService {

    @Value("${time-taken}")
    private String timeTakenValue;
    @Value("${amount-study}")
    private String amountStudyValue;
    @Value("${bonus}")
    private String bonusValue;
    @Value("${difficulty}")
    private String difficultyValue;

    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;
    private final SubjectRepository subjectRepository;

    public SubjectRatingDTO getRatingAverage(String subjectName) {

        Subject subject = subjectRepository.findByName(subjectName);

        return entityToSubjectRatingDTO(subject);
    }


    private SubjectRatingDTO entityToSubjectRatingDTO(Subject subject) {

        List<Integer> totalStarRating = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            totalStarRating.add(ratingRepository.starRatingCount(subject.getId(), i));
        }

        return SubjectRatingDTO.builder()
                .commentCnt(subject.getCommentCnt())
                .avgStarRating(ratingRepository.starRatingAvg(subject.getId()))
                .totalStarRating(totalStarRating)
                .timeTaken(getRatingValueDTO(subject, "timeTaken", timeTakenValue))
                .amountStudy(getRatingValueDTO(subject, "amountStudy", timeTakenValue))
                .bonus(getRatingValueDTO(subject, "bonus", timeTakenValue))
                .difficulty(getRatingValueDTO(subject, "difficulty", timeTakenValue))
                .build();

    }

    public RatingValueDTO getRatingValueDTO(Subject subject, String status, String values) {

        List<String> value = List.of(values.split(", "));

        String title;

        List<Double> percentage = new ArrayList<>();

        for (String v : value) {
            switch (status) {
                case "timeTaken":
                    percentage.add(ratingRepository.timeTakenPercentage(subject.getId(), v));
                    break;
                case "amountStudy":
                    percentage.add(ratingRepository.amountStudyPercentage(subject.getId(), v));
                    break;
                case "bonus":
                    percentage.add(ratingRepository.bonusPercentage(subject.getId(), v));
                    break;
                case "difficulty":
                    percentage.add(ratingRepository.difficultyPercentage(subject.getId(), v));
                    break;
                default:
                    break;
                    // 에러처리
            }
        }

        Double maxValue = Collections.max(percentage);

        int index = percentage.indexOf(maxValue) + 1;

        switch (index) {
            case 1:
                title = value.get(0);
                break;
            case 2:
                title = value.get(1);
                break;
            case 3:
                title = value.get(2);
                break;
            case 4:
                title = value.get(3);
                break;
            case 5:
                title = value.get(4);
                break;
            default:
                title = "error";
                break;
            // todo 에러 처리
        }

        return RatingValueDTO.builder()
                .title(title)
                .value(maxValue)
                .detail(percentage)
                .build();
    }
}