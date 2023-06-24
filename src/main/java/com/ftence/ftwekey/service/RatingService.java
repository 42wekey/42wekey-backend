package com.ftence.ftwekey.service;

import com.ftence.ftwekey.constant.CommentProperties;
import com.ftence.ftwekey.dto.response.RatingValueDTO;
import com.ftence.ftwekey.dto.response.SubjectRatingDTO;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.repository.RatingRepository;
import com.ftence.ftwekey.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
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
                .timeTaken(getRatingValueDTO(subject, CommentProperties.TIME_TAKEN, CommentProperties.TIME_TAKEN_VALUES))
                .amountStudy(getRatingValueDTO(subject, CommentProperties.AMOUNT_STUDY, CommentProperties.AMOUNT_STUDY_VALUES))
                .bonus(getRatingValueDTO(subject, CommentProperties.BONUS, CommentProperties.BONUS_VALUES))
                .difficulty(getRatingValueDTO(subject, CommentProperties.DIFFICULTY, CommentProperties.DIFFICULTY_VALUES))
                .build();
    }

    private RatingValueDTO getRatingValueDTO(Subject subject, String status, String values) {

        List<String> value = List.of(values.split(", "));

        String title;

        List<Double> percentage = new ArrayList<>();

        for (String v : value) {
            switch (status) {
                case CommentProperties.TIME_TAKEN:
                    percentage.add(ratingRepository.timeTakenPercentage(subject.getId(), v));
                    break;
                case CommentProperties.AMOUNT_STUDY:
                    percentage.add(ratingRepository.amountStudyPercentage(subject.getId(), v));
                    break;
                case CommentProperties.BONUS:
                    percentage.add(ratingRepository.bonusPercentage(subject.getId(), v));
                    break;
                case CommentProperties.DIFFICULTY:
                    percentage.add(ratingRepository.difficultyPercentage(subject.getId(), v));
                    break;
                default:
                    break;
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
        }

        return RatingValueDTO.builder()
                .title(title)
                .value(maxValue)
                .detail(percentage)
                .build();
    }
}
