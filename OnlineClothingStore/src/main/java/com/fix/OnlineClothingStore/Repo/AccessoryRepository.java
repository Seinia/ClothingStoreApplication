package com.fix.OnlineClothingStore.Repo;



import com.fix.OnlineClothingStore.Model.AccessoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<AccessoryItem, Integer> {

    List<AccessoryItem> findByTypeIn(List<String> types);
}
