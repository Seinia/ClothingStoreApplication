package com.fix.OnlineClothingStore.Service;



import com.fix.OnlineClothingStore.Model.Item;
import com.fix.OnlineClothingStore.Repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repo;

    public ResponseEntity<Item> getItem(int id){
        try {
            return new ResponseEntity<>(repo.findById(id).orElseThrow(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Item(), HttpStatus.BAD_REQUEST);
    }

    public void addItem(Item item){
        repo.save(item);
    }

    public ResponseEntity<List<Item>> getAllItems(){
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Item>> getItemsByCategory(String category){
        try {
            return new ResponseEntity<>(repo.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

}
