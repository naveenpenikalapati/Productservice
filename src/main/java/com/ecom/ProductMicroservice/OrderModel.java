package com.ecom.ProductMicroservice;

import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@jakarta.persistence.Entity
@Data
//@Builder
public class OrderModel
{
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int orderId;
      private String Customer;
      private int   orderQuantity;
      private long  mobno;
      
      @Transient
      private List<ProductModel> products;
      
//      @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//      @JsonManagedReference
//      private List<ProductModel> products;
//      
//      public static void main(String[] args) {
//		 
//    	  OrderModel order =  OrderModel.builder().orderId(123).Customer("a").build();
//    	  
//    	  System.out.println(order.getOrderId()+" "+order.getCustomer());
//	}
      
}
