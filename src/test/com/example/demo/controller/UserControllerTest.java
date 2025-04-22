package com.example.demo.controller;

import com.example.demo.entity.DTO.UserDTO;
import com.example.demo.entity.DTO.UserUpdateRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.service.IUserService;
import com.example.demo.service.UserProFileService;
import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService iUserService;

    @MockBean
    private UserProFileService userProFileService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test POST /api/login endpoint.
     */
    @Test
    public void testLogin() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setRole("user");

        // Simulate a successful login result.
        Result<String> expectedResult = Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS, "Logged in successfully");
        Mockito.when(iUserService.login(ArgumentMatchers.any(UserDTO.class))).thenReturn(expectedResult);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.data").value("Logged in successfully"));
    }

    /**
     * Test POST /api/register endpoint.
     */
    @Test
    public void testRegister() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("newuser@example.com");
        userDTO.setPassword("password");
        userDTO.setRole("user");

        Result<String> expectedResult = Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS, "Registered successfully");
        Mockito.when(iUserService.register(ArgumentMatchers.any(UserDTO.class))).thenReturn(expectedResult);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.data").value("Registered successfully"));
    }

    /**
     * Test POST /api/update endpoint.
     */
    @Test
    public void testUpdate() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setEmail("update@example.com");
        updateRequest.setOldPassword("oldpass");
        updateRequest.setConfirmPassword("newpass");

        Result<String> expectedResult = Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS, "Update successful");
        Mockito.when(iUserService.updateByName(ArgumentMatchers.any(UserUpdateRequest.class))).thenReturn(expectedResult);

        mockMvc.perform(post("/api/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.data").value("Update successful"));
    }

    /**
     * Test GET /api/profile/{uid} endpoint.
     */
    @Test
    public void testGetProfile() throws Exception {
        UserProfile profile = new UserProfile();
        profile.setUid(1);
        profile.setFullName("John Doe");
        profile.setAddress1("123 Main St");
        profile.setCity("Sample City");
        // set other fields as needed

        Mockito.when(userProFileService.getById(1)).thenReturn(profile);

        mockMvc.perform(get("/api/profile/{uid}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.data.uid").value(1))
                .andExpect(jsonPath("$.data.fullName").value("John Doe"))
                .andExpect(jsonPath("$.data.address1").value("123 Main St"));
    }

    /**
     * Test POST /api/profile endpoint for creating a profile when none exists.
     */
    @Test
    public void testCreateProfile() throws Exception {
        UserProfile newProfile = new UserProfile();
        newProfile.setUid(2);
        newProfile.setFullName("Jane Doe");
        newProfile.setAddress1("456 Main St");
        newProfile.setCity("Another City");

        // Simulate that no profile exists for uid = 2.
        Mockito.when(userProFileService.getById(2)).thenReturn(null);
        Mockito.when(userProFileService.save(ArgumentMatchers.any(UserProfile.class))).thenReturn(true);

        mockMvc.perform(post("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.data.uid").value(2))
                .andExpect(jsonPath("$.data.fullName").value("Jane Doe"));
    }

    /**
     * Test POST /api/profile endpoint for updating an existing profile (success scenario).
     */
    @Test
    public void testUpdateProfileSuccess() throws Exception {
        UserProfile existingProfile = new UserProfile();
        existingProfile.setUid(3);
        existingProfile.setFullName("Existing User");
        existingProfile.setAddress1("789 Main St");
        existingProfile.setCity("Old City");

        // Simulate that a profile already exists.
        Mockito.when(userProFileService.getById(3)).thenReturn(existingProfile);
        // Simulate update success.
        Mockito.when(userProFileService.updateById(ArgumentMatchers.any(UserProfile.class))).thenReturn(true);

        // Modify profile data to simulate an update.
        existingProfile.setFullName("Updated User");

        mockMvc.perform(post("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.data.fullName").value("Updated User"));
    }

    /**
     * Test POST /api/profile endpoint for updating an existing profile (failure scenario).
     */
    @Test
    public void testUpdateProfileFailure() throws Exception {
        UserProfile existingProfile = new UserProfile();
        existingProfile.setUid(4);
        existingProfile.setFullName("Existing User");
        existingProfile.setAddress1("101 Main St");
        existingProfile.setCity("Old City");

        Mockito.when(userProFileService.getById(4)).thenReturn(existingProfile);
        Mockito.when(userProFileService.updateById(ArgumentMatchers.any(UserProfile.class))).thenReturn(false);

        mockMvc.perform(post("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.FAILED))
                .andExpect(jsonPath("$.msg").value(HttpMessage.UPDATE_FAILED))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    /**
     * Test GET /api/list endpoint.
     */
    @Test
    public void testGetUserList() throws Exception {
        Result<?> expectedResult = Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS, Collections.emptyList());
        Mockito.when(iUserService.getUserList()).thenReturn(expectedResult);

        mockMvc.perform(get("/api/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.data").isArray());
    }

    /**
     * Test POST /api/test endpoint.
     */
    @Test
    public void testTestEndpoint() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test2@example.com");
        userDTO.setPassword("pass");
        userDTO.setRole("user");

        String expectedResponse = "Test successful";
        Mockito.when(iUserService.test(ArgumentMatchers.any(UserDTO.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}
