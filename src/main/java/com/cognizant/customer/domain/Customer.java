package com.cognizant.customer.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Customer {

	@JsonProperty("cust_id")
	private long custId;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("cust_address")
	private Address address;

	@JsonProperty("created_date")
	private Timestamp createdDate;

	@JsonProperty("updated_date")
	private Timestamp updatedDate;
}

