package com.ecom.ProductMicroservice.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.ProductMicroservice.DTO.OrderUpdateDTO;
import com.ecom.ProductMicroservice.DTO.OrderWithProductsDTO;
import com.ecom.ProductMicroservice.DTO.ProductDTO;
import com.ecom.ProductMicroservice.Entity.OrderModel;
import com.ecom.ProductMicroservice.Entity.ProductModel;
import com.ecom.ProductMicroservice.Feign.OrderFeignClient;
import com.ecom.ProductMicroservice.Repository.ProductRepo;

@Service
public class ProductService 
{
	@Autowired
	private OrderFeignClient OrderFeignClient;
	@Autowired
	private ProductRepo productrepo;
//	@Autowired
//	private com.ecom.ProductMicroservice.Feign.PaymentFeignClient PaymentFeignClient ;

	@Transactional
public List<ProductModel> saveProductsToOrder(List<ProductDTO> products) 
{
List<ProductModel>productModels = products.stream()
	                                      .map(dto -> new ProductModel(dto.getProductID(), dto.getCategory(), dto.getAmount(), dto.getOrderId()))
	                                      .collect(Collectors.toList());

List<ProductModel> savedProducts = productrepo.saveAll(productModels);
	    if (!savedProducts.isEmpty()) 
	    {
	        int orderId = savedProducts.get(0).getOrderId();
	        int orderQuantity = productrepo.countByorderId(orderId);
	        
	        try {
	            OrderFeignClient.updateOrderQuantity(orderId, orderQuantity);
	          //  PaymentFeignClient.paymentforOrder(orderId, "cash");
	            System.out.println("Successfully called both services");
	        } catch (Exception e) {
	            System.out.println("Failed to call a service: " + e.getMessage());
	        }
	    }
	    return savedProducts;
	}

	public ResponseEntity<OrderWithProductsDTO> getOrderWithProducts(Integer orderId)      // linked
	{
		List<ProductModel> products = productrepo.findByOrderId(orderId);
		// Use OrderFeignClient to call OrderService
		OrderModel order = OrderFeignClient.getOrderById(orderId);                  // method in orderfeignclient

		OrderWithProductsDTO dto = new OrderWithProductsDTO();
		dto.setOrder(order);
		dto.setProducts(products);
		return ResponseEntity.ok(dto);
	}
	@Transactional
	public ResponseEntity<String> updateProductFromOrder(OrderUpdateDTO dto)     // linked
	{                                                                  // method in productrepo
	 Optional<ProductModel> product = productrepo.findByOrderIdAndProductID(dto.getOrderId(), dto.getProductID());   
		 if(product.isPresent())
			{
				   ProductModel product1  =  product.get();
				   product1.setCategory(dto.getUpdatedCategory());
				   product1.setAmount(dto.getNewAmount());
				   productrepo.save(product1);
				   return ResponseEntity.ok("Product updated successfully");
			   }
			   else {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for Order ID: " + dto.getOrderId() + " and Product ID: " + dto.getProductID());
			    }
			}
	@Transactional
	public String deleteProductFromOrder(int productId) 
	{
	    int orderId = productrepo.findById(productId).map(ProductModel::getOrderId)
	                                                 .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
	    productrepo.deleteById(productId);
	    int remainingProducts = productrepo.countByorderId(orderId);
	    OrderFeignClient.updateOrderQuantity(orderId, remainingProducts);
	    return "product deleted & order quantity updated";
	}

	public List<ProductModel> getProductsForOrder(int orderid) // linked
	{
		return productrepo.findByOrderId(orderid);
	}

	public List<ProductModel> searchProducts( String category) 
	{
		return productrepo.findByCategory(category);             // method in productrepo
	}

}
