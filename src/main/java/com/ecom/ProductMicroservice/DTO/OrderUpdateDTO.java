package com.ecom.ProductMicroservice.DTO;


public class OrderUpdateDTO 
{
	private int orderId;
	private int productID;
    private String updatedCategory;
    private int newAmount;
    
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getUpdatedCategory() {
		return updatedCategory;
	}
	public void setUpdatedCategory(String updatedCategory) {
		this.updatedCategory = updatedCategory;
	}
	public int getNewAmount() {
		return newAmount;
	}
	public void setNewAmount(int newAmount) {
		this.newAmount = newAmount;
	}
    
	
   
    
    
    
}
