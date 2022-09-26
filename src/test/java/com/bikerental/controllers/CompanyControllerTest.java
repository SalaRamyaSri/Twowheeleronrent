package com.bikerental.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.bikerental.entities.Company;
import com.bikerental.services.CompanyService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CompanyController.class)
public class CompanyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CompanyService companyService;
	
	

//	String  exampleCompanyJson = "{\"compname\":\"honda\"}";
//	{ "id":null, "position":null, "name":"Test", "description":null, "image":null }
	String  exampleCompanyJson = "{\"id\":\"101\",\"compname\":\"honda\"}";
//	String  exampleCompanyJson = {"id":"101", "compname":"honda"};
	
	@Test
	public void givenCompanyObject_whenCreateCompany_thenReturnSavedCompany() throws Exception {
		Company mockCompany = new Company("honda");

		// CompanyService saveCompany to respond back with mockCompany
		Mockito.doNothing().when(companyService).saveCompany(mockCompany);

		// Send mockCompany as body to /api/companies/createCompany
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/companies/createCompany")
				.accept(MediaType.APPLICATION_JSON).content(exampleCompanyJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	
    @Test
	public void retrieveDetailsForCourse() throws Exception {
		Company mockCompany = new Company("honda");

		Company mockCompanyData = new Company("hero");

		// given - precondition or setup
		List<Company> listOfCompanies = new ArrayList<>();
		listOfCompanies.add(mockCompany);
		listOfCompanies.add(mockCompanyData);

		Mockito.when(companyService.listall()).thenReturn(listOfCompanies);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/companies/getAllCompanies"));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfCompanies.size())));
	}
	
	
//	@Test
//	public void retrieveDetailsForCourse() throws Exception {
//      List<Company> listOfEmployees = new ArrayList<>();
//      listOfEmployees.add(null);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//				"/api/companies/getAllCompanies").accept(
//				MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		System.out.println(result.getResponse());
//		String expected ="{\"id\":\"101\",\"compname\":\"honda\"}";
//
//		JSONAssert.assertEquals(expected, result.getResponse()
//				.getContentAsString(), false);
//
//     }
	
}
