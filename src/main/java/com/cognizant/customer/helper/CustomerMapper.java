package com.cognizant.customer.helper;

import org.springframework.stereotype.Component;

import com.cognizant.customer.domain.Address;
import com.cognizant.customer.domain.Customer;
import com.cognizant.customer.model.AddressDTO;
import com.cognizant.customer.model.CustomerDTO;

@Component
public class CustomerMapper {

	public CustomerDTO mapDomainToModel(Customer customer) {

		com.cognizant.customer.model.CustomerDTO mappedCustomer = new com.cognizant.customer.model.CustomerDTO();
		com.cognizant.customer.model.AddressDTO mappedAddress = new com.cognizant.customer.model.AddressDTO();
		mappedCustomer.setCustId(customer.getCustId());
		mappedCustomer.setFirstName(customer.getFirstName());
		mappedCustomer.setLastName(customer.getLastName());
		mappedCustomer.setAge(customer.getAge());
		
		mappedAddress.setCustAddressId(customer.getAddress().getCustAddressId());
		mappedAddress.setDoorNo(customer.getAddress().getDoorNo());
		mappedAddress.setStreet(customer.getAddress().getStreet());
		mappedAddress.setCity(customer.getAddress().getCity());
		mappedAddress.setCountry(customer.getAddress().getCountry());
		mappedAddress.setZipCode(customer.getAddress().getZipCode());
		mappedAddress.setCreatedDate(customer.getAddress().getCreatedDate());
		mappedAddress.setUpdatedDate(customer.getAddress().getUpdatedDate());

		mappedCustomer.setAddress(mappedAddress);
		mappedCustomer.setCreatedDate(customer.getCreatedDate());
		mappedCustomer.setUpdatedDate(customer.getUpdatedDate());
		return mappedCustomer;
	}

	public Customer mapModelToDomain(CustomerDTO customer) {

		Customer mappedCustomer = new Customer();
		Address mappedAddress = new Address();
		mappedCustomer.setCustId(customer.getCustId());
		mappedCustomer.setFirstName(customer.getFirstName());
		mappedCustomer.setLastName(customer.getLastName());
		mappedCustomer.setAge(customer.getAge());
		
		mappedAddress.setCustAddressId(customer.getAddress().getCustAddressId());
		mappedAddress.setDoorNo(customer.getAddress().getDoorNo());
		mappedAddress.setStreet(customer.getAddress().getStreet());
		mappedAddress.setCity(customer.getAddress().getCity());
		mappedAddress.setCountry(customer.getAddress().getCountry());
		mappedAddress.setZipCode(customer.getAddress().getZipCode());
		mappedAddress.setCreatedDate(customer.getAddress().getCreatedDate());
		mappedAddress.setUpdatedDate(customer.getAddress().getUpdatedDate());

		mappedCustomer.setAddress(mappedAddress);
		mappedCustomer.setCreatedDate(customer.getCreatedDate());
		mappedCustomer.setUpdatedDate(customer.getUpdatedDate());
		return mappedCustomer;
	}

	public CustomerDTO mapAddress(CustomerDTO toModel,
			Customer customer) {
		AddressDTO mappedAddress = new com.cognizant.customer.model.AddressDTO();

		mappedAddress.setCustAddressId(customer.getAddress().getCustAddressId());
		mappedAddress.setDoorNo(customer.getAddress().getDoorNo());
		mappedAddress.setStreet(customer.getAddress().getStreet());
		mappedAddress.setCity(customer.getAddress().getCity());
		mappedAddress.setCountry(customer.getAddress().getCountry());
		mappedAddress.setZipCode(customer.getAddress().getZipCode());
		mappedAddress.setCreatedDate(customer.getAddress().getCreatedDate());
		mappedAddress.setUpdatedDate(customer.getAddress().getUpdatedDate());
		
		toModel.setAddress(mappedAddress);
		return toModel;
	}
}
