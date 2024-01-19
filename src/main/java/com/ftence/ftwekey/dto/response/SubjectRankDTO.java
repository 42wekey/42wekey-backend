package com.ftence.ftwekey.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubjectRankDTO {
    private String title;
    private List<SubjectRankInfoDTO> rank;
}
