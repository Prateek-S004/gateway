package com.example.gateway.Dto;

import com.example.gateway.Entity.Order;
import com.example.gateway.Entity.Status.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private String product;
    private Integer quantity;
    private OrderStatus status;

    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getProduct(),
                order.getQuantity(),
                order.getStatus()
        );
    }
}