package com.fix.OnlineClothingStore.Controller;



import com.fix.OnlineClothingStore.Model.Item;
import com.fix.OnlineClothingStore.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    @Autowired
    ItemService service;

    @GetMapping("getItem")
    public ResponseEntity<Item> getItem(@RequestParam(name = "id", required = true) int id){
        return service.getItem(id);
    }

    @GetMapping("getAllItems")
    public ResponseEntity<List<Item>> getAllItems(){
        return service.getAllItems();
    }

    @GetMapping("getItemsByCategory")
    public ResponseEntity<List<Item>> getItemsByCategory(@RequestParam(name = "category", required = true) String category){
        return service.getItemsByCategory(category);
    }

    @PostMapping(path = "postItem", consumes = {"application/xml", "application/JSON"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Item> postItem(@RequestBody Item item){
        service.addItem(item);
        return service.getItem(item.getId());
    }
}
