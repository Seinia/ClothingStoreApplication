package com.fix.OnlineClothingStore.Repo;



import com.fix.OnlineClothingStore.Model.CoffeeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<CoffeeItem, Integer> {

    List<CoffeeItem> findByTypeIn(List<String> types);
}
