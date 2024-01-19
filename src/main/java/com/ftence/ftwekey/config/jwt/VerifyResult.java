package com.ftence.ftwekey.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyResult {
    private Long uniqueId;
    private String intraId;
    private double level;
}
