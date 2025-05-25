package com.fix.OnlineClothingStore.Repo;

import com.fix.OnlineClothingStore.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}

