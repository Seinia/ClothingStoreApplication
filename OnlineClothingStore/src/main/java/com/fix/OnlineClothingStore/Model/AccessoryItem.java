package com.fix.OnlineClothingStore.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Accessories")
public class AccessoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private float price;
    @Column(name = "imageURL")
    private String imageURL;
}
