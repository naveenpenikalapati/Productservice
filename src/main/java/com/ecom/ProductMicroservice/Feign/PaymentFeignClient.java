package com.ecom.ProductMicroservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="paymentmicroservice")
public interface PaymentFeignClient 
{
	@PostMapping("/pay")
	public void paymentforOrder(@RequestParam int orderid,@RequestParam String mode);
	
}
