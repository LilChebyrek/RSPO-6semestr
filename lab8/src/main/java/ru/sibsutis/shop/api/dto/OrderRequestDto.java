package ru.sibsutis.shop.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderDetailDto> orderDetails;
    private Long paymentId;
}
