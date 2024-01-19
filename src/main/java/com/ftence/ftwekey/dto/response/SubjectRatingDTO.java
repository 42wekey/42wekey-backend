package com.ftence.ftwekey.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectRatingDTO {
    private int commentCnt;
    private double avgStarRating;
    private List<Integer> totalStarRating;
    private RatingValueDTO timeTaken;
    private RatingValueDTO amountStudy;
    private RatingValueDTO bonus;
    private RatingValueDTO difficulty;
}
