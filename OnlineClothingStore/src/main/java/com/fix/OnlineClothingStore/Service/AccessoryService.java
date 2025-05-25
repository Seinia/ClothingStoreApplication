package com.fix.OnlineClothingStore.Service;

import com.fix.OnlineClothingStore.Model.AccessoryItem;
import com.fix.OnlineClothingStore.Repo.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessoryService {

    @Autowired
    private AccessoryRepository repo;

    public ResponseEntity<AccessoryItem> getItem(int id){
        try {
            return new ResponseEntity<>(repo.findById(id).orElseThrow(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new AccessoryItem(), HttpStatus.BAD_REQUEST);
    }

    public void addItem(AccessoryItem accessoryItem){
        repo.save(accessoryItem);
    }

    public ResponseEntity<List<AccessoryItem>> getAllItems(List<String> types){
        try {
            if (types == null || types.isEmpty()) {
                return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
            }
            return new ResponseEntity<>(repo.findByTypeIn(types), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
}