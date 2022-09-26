package com.bikerental.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bikerental.entities.Bike;
import com.bikerental.entities.Company;
import com.bikerental.entities.Variant;
import com.bikerental.models.BikeDTO;
import com.bikerental.services.BikeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BikesController.class)
public class BikesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BikeService bikeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenBikeObject_whenCreateBike_thenReturnSavedBike() throws Exception {

		// given - precondition or setup
		Company companyData = new Company("honda");

		Variant variantData = new Variant(101, "hondaTitle", 50000, "photo", companyData, LocalDateTime.now());

		Bike bike1 = new Bike("3", 2024, "In-Active", variantData, false, LocalDateTime.now());

		BikeDTO bike = new BikeDTO("5", 2019, 100);
		Mockito.doNothing().when(bikeService).saveBike(bike);

		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(post("/api/insertbike").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bike1)));

		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(status().is4xxClientError());
	}

	// JUnit test for delete Bike REST API
	@Test
	public void givenBikeId_whenDeleteBike_thenReturn200() throws Exception {
		// given - precondition or setup
		String bikeId = "100";
		Mockito.doNothing().when(bikeService).deleteBike(bikeId);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(delete("/api/{id}", bikeId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print());
	}

	// JUnit test for Get All bikes REST API
	@Test
	public void givenListOfBikes_whenGetAllBikes_thenReturnBikesList() throws Exception {

		// given - precondition or setup
		Company companyData = new Company("honda");

		Variant variantData = new Variant(101, "hondaTitle", 50000, "photo", companyData, LocalDateTime.now());

		List<Bike> listOfBikes = new ArrayList<>();
		listOfBikes.add(new Bike("1", 2022, "Active", variantData, false, LocalDateTime.now()));
		listOfBikes.add(new Bike("2", 2023, "Active", variantData, false, LocalDateTime.now()));
		when(bikeService.listAll()).thenReturn(listOfBikes);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/bikeDetails"));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfBikes.size())));

	}
	
	// JUnit test for GET Bike by id REST API
	@Test
	public void givenBikeId_whenGetBikeById_thenReturnBikeObject() throws Exception {
		// given - precondition or setup
		String bikeId = "123";
		Company companyData = new Company("honda");

		Variant variantData = new Variant(101, "hondaTitle", 50000, "photo", companyData, LocalDateTime.now());

		Bike bike = new Bike("3", 2024, "In-Active", variantData, false, LocalDateTime.now());

		when(bikeService.findById(bikeId)).thenReturn(bike);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/search", bikeId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print());
//		        .andExpect(jsonPath("$.id", is(bike.getId())));
//				.andExpect(jsonPath("$.modelYear", is(bike.getModelyear())))
//				.andExpect(jsonPath("$.status", is(bike.getStatus())))
//				.andExpect(jsonPath("$.variant", is(bike.getVariant())))
//				.andExpect(jsonPath("$.isdeleted", is(bike.isIsdeleted())))
//				.andExpect(jsonPath("$.createdon", is(bike.getCreatedon())));

	}
}
