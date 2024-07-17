package com.fix.OnlineClothingStore.Repo;



import com.fix.OnlineClothingStore.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByCategory(String category);
}
