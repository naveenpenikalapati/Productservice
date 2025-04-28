
package com.ecom.ProductMicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="orderdetails")
public interface OrderFeignClient       
{
	@PutMapping("/order/orderquantity/{orderid}")
	 public void updateOrderQuantity(@PathVariable("orderid") int orderid,@RequestParam int orderquantity);
	
	 @GetMapping("/gettingorder/{orderId}")
	 public OrderModel getOrderById(@PathVariable("orderId") int orderId);
	 
}	 
