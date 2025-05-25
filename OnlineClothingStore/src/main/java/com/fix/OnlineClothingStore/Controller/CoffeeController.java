package com.fix.OnlineClothingStore.Controller;



import com.fix.OnlineClothingStore.Model.CoffeeItem;
import com.fix.OnlineClothingStore.Service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products/coffee")
@CrossOrigin(origins = "http://localhost:5173")
public class CoffeeController {

    @Autowired
    CoffeeService service;

    @GetMapping("getItem")
    public ResponseEntity<CoffeeItem> getItem(@RequestParam(name = "id", required = true) int id){
        return service.getItem(id);
    }

    @GetMapping("getAllItems")
    public ResponseEntity<List<CoffeeItem>> getAllItems(@RequestParam(name = "types", required = false) List<String> types){
        return service.getAllItems(types);
    }

    @PostMapping(path = "postItem", consumes = {"application/xml", "application/JSON"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CoffeeItem> postItem(@RequestBody CoffeeItem coffeeItem){
        service.addItem(coffeeItem);
        return service.getItem(coffeeItem.getId());
    }
}
