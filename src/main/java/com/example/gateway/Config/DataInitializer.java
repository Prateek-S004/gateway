package com.example.gateway.Config;

import com.example.gateway.Entity.Order;
import com.example.gateway.Entity.Role.Role;
import com.example.gateway.Entity.Status.OrderStatus;
import com.example.gateway.Entity.User;
import com.example.gateway.Repository.OrderRepository;
import com.example.gateway.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers() {
        return args -> {

            String encodedPassword =
                    passwordEncoder.encode("password@123");

            User rajeev = createUser(
                    "Rajeev",
                    "rajeev@gmail.com",
                    Role.USER,
                    encodedPassword
            );

            User rahul = createUser(
                    "Rahul",
                    "rahul@gmail.com",
                    Role.USER,
                    encodedPassword
            );

            User amit = createUser(
                    "Amit",
                    "amit@gmail.com",
                    Role.USER,
                    encodedPassword
            );

            User admin = createUser(
                    "Admin",
                    "admin@gmail.com",
                    Role.ADMIN,
                    encodedPassword
            );

            User manager = createUser(
                    "Manager",
                    "manager@gmail.com",
                    Role.MANAGER,
                    encodedPassword
            );

            createDefaultOrders(rajeev, rahul, amit);
        };
    }

    private User createUser(
            String name,
            String email,
            Role role,
            String encodedPassword) {

        User existingUser = (User) userRepository.findByEmail(email);

        if (existingUser != null) {
            return existingUser;
        }

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(role);
        user.setRefreshToken(null);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    private void createDefaultOrders(
            User rajeev,
            User rahul,
            User amit) {

        if (orderRepository.count() > 0) {
            return;
        }

        // Rajeev's orders
        createOrder(
                "Laptop",
                1,
                OrderStatus.CREATED,
                rajeev
        );

        createOrder(
                "Mechanical Keyboard",
                2,
                OrderStatus.CONFIRMED,
                rajeev
        );

        // Rahul's orders
        createOrder(
                "Monitor",
                1,
                OrderStatus.SHIPPED,
                rahul
        );

        createOrder(
                "Mouse",
                2,
                OrderStatus.CREATED,
                rahul
        );

        // Amit's order
        createOrder(
                "Headphones",
                1,
                OrderStatus.DELIVERED,
                amit
        );
    }

    private void createOrder(
            String product,
            Integer quantity,
            OrderStatus status,
            User user) {

        Order order = new Order();

        order.setProduct(product);
        order.setQuantity(quantity);
        order.setStatus(status);
        order.setUser(user);

        orderRepository.save(order);
    }
}