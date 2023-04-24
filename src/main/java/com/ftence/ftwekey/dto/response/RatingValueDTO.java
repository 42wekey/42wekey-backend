package com.ftence.ftwekey.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RatingValueDTO {
    private String title;
    private double value;
    private List<Double> detail;
}
