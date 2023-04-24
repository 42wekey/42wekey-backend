package com.ftence.ftwekey.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WikiResponseDTO {

    private Long id;
    private String content;
}
