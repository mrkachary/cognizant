package com.cognizant.customer.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ADDRESS")
@Data
public class AddressDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="CUST_ADDRS_ID")
	private long custAddressId;
	
	@Column(name="DOOR_NO")
	private String doorNo;
	
	@Column(name="STREET")
	private String street;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="ZIP_CODE")
	private String zipCode;
	
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;
	
}
