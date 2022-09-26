package com.bikerental.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import com.bikerental.entities.Bike;
import com.bikerental.entities.Booking;
import com.bikerental.entities.Company;
import com.bikerental.entities.Customer;
import com.bikerental.entities.Variant;
import com.bikerental.models.BookingDTO;
import com.bikerental.services.BookingService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BookingController.class)
public class BookingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookingService bookingService;

	String exampleJson = "{\"userid\":\"1\",\"pwd\":\"admin\",\"uname\":\"admin\"}";

	@Test
	public void givenBookingObject_whenCreateBooking_thenReturnSavedBooking() throws Exception {

		Customer customerData = new Customer();
		customerData.setUserid("userId");
		customerData.setUname("userName");
		customerData.setPwd("password");
		customerData.setPhone("12365478956");
		customerData.setGender("male");
		customerData.setAddress("address");
		customerData.setLicense("license");
		customerData.setCreatedon(java.time.LocalDateTime.now());

		Company companyData = new Company("honda");

		Variant variantData = new Variant();
		variantData.setId(101);
		variantData.setTitle("hondaTitle");
		variantData.setPrice(60000);
		variantData.setPhoto("photo");
		variantData.setCompany(companyData);
		variantData.setCreatedon(java.time.LocalDateTime.now());

		Bike bikeData = new Bike();
		bikeData.setId("3");
		bikeData.setModelyear(2024);
		bikeData.setStatus("In-Active");
		bikeData.setVariant(variantData);
		bikeData.setIsdeleted(false);
		bikeData.setCreatedon(java.time.LocalDateTime.now());

		Booking bookingData = new Booking();
		bookingData.setAdvance(1000);
		bookingData.setFromdate(LocalDate.now());
		bookingData.setTodate(LocalDate.now());
		bookingData.setMessage("booking_message");
		bookingData.setBookingdate(java.time.LocalDateTime.now());
		bookingData.setStatus("Active");
		bookingData.setBillamount(90000);
		bookingData.setVariant(variantData);
		bookingData.setBike(bikeData);

		BookingDTO bookingDTO = new BookingDTO();
		bookingDTO.setAdvance(1000);
		bookingDTO.setFromdate(LocalDate.now());
		bookingDTO.setTodate(LocalDate.now());
		bookingDTO.setMessage("booking_message");
		bookingDTO.setBillamount(5000);
		bookingDTO.setCardno("card number");
		bookingDTO.setNameoncard("name_on_card");
		bookingDTO.setUserid("userId");
		bookingDTO.setVarid(101);

		Mockito.doNothing().when(bookingService).saveBooking(bookingDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/bookings/createBooking")
				.accept(MediaType.APPLICATION_JSON).content(exampleJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void givenListOfBookings_whenGetAllBookings_thenReturnBookingsList() throws Exception {
		// given - precondition or setup
		Company companyData = new Company("honda");

		Variant variantData = new Variant();
		variantData.setId(101);
		variantData.setTitle("hondaTitle");
		variantData.setPrice(60000);
		variantData.setPhoto("photo");
		variantData.setCompany(companyData);
		variantData.setCreatedon(java.time.LocalDateTime.now());

		Bike bikeData = new Bike();
		bikeData.setId("3");
		bikeData.setModelyear(2024);
		bikeData.setStatus("In-Active");
		bikeData.setVariant(variantData);
		bikeData.setIsdeleted(false);
		bikeData.setCreatedon(java.time.LocalDateTime.now());

		Booking bookingData = new Booking();
		bookingData.setAdvance(1000);
		bookingData.setFromdate(LocalDate.now());
		bookingData.setTodate(LocalDate.now());
		bookingData.setMessage("booking_message");
		bookingData.setBookingdate(java.time.LocalDateTime.now());
		bookingData.setStatus("Active");
		bookingData.setBillamount(90000);
		bookingData.setVariant(variantData);
		bookingData.setBike(bikeData);

		List<Booking> listOfBookings = new ArrayList<>();
		listOfBookings.add(bookingData);
		when(bookingService.findAllBookings()).thenReturn(listOfBookings);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/bookings/getAllBookings"));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfBookings.size())));

	}
}
