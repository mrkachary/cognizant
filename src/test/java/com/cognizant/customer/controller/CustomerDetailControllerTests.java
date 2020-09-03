package com.cognizant.customer.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.customer.domain.Address;
import com.cognizant.customer.domain.Customer;
import com.cognizant.customer.exception.ResourceNotFoundException;
import com.cognizant.customer.helper.TestUtils;
import com.cognizant.customer.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = CustomerDetailController.class)
@WebAppConfiguration
public class CustomerDetailControllerTests{

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	Address mockAddress = populateAddress();
	Customer mockCustomer = populateCustomer(mockAddress);
	List<Customer> mockCustomerList = populateCustomerList();

	String customerJson = "{\"first_name\":\"Brian\",\"last_name\":\"Hamphire\",\"age\":45,\"cust_address\":{\"door_no\":\"237-1\",\"street\":\"Briadale St\",\"city\":\"New York\",\"country\":\"United States\",\"zip_code\":\"05601\"}} ";
	String customerWithoutFirstnameJson = "{\"first_name\":\"\",\"last_name\":\"Hamphire\",\"age\":45,\"cust_address\":{\"door_no\":\"237-1\",\"street\":\"Briadale St\",\"city\":\"New York\",\"country\":\"United States\",\"zip_code\":\"05601\"}} ";
	String customerWithoutLastnameJson = "{\"first_name\":\"Brian\",\"last_name\":\"\",\"age\":45,\"cust_address\":{\"door_no\":\"237-1\",\"street\":\"Briadale St\",\"city\":\"New York\",\"country\":\"United States\",\"zip_code\":\"05601\"}} ";
	String customerWithChangedAddressJson = "{\"cust_id\":1,\"first_name\":\"Brian\",\"last_name\":\"Hamphire\",\"age\":45,\"cust_address\":{\"door_no\":\"444-1\",\"street\":\"Briadale St\",\"city\":\"New Orlean\",\"country\":\"United States\",\"zip_code\":\"05601\"}} ";
	String customerWithoutAddressJson = "{\"first_name\":\"Brian\",\"last_name\":\"Hamphire\",\"age\":45} ";
	String customerWithNullAddressJson = "{\"cust_id\":1,\"first_name\":\"Brian\",\"last_name\":\"Hamphire\",\"age\":45,\"cust_address\":null} ";
	String customerWithNoCustIdJson = "{\"first_name\":\"Brian\",\"last_name\":\"Hamphire\",\"age\":45,\"cust_address\":{\"door_no\":\"237-1\",\"street\":\"Briadale St\",\"city\":\"New York\",\"country\":\"United States\",\"zip_code\":\"05601\"}} ";
	String customerWithChangedAddressWithWrongCustIdJson = "{\"cust_id\":999999,\"first_name\":\"Brian\",\"last_name\":\"Hamphire\",\"age\":45,\"cust_address\":\"\"} ";
	
	@Test
	public void retrieveAllCustomer() throws Exception {
		String uri = "/customer/all";
		Mockito.when(customerService.getAllCustomer()).thenReturn(mockCustomerList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
		verify(customerService).getAllCustomer();
	}


	@Test
	public void retrieveCustomerById() throws Exception {
		String uri = "/customer/cust-id/1";
		Mockito.when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(mockCustomer);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
		verify(customerService).getCustomerById(Mockito.anyLong());

		String expected = "{\"cust_id\":0,\"first_name\":\"Brian\",\"last_name\":\"Hamphire\",\"cust_address\":{\"cust_addrs_id\":0,\"door_no\":\"237-1\",\"street\":\"Briadale St\",\"city\":\"New York\",\"country\":\"United States\",\"zip_code\":\"05601\",\"created_date\":null,\"updated_date\":null},\"created_date\":null,\"updated_date\":null}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void retrieveCustomerByIdNotExist() throws Exception {
		String uri = "/customer/cust-id/2";
		Mockito.when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", 
				HttpStatus.NOT_FOUND.value(), status);

		verify(customerService).getCustomerById(Mockito.anyLong());


		Customer customer = TestUtils.jsonToObject(result.getResponse()
				.getContentAsString(), Customer.class); assertNull(customer);

	}


