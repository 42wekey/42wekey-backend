package com.ftence.ftwekey.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectRequestDTO {

    private String subjectName;
    private int circle;
    private String subjectInfo;
    private String description;
}
