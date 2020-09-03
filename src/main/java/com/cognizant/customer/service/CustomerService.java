package com.cognizant.customer.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.customer.domain.Customer;
import com.cognizant.customer.exception.ResourceNotFoundException;
import com.cognizant.customer.helper.CustomerMapper;
import com.cognizant.customer.model.CustomerDTO;
import com.cognizant.customer.repository.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerService {

	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	CustomerRepository customerRepository;

	/**
	 * creating customer details and his address
	 * 
	 * @param customer
	 * @return
	 */
	public Customer createCustomer(Customer customer) {
		Date currentDate = new Date();
		Timestamp currentTimestamp = new Timestamp(currentDate.getTime());

		customer.setCreatedDate(currentTimestamp);
		customer.setUpdatedDate(currentTimestamp);
		customer.getAddress().setCreatedDate(currentTimestamp);
		customer.getAddress().setUpdatedDate(currentTimestamp);

		log.info("Started creating customer");
		CustomerDTO toModel = customerMapper.mapDomainToModel(customer);
		customer = customerMapper.mapModelToDomain(customerRepository.save(toModel));		
		log.info("Successfully created a customer with customer id :"+customer.getCustId());
		return customer;
	}

	/**
	 * Listing all Customer details
	 * 
	 * @return
	 */
	public List<Customer> getAllCustomer() {

		List<CustomerDTO> procuredCustomers = customerRepository.getAllCustomers();
		List<Customer> customers = new ArrayList<Customer>();

		for(CustomerDTO procuredCustomer : procuredCustomers) {
			Customer customer = customerMapper.mapModelToDomain(procuredCustomer);
			customers.add(customer);
		}
		return customers;
	}

	/**
	 * Listing all customers by their first or last name
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public List<Customer> getAllCustomerByName(String firstName, String lastName) {
		List<com.cognizant.customer.model.CustomerDTO> procuredCustomers = customerRepository.getAllCustomerByName(firstName, lastName);
		List<Customer> customers = new ArrayList<Customer>();

		for(com.cognizant.customer.model.CustomerDTO procuredCustomer : procuredCustomers) {
			Customer customer = customerMapper.mapModelToDomain(procuredCustomer);
			customers.add(customer);
		}
		return customers;
	}

	/**
	 * Get customer by customer id
	 * 
	 * @param custId
	 * @return
	 */
	public Customer getCustomerById(long custId) {
		CustomerDTO toModel = customerRepository.getCustomerById(custId);
		return customerMapper.mapModelToDomain(toModel);
	}

	/**
	 * update customer details
	 * @param customer 
	 * 
	 * @param custId
	 * @return
	 * @throws ResourceNotFoundException 
	 */

	public Customer updateLivingAddress(Customer customer) throws ResourceNotFoundException {
		com.cognizant.customer.model.CustomerDTO toModel = customerRepository.getCustomerById(customer.getCustId());
		Date currentDate = new Date();
		Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
		if(toModel!=null) {
			com.cognizant.customer.model.CustomerDTO updatedCustomer= customerMapper.mapAddress(toModel, customer);

			updatedCustomer.setUpdatedDate(currentTimestamp);
			updatedCustomer.getAddress().setUpdatedDate(currentTimestamp);
			log.info("Updating customer living address");
			toModel = customerRepository.updateLivingAddress(updatedCustomer);
			log.info("Successfully updating customer living address");
			return customerMapper.mapModelToDomain(toModel);
		} else {
			log.error("Cannot update customer address");
			throw new ResourceNotFoundException("could not update customer with id "+customer.getCustId());
		}		
	}
}
