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

import lombok.extern.log4j.Log4j2;
@Log4j2
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
	 * @throws InvalidDataException 
	 */
	@PostMapping("/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws InvalidDataException {
		if(customer.getFirstName()==null || customer.getFirstName().isEmpty()) {
			log.debug("First name is missing");
			throw new InvalidDataException("first name is missing");
		}
		if(customer.getLastName()  == null || customer.getLastName().isEmpty() ) {
			log.debug("Last name is missing");
			throw new InvalidDataException("Last name is missing");
		}
		if(customer.getAddress()==null) {
			log.debug("Address is missing");
			throw new InvalidDataException("Address is missing");
		}
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
	@GetMapping("/cust-id/{cust-id}") 
	public ResponseEntity<Customer> getCustomerById(@PathVariable("cust-id") long custId) {
		Customer customer = customerService.getCustomerById(custId);
		if(customer==null) {
			log.debug("Customer with customer id "+custId+" does not exists");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		log.info("Customer with customer id "+custId+" does not exists");
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
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
			log.debug("Missing customer id ");
			throw new InvalidDataException("customer id is required");
		}
		if(customer.getAddress()==null) {
			log.debug("Address must be required");
			throw new InvalidDataException("Address is required required");
		}
		Customer customers = customerService.updateLivingAddress(customer);
		
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(customers);
	}	
}
