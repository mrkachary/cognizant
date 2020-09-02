package com.cognizant.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.customer.domain.Customer;
import com.cognizant.customer.exception.InvalidDataException;
import com.cognizant.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerDetailController {

	@Autowired 
	CustomerService customerService; 
	
	/**
	 * Retrieve a new customer
	 * 
	 * @param customer
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		
		Customer savedCustomer= customerService.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
	}
	
	/**
	 * Retrieve all customer
	 * @return
	 */
	@GetMapping("/all") 
	public ResponseEntity<List<Customer>> getAllCustomer() {
		List<Customer> customers = customerService.getAllCustomer();
		return ResponseEntity.ok().body(customers);
	}
	
	/**
	 * retrieve by first or last name
	 * 
	 * @param firstname
	 * @param lastname
	 * @return
	 */
	@GetMapping("/searchbyname") 
	public ResponseEntity<List<Customer>> getByNameCustomer(@RequestParam(value="firstname",required = false) String firstname, @RequestParam(value="lastname",required = false) String lastname) {
		List<Customer> customers = customerService.getAllCustomerByName(firstname, lastname);
		return ResponseEntity.ok().body(customers);
	}
	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	@GetMapping("/custId/{custId}") 
	public ResponseEntity<Customer> getCustomerById(@PathVariable("custId") Long custId) {
		Customer customers = customerService.getCustomerById(custId);
		return ResponseEntity.ok().body(customers);
	}
	
	/**
	 * 
	 * @param customer
	 * @param custId
	 * @return
	 * @throws InvalidDataException 
	 */
	@PutMapping("/cust-id") 
	public ResponseEntity<Customer> updateCustomerDetails(@RequestBody Customer customer) throws InvalidDataException {
		if(customer.getCustId()==0L) {
			throw new InvalidDataException("customer id is required");
		}
		Customer customers = customerService.updateLivingAddress(customer);
		
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(customers);
	}
}
