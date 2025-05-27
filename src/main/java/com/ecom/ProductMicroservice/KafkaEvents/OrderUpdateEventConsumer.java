package com.ecom.ProductMicroservice.KafkaEvents;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecom.ProductMicroservice.DTO.OrderUpdateDTO;
import com.ecom.ProductMicroservice.Entity.ProductModel;
import com.ecom.ProductMicroservice.Repository.ProductRepo;

@Component
public class OrderUpdateEventConsumer {

	@Autowired
	private ProductRepo productrepo;
//	
//	 @KafkaListener(topics = "order-update-topic", groupId = "product-order-update-group", containerFactory = "kafkaListenerContainerFactory")
//	 public void updateProductFromOrder(OrderUpdateDTO dto)        // linked
//		{                  
//		 System.out.println("Received OrderUpdateDTO via Kafka for Order ID: " + dto.getOrderId());
//                                                                        
//		 Optional<ProductModel> product = productrepo.findByOrderIdAndProductID(dto.getOrderId(), dto.getProductID());   
//			 if(product.isPresent())
//				{
//					   ProductModel product1  =  product.get();
//					   product1.setCategory(dto.getUpdatedCategory());
//					   product1.setAmount(dto.getNewAmount());
//					   productrepo.save(product1);
//				   }		
}

