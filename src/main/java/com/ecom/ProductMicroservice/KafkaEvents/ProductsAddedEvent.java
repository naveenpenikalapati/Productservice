package com.ecom.ProductMicroservice.KafkaEvents;

import java.util.List;

import com.ecom.ProductMicroservice.DTO.ProductDTO;

public class ProductsAddedEvent 
{
	private int orderId;
    private List<ProductDTO> products;
    
	public ProductsAddedEvent(int orderId, List<ProductDTO> products) 
	{
		this.orderId = orderId;
		this.products = products;
	}

	public ProductsAddedEvent() {
		super();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

}
