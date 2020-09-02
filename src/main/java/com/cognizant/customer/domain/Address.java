package com.cognizant.customer.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Address {
	
	@JsonProperty("cust_addrs_id")
	private long custAddressId;
	
	@JsonProperty("door_no")
	private String doorNo;
	
	@JsonProperty("street")
	private String street;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("zip_code")
	private String zipCode;
	
	@JsonProperty("created_date")
	private Timestamp createdDate;
	
	@JsonProperty("updated_date")
	private Timestamp updatedDate;
	
}
