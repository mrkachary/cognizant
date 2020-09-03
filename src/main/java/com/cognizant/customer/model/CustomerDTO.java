package com.cognizant.customer.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity(name="CUSTOMER")
@NamedQueries(value = 
		{
				@NamedQuery(
				name="findAllCustomers",
				query="SELECT c FROM com.cognizant.customer.model.CustomerDTO c LEFT JOIN com.cognizant.customer.model.AddressDTO a ON c.custAddressId = a.custAddressId "
				),
				@NamedQuery(
				name="findAllCustomersByFirstame",
				query="SELECT c FROM com.cognizant.customer.model.CustomerDTO c LEFT JOIN com.cognizant.customer.model.AddressDTO a ON c.custAddressId = a.custAddressId WHERE c.firstName LIKE :firstName"
				),
				@NamedQuery(
				name="findAllCustomersByLastname",
				query="SELECT c FROM com.cognizant.customer.model.CustomerDTO c LEFT JOIN com.cognizant.customer.model.AddressDTO a ON c.custAddressId = a.custAddressId WHERE c.lastName LIKE :lastName"
				),
				@NamedQuery(
				name="findAllCustomersByFirstnameAndLastname",
				query="SELECT c FROM com.cognizant.customer.model.CustomerDTO c LEFT JOIN com.cognizant.customer.model.AddressDTO a ON c.custAddressId = a.custAddressId WHERE c.firstName LIKE :firstName AND c.lastName LIKE :lastName"
				)

})
@Table
@Data
public class CustomerDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="CUST_ID")
	private long custId;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="AGE")
	private int age;
	
	@Column(name="CUST_ADDRS_ID")
	private long custAddressId;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;
	
	@OneToOne
	private AddressDTO address;

}

