package com.ecom.ProductMicroservice.KafkaEvents;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.ecom.ProductMicroservice.DTO.ProductDTO;
import com.ecom.ProductMicroservice.Entity.ProductModel;
import com.ecom.ProductMicroservice.Repository.ProductRepo;

@Component
public class ProductsAddedEventConsumer
{
	@Autowired
	private ProductRepo productrepo;
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(ProductsAddedEventConsumer.class);
	
	@KafkaListener(topics = "order-product-events-test", groupId = "product-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleProductsAddedEvent(ProductsAddedEvent event) 
	{
       logger.info("started in productsadded event consumer in product microservice");

        for (ProductDTO dto : event.getProducts()) 
        {
            ProductModel product = new ProductModel();
            product.setProductID(dto.getProductID());
            product.setCategory(dto.getCategory());
            product.setAmount(dto.getAmount());
            product.setOrderId(event.getOrderId());

            productrepo.save(product);         // Save to DB
        }
        logger.info("ended by saving products to database -product microservice ");
       
    }
	
	
}