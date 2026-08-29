package com.example.gateway.Service;

import com.example.gateway.Dto.OrderResponseDto;
import com.example.gateway.Entity.Order;
import com.example.gateway.Entity.Status.OrderStatus;
import com.example.gateway.Entity.User;
import com.example.gateway.Exception.ForbiddenException;
import com.example.gateway.Exception.ResourceNotFoundException;
import com.example.gateway.Repository.OrderRepository;
import com.example.gateway.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order, Authentication authentication) {
        order.setStatus(OrderStatus.CREATED);
        order.setUser((User) authentication.getPrincipal());
        return orderRepository.save(order);
    }

    public OrderResponseDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderResponseDto.from(order);
    }

    public List<OrderResponseDto> getAllOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(OrderResponseDto::from).toList();
    }

    public OrderResponseDto cancelOrder(Long orderId, User user) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("You cannot cancel this order");
        }

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED ||
                order.getStatus() == OrderStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Order cannot be cancelled in its current state"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return OrderResponseDto.from(order);
    }
}