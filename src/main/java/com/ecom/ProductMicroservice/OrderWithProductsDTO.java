package com.ecom.ProductMicroservice;

import java.util.List;

import lombok.Data;

@Data
public class OrderWithProductsDTO {

	private OrderModel order;
    private List<ProductModel> products;
    
	public OrderWithProductsDTO() 
	{
		
	}
    
    
    
}
