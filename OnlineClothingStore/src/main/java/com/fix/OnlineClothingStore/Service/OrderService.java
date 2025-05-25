package com.fix.OnlineClothingStore.Service;

import com.fix.OnlineClothingStore.Model.Order;
import com.fix.OnlineClothingStore.Model.OrderItem;
import com.fix.OnlineClothingStore.Repo.AccessoryRepository;
import com.fix.OnlineClothingStore.Repo.CoffeeRepository;
import com.fix.OnlineClothingStore.Repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    public Order createOrder(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
        }

        order.calculateTotalPrice();
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}