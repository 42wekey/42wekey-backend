package com.ftence.ftwekey.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentRequestDTO {

    private String content;
    private int starRating;
    private String timeTaken;
    private String amountStudy;
    private String bonus;
    private String difficulty;
}
