package com.ftence.ftwekey.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ratingValueDTO {
    private String title;
    private double value;
    private List<Double> detail;
}