	@Test
	public void createCustomer() throws Exception {
		Mockito.when(customerService.createCustomer(Mockito.any())).thenReturn(mockCustomer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
				.accept(MediaType.APPLICATION_JSON).content(customerJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(customerService).createCustomer(Mockito.any());
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}


	@Test
	public void createCustomerWithoutFirstname() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
				.accept(MediaType.APPLICATION_JSON).content(customerWithoutFirstnameJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(customerService, never()).createCustomer(Mockito.any());
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void createCustomerWithoutLastname() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
				.accept(MediaType.APPLICATION_JSON).content(customerWithoutLastnameJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void createCustomerWithoutAddress() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
				.accept(MediaType.APPLICATION_JSON).content(customerWithoutAddressJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(customerService, never()).updateLivingAddress(Mockito.any());
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void updateCustomer() throws Exception {
		mockAddress.setDoorNo("444-1");
		mockAddress.setCity("New Orlean");
		mockCustomer.setAddress(mockAddress);

		Mockito.when(customerService.updateLivingAddress(mockCustomer)).thenReturn(mockCustomer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/cust-id")
				.accept(MediaType.APPLICATION_JSON).content(customerWithChangedAddressJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.PARTIAL_CONTENT.value(), response.getStatus());
	}


	@Test
	public void updateCustomerWithNoAddress() throws Exception {
		mockCustomer.setAddress(null);

		Mockito.when(customerService.updateLivingAddress(mockCustomer)).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/cust-id")
				.accept(MediaType.APPLICATION_JSON).content(customerWithNullAddressJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void createCustomerWithNoCustId() throws Exception {
		Mockito.when(customerService.updateLivingAddress(Mockito.any())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/cust-id")
				.accept(MediaType.APPLICATION_JSON).content(customerWithNoCustIdJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}	

	@Test
	public void updateCustomerWithCustIdNotFound() throws Exception {
		mockAddress.setDoorNo("444-1");
		mockAddress.setCity("New Orlean");
		mockCustomer.setAddress(mockAddress);

		Mockito.when(customerService.updateLivingAddress(mockCustomer)).thenThrow(ResourceNotFoundException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/cust-id")
				.accept(MediaType.APPLICATION_JSON).content(customerWithChangedAddressWithWrongCustIdJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		verify(customerService, never()).updateLivingAddress(Mockito.any());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	public void searchCustomerByfirstName() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/searchbyname?firstname={firstname}","Brian");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		verify(customerService).getAllCustomerByName(Mockito.anyString(), Mockito.any());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void searchCustomerByLastName() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/searchbyname?lastname={lastname}","Hamphire");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		verify(customerService).getAllCustomerByName(Mockito.any(), Mockito.anyString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void searchCustomerWithoutName() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/searchbyname");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		verify(customerService).getAllCustomerByName(Mockito.any(), Mockito.any());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void searchCustomerByFirstAndLastName() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/searchbyname?firstname={firstname}&lastname={lastname}","Brian","Hamphire");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		verify(customerService).getAllCustomerByName(Mockito.anyString(), Mockito.anyString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private static Address populateAddress() {
		Address address = new Address();
		address.setDoorNo("237-1");
		address.setStreet("Briadale St");
		address.setCity("New York");
		address.setCountry("United States");
		address.setZipCode("05601");
		return address;
	}

	private static Customer populateCustomer(Address mockAddress) {
		Customer customer = new Customer();
		customer.setFirstName("Brian");
		customer.setLastName("Hamphire");
		customer.setAge(45);
		customer.setAddress(mockAddress);
		return customer;
	}

	private static List<Customer> populateCustomerList() {
		ArrayList<Customer> customerList = new ArrayList<Customer>();

		Address address = new Address();
		Customer customer = new Customer();
		customer.setFirstName("Brian");
		customer.setLastName("Hamphire");
		customer.setAge(45);
		customer.setAddress(address);		
		customerList.add(customer);

		Customer customer1 = new Customer();
		customer1.setFirstName("Melena");
		customer1.setLastName("Hamphire");
		customer.setAge(40);
		customer.setAddress(address);
		customerList.add(customer1);
		return customerList;
	}

}
