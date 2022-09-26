package com.bikerental.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bikerental.entities.Customer;
import com.bikerental.services.CustomerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenCustomerObject_whenCreateCustomer_thenReturnSavedCustomer() throws Exception {

		// given - precondition or setup
		Customer customerData = new Customer();
		customerData.setUserid("userId");
		customerData.setUname("userName");
		customerData.setPwd("password");
		customerData.setPhone("12365478956");
		customerData.setGender("male");
		customerData.setAddress("address");
		customerData.setLicense("license");
		customerData.setCreatedon(java.time.LocalDateTime.now());

		String exampleCustomerJson = "{\"id\":\"101\",\"compname\":\"honda\"}";

		Mockito.doNothing().when(customerService).registerCustomer(customerData);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/bookings/createBooking")
				.accept(MediaType.APPLICATION_JSON).content(exampleCustomerJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	/**
	 * JUnit test for Get All Customers REST API
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenListOfCustomers_whenGetAllCustomers_thenReturnCustomersList() throws Exception {
		// given - precondition or setup
		Customer customerData = new Customer();
		customerData.setUserid("userId");
		customerData.setUname("userName");
		customerData.setPwd("password");
		customerData.setPhone("12365478956");
		customerData.setGender("male");
		customerData.setAddress("address");
		customerData.setLicense("license");
		customerData.setCreatedon(java.time.LocalDateTime.now());

		Customer mockcustomerData = new Customer();
		mockcustomerData.setUserid("mockUserId");
		mockcustomerData.setUname("mockUserName");
		mockcustomerData.setPwd("mockPassword");
		mockcustomerData.setPhone("9865374215");
		mockcustomerData.setGender("female");
		mockcustomerData.setAddress("mockAddress");
		mockcustomerData.setLicense("mockLicense");
		mockcustomerData.setCreatedon(java.time.LocalDateTime.now());

		List<Customer> listOfCustomers = new ArrayList<>();
		listOfCustomers.add(customerData);
		listOfCustomers.add(mockcustomerData);
		when(customerService.allCustomers()).thenReturn(listOfCustomers);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/customers/getAllCustomers"));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfCustomers.size())));

	}

}
