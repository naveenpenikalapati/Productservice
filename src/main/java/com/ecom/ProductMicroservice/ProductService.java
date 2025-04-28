package com.ecom.ProductMicroservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.EcomOrderMicroservice.order.OrderUpdateDTO;

@Service
public class ProductService 
{
	@Autowired
	private OrderFeignClient OrderFeignClient;

	@Autowired
	private ProductRepo productrepo;

	public List<ProductModel> saveProductsToOrder(List<ProductDTO> products) // linked
	{
		System.out.println(" product before saving : " + products.size());
		List<ProductModel> products2 = products.stream().map(dto -> {
			ProductModel model = new ProductModel();
			model.setProductID(dto.getProductID());
			model.setCategory(dto.getCategory());
			model.setAmount(dto.getAmount());
			model.setOrderId(dto.getOrderId());
			return model;
		}).collect(Collectors.toList());

		List<ProductModel> savedproducts = productrepo.saveAll(products2);
		System.out.println("products after saving : " + savedproducts.size());

		if (!savedproducts.isEmpty()) {
			int orderid = savedproducts.get(0).getOrderId();
			int orderquantity = productrepo.countByorderId(orderid);

			System.out
					.println("Calling OrderService to update quantity" + orderid + " with Quantity: " + orderquantity);
			try {
				OrderFeignClient.updateOrderQuantity(orderid, orderquantity);  // method in orderfeignclient
			} catch (Exception e) {
				System.out.println("Failed to call OrderService: " + e.getMessage());
			}
			System.out.println("Successfully called OrderService");
		}
		return savedproducts;
	}

	public List<ProductModel> getProductsForOrder(int orderid) // linked
	{
		return productrepo.findByOrderId(orderid);
	}

	public ResponseEntity<OrderWithProductsDTO> getOrderWithProducts(Integer orderId) { // linked
		List<ProductModel> products = productrepo.findByOrderId(orderId);

		// Use OrderFeignClient to call OrderService
		OrderModel order = OrderFeignClient.getOrderById(orderId);       // method in orderfeignclient

		OrderWithProductsDTO dto = new OrderWithProductsDTO();
		dto.setOrder(order);
		dto.setProducts(products);

		return ResponseEntity.ok(dto);
	}

	public ResponseEntity<String> updateProductFromOrder(OrderUpdateDTO dto)     // linked
			{
		                                                                      // method in productrepo
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
			        return ResponseEntity.status(HttpStatus.NOT_FOUND)
			                .body("Product not found for Order ID: " + dto.getOrderId() + " and Product ID: " + dto.getProductID());
			    }
			}

	public String deleteProductFromOrder(int productID) 
	{
		ProductModel  product  =  productrepo.findById(productID)       // method in productrepo
		                        .orElseThrow(() -> new RuntimeException("product not found with ID: " + productID));
		
		int  orderids  =  product.getOrderId();
		
		productrepo.deleteById(productID);
		                 
	    int remainingproducts   =  productrepo.countByorderId(orderids);     // method in productrepo
		
		OrderFeignClient.updateOrderQuantity(orderids ,remainingproducts);    // method in orderfeignclient
		
		return "product deleted & order quantity updated";
	}

		public List<ProductModel> searchProducts( String category) 
		{
			return productrepo.findByCategory(category);             // method in productrepo
		}

}
