package com.ecom.ProductMicroservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepo extends JpaRepository<OrderModel,Integer>
{

	OrderModel findByOrderQuantity(int orderQuantity);
	
	 @Query("SELECT o FROM OrderModel o WHERE o.orderQuantity = (SELECT MAX(o2.orderQuantity) FROM OrderModel o2)")
	  OrderModel findByHighOrders();
	 
//	 @Query("SELECT DISTINCT o FROM OrderModel o JOIN o.products p WHERE p.category = :category")
//		List<OrderModel> findByProductsCategory(@Param("category") String category);
//

	//String deleteAllById(List<Integer> orderid);

}
