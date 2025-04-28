package com.ecom.ProductMicroservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
public class ProductModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private int productID;
    private String category;
    private int amount;
    private int orderId;
    @Version
    private int version;

    // Direct reference to the order ID (no FK constraint)
//    @Column(name = "order_id", nullable = false)
//    private int orderId;
}
