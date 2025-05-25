package com.fix.OnlineClothingStore.Service;



import com.fix.OnlineClothingStore.Model.CoffeeItem;
import com.fix.OnlineClothingStore.Repo.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository repo;

    public ResponseEntity<CoffeeItem> getItem(int id){
        try {
            return new ResponseEntity<>(repo.findById(id).orElseThrow(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new CoffeeItem(), HttpStatus.BAD_REQUEST);
    }

    public void addItem(CoffeeItem coffeeItem){
        repo.save(coffeeItem);
    }

    public ResponseEntity<List<CoffeeItem>> getAllItems(List<String> types){
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
