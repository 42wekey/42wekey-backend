package com.ftence.ftwekey.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentDTO {

    private String intraId;
    private int circle;
    private String subjectName;
    private double userLevel;
    private Long commentId;
    private String content;
    private LocalDateTime updateTime;
    private double starRating;
    private String timeTaken;
    private String amountStudy;
    private String bonus;
    private String difficulty;
    private int likeNum;
    private Boolean isLike;
}