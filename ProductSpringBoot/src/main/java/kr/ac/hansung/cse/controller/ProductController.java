package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.repo.ProductRepository;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	ProductRepository repository;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllCustomers() {
		List<Product> customers = new ArrayList<>();
		try {
			repository.findAll().forEach(customers::add);

			if (customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getCustomerById(@PathVariable("id") long id) {
		Optional<Product> customerData = repository.findById(id);

		if (customerData.isPresent()) {
			return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/products/category")
	public ResponseEntity<List<Product>> getAllCategory() {
		List<Product> customers = new ArrayList<>();
		try {
			repository.findAll().forEach(customers::add);

			if (customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @GetMapping(value = "products/category/{category}") public
	 * ResponseEntity<Product> getByCategory(@PathVariable("category") String
	 * category) { Optional<Product> customerData =
	 * repository.findByCategory(category); if (customerData.isPresent()) { return
	 * new ResponseEntity<>(customerData.get(), HttpStatus.OK); } else { return new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND); } }
	 */

	@GetMapping(value = "products/category/{category}")
	public ResponseEntity<List<Product>> findByCategory(@PathVariable String category) {
		try {
			List<Product> customers = repository.findByCategory(category);

			if (customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping(value = "/products")
	public ResponseEntity<Product> postCustomer(@RequestBody Product customer) {
		try {
			Product _customer = repository
					.save(new Product(customer.getId(), customer.getName(), customer.getCategory(), customer.getPrice(),
							customer.getManufacturer(), customer.getUnitInStock(), customer.getDescription()));
			return new ResponseEntity<>(_customer, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/products")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	/*
	 * @GetMapping(value = "customers/age/{age}") public
	 * ResponseEntity<List<Customer>> findByAge(@PathVariable int age) { try {
	 * List<Customer> customers = repository.findByAge(age);
	 * 
	 * if (customers.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); } return new
	 * ResponseEntity<>(customers, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED); } }
	 */

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateCustomer(@PathVariable("id") long id, @RequestBody Product customer) {
		Optional<Product> customerData = repository.findById(id);

		if (customerData.isPresent()) {
			Product _customer = customerData.get();

			_customer.setId(customer.getId());
			_customer.setName(customer.getName());
			_customer.setCategory(customer.getCategory());
			_customer.setPrice(customer.getPrice());
			_customer.setManufacturer(customer.getManufacturer());
			_customer.setUnitInStock(customer.getUnitInStock());
			_customer.setDescription(customer.getDescription());
			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}