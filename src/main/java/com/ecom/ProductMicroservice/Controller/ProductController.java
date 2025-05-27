package com.ecom.ProductMicroservice.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ProductMicroservice.DTO.OrderUpdateDTO;
import com.ecom.ProductMicroservice.DTO.OrderWithProductsDTO;
import com.ecom.ProductMicroservice.DTO.ProductDTO;
import com.ecom.ProductMicroservice.Service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService ProductService;
	
	@PostMapping("/saveproducts")     //linked - method from ProductFeignClient of OrderMicroservice
	public ResponseEntity<String> saveProductsToOrder(@RequestBody List<ProductDTO> products)
	{
		ProductService.saveProductsToOrder(products);
		return ResponseEntity.ok("Products saved successfully");
	}
	
	@GetMapping("/products/byorder/{orderid}")    //linked - method from ProductFeignClient of OrderMicroservice
	public List<com.ecom.ProductMicroservice.Entity.ProductModel> getProductsForOrder(@PathVariable("orderid")int orderid)
	{
		return ProductService.getProductsForOrder(orderid);
	}
	
	 @GetMapping(value="/order-details/{orderId}")    //linked - method from ProductFeignClient of OrderMicroservice
	 public ResponseEntity<OrderWithProductsDTO> getOrderWithProducts(@PathVariable("orderId") int orderId) 
	 {
	     return ProductService.getOrderWithProducts(orderId);
	 }

	
	 @PutMapping("/update-by-order")     //linked - method from ProductFeignClient of OrderMicroservice
	    public ResponseEntity<String> updateProductFromOrder(@RequestBody OrderUpdateDTO dto) 
	    {
		 System.out.println("Received DTO: " + dto.getProductID()+" in product controller");

	        return ProductService.updateProductFromOrder(dto);
	    }

	 
	// Remove a Product from an Order
	@DeleteMapping("/{id}/deleteproduct")
	public String deleteProductFromOrder(@PathVariable("id") int productID)
	{
		return ProductService.deleteProductFromOrder(productID);
	}
	
	// Search Orders by Product Category
	@GetMapping("/search")
	public List<com.ecom.ProductMicroservice.Entity.ProductModel> searchProducts(@RequestParam("category") String category)
	{
		return ProductService.searchProducts(category);
	}
	

}
