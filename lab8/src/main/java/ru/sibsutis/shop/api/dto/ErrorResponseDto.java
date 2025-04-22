package ru.sibsutis.shop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private int code;
    private String message;
}
