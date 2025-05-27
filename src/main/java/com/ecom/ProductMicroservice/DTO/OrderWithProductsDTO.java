package com.ecom.ProductMicroservice.DTO;

import java.util.List;

import com.ecom.ProductMicroservice.Entity.OrderModel;
import com.ecom.ProductMicroservice.Entity.ProductModel;

import lombok.Data;

@Data
public class OrderWithProductsDTO {

	private OrderModel order;
    private List<ProductModel> products;
    
	public OrderWithProductsDTO() 
	{
		
	}
    
    
    
}
