package com.fix.OnlineClothingStore.Controller;

import com.fix.OnlineClothingStore.Model.AccessoryItem;
import com.fix.OnlineClothingStore.Service.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products/accessory")
@CrossOrigin(origins = "http://localhost:5173")
public class AccessoryController {

    @Autowired
    AccessoryService service;

    @GetMapping("getItem")
    public ResponseEntity<AccessoryItem> getItem(@RequestParam(name = "id", required = true) int id){
        return service.getItem(id);
    }

    @GetMapping("getAllItems")
    public ResponseEntity<List<AccessoryItem>> getAllItems(@RequestParam(name = "types", required = false) List<String> types){
        return service.getAllItems(types);
    }

    @PostMapping(path = "postItem", consumes = {"application/xml", "application/JSON"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AccessoryItem> postItem(@RequestBody AccessoryItem accessoryItem){
        service.addItem(accessoryItem);
        return service.getItem(accessoryItem.getId());
    }
}