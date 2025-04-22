package ru.sibsutis.shop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionResponseDto {
    private String expiryDate;
    private String token;
}
