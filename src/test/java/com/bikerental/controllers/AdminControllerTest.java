package com.bikerental.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bikerental.entities.Admin;
import com.bikerental.services.AdminService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AdminController.class)
public class AdminControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;
	
	
	String  exampleAdminJson = "{\"userid\":\"1\",\"pwd\":\"admin\",\"uname\":\"admin\"}";
	
	@Test
	public void validateAdminUser() throws Exception {
		Admin mockAdmin = new Admin("1","admin","admin");

		// AdminService.validate to respond back with mockCourse
		Mockito.when(adminService.validate(Mockito.anyString(),
				Mockito.anyString())).thenReturn(mockAdmin);

		// Send mockAdmin as body to /api/admin/validate
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/admin/validate")
				.accept(MediaType.APPLICATION_JSON).content(exampleAdminJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void updateAdminUser() throws Exception {
		Admin mockAdmin = new Admin("1","admin","admin");

		Mockito.doNothing().when(adminService).updateAdmin(mockAdmin);

		// Send mockAdmin as body to /api/admin/updateAdminProfile
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/admin/checkPassword")
				.accept(MediaType.APPLICATION_JSON).content(exampleAdminJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}
