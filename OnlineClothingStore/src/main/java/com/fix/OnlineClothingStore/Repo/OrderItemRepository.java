package com.fix.OnlineClothingStore.Repo;

import com.fix.OnlineClothingStore.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}