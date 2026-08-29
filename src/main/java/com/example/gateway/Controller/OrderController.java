package com.example.gateway.Controller;

import com.example.gateway.Dto.OrderResponseDto;
import com.example.gateway.Entity.Order;
import com.example.gateway.Entity.User;
import com.example.gateway.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAuthority('ORDER_CREATE')")
    @PostMapping
    public Order createOrder(@RequestBody Order order, Authentication authentication) {
        return orderService.createOrder(order, authentication);
    }

    @PreAuthorize("hasAuthority('ORDER_READ')")
    @GetMapping("/order")
    public List<OrderResponseDto> getAllOrders(Authentication authentication) {
        return orderService.getAllOrders(authentication);
    }

    @PreAuthorize("hasAuthority('ORDER_CANCEL')")
    @PatchMapping("/{id}/cancel")
    public OrderResponseDto cancelOrder(@PathVariable Long id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return orderService.cancelOrder(id, user);
    }
}