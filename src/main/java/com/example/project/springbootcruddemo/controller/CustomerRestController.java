package com.example.project.springbootcruddemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.springbootcruddemo.entity.Customer;
import com.example.project.springbootcruddemo.repository.CustomerRepository;

@RestController
@RequestMapping("/v1/api/customer")
public class CustomerRestController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping(value="/listAllCustomers")
	public Iterable<Customer> listAllCustomers() {
		
		return customerRepository.findAll();
	}
	
	@GetMapping(value="/listAllCustomersPageWise")
	public Iterable<Customer> getAllCustomersPageWise(@RequestParam(name="page", defaultValue="1") Integer pageNumber, 
		@RequestParam(name="size", defaultValue="10") Integer pageSize) {
		Pageable pagable = PageRequest.of(pageNumber-1, pageSize);
		return customerRepository.findAll(pagable).getContent();
	}
	
	/*@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable Integer id) {
		return customerRepository.findById(id).get();
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
		try {
			Customer c1 = customerRepository.findById(id).get();
			return ResponseEntity.ok(c1);
		} catch(Exception ex) {
			return ResponseEntity.status(404).body("Customer ID doesnot exist");
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addNewCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
		return ResponseEntity.ok(customer);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer) {
		customer.setId(id);
		customerRepository.save(customer);
		return ResponseEntity.ok(customer);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Integer id) {
		try {
			Customer c1 = customerRepository.findById(id).get();
			customerRepository.delete(c1);
			return ResponseEntity.ok(c1);
		} catch(Exception ex) {
			return ResponseEntity.status(404).body(null);
		}
	}
}
