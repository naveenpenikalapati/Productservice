package com.ecom.ProductMicroservice.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.ProductMicroservice.Entity.ProductModel;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel,Integer>
{

	int countByorderId(int orderid);
	
	Optional<ProductModel> findByOrderIdAndProductID(int orderId, int productID);

	List<ProductModel> findByOrderId(int orderId);

	List<ProductModel> findByCategory(String category);

	//List<ProductModel> saveAll(List<com.EcomOrderMicroservice.order.ProductModel> products);

	
	
	

}
