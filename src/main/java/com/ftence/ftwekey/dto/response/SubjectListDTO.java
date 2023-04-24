package com.ftence.ftwekey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubjectListDTO {

    private int circle;
    private List<SubjectInfoDTO> subjectInfo;
}
