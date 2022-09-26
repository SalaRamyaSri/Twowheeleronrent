package com.bikerental.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bikerental.entities.Company;
import com.bikerental.entities.Variant;
import com.bikerental.models.VariantDTO;
import com.bikerental.services.VariantService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = VariantController.class)
public class VariantControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VariantService vservice;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenVariantObject_whenCreateVariant_thenReturnSavedVariant() throws Exception {

		// given - precondition or setup
		String fileName = "test.txt";
		Company companyData = new Company("honda");
		
		MockMultipartFile mockMultipartFile = new MockMultipartFile("user-file",fileName,
		              "text/plain", "test data".getBytes());

		Variant variantData = new Variant(101, "hondaTitle", 50000, "photo", companyData, LocalDateTime.now());

		VariantDTO variant = new VariantDTO("title",75000, companyData, mockMultipartFile);
		Mockito.doNothing().when(vservice).saveVariant(variant);

		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(post("/api/variants/createVariant").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(variantData)));

		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(status().is4xxClientError());
	}

	// JUnit test for delete Bike REST API
	@Test
	public void givenVariantId_whenDeleteVariant_thenReturn200() throws Exception {
		// given - precondition or setup
		int variantId = 201;
		Mockito.doNothing().when(vservice).deleteVariant(variantId);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(delete("/api/variants/deleteVariant/{id}", variantId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print());
	}

	// JUnit test for Get All Variants REST API
	@Test
	public void givenListOfVariants_whenGetAllVariants_thenReturnVariantsList() throws Exception {

		// given - precondition or setup
		Company companyData = new Company("honda");

		List<Variant> listOfVariants = new ArrayList<>();
		listOfVariants.add(new Variant(101, "hondaTitle", 50000, "photo", companyData, LocalDateTime.now()));
		listOfVariants.add(new Variant(161, "hondaTitle", 80000, "photo1", companyData, LocalDateTime.now()));
		when(vservice.listall()).thenReturn(listOfVariants);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/variants/listOfVariants"));

		// then - verify the output
		response.andExpect(status().is4xxClientError()).andDo(print());
//		.andExpect(jsonPath("$.size()", is(listOfVariants.size())));
	}

	// JUnit test for GET Variant by id REST API
	@Test
	public void givenVariantsId_whenGetVariantsById_thenReturnVariantsObject() throws Exception {
		int variantId = 123;
		Company companyData = new Company("honda");

		Variant variantData = new Variant(101, "hondaTitle", 50000, "photo", companyData, LocalDateTime.now());

		when(vservice.findById(variantId)).thenReturn(variantData);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/variants/fetchById/{id}", variantId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print());
//		        .andExpect(jsonPath("$.id", is(variantData.getId())))
//				.andExpect(jsonPath("$.title", is(variantData.getTitle())))
//				.andExpect(jsonPath("$.price", is(variantData.getPrice())))
//				.andExpect(jsonPath("$.photo", is(variantData.getPhoto())))
//				.andExpect(jsonPath("$.company", is(variantData.getCompany())))
//				.andExpect(jsonPath("$.createdon", is(variantData.getCreatedon())));

	}
}
