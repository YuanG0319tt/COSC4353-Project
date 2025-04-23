package com.example.demo.service;

import com.example.demo.entity.DTO.UserDTO;
import com.example.demo.entity.DTO.UserUpdateRequest;
import com.example.demo.entity.PO.UserPO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.entity.VO.UserVO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserProFileService;
import com.example.demo.service.impl.IUserServiceImpl;
import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IUserServiceImplTest {

    @InjectMocks
    private IUserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserProFileService userProFileService;

    // ----- Tests for login(UserDTO) -----
    @Test
    public void testLogin_MissingEmail() {
        UserDTO dto = new UserDTO();
        dto.setEmail("");
        dto.setPassword("pass");
        // When email is missing, should return NEED_USER_NAME error
        Result result = userService.login(dto);
        assertEquals(HttpStatus.ERROR, result.getCode());
        assertEquals(HttpMessage.NEED_USER_NAME, result.getMsg());
    }

    @Test
    public void testLogin_MissingPassword() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("");
        // When password is missing, should return NEED_PASSWORD error
        Result result = userService.login(dto);
        assertEquals(HttpStatus.ERROR, result.getCode());
        assertEquals(HttpMessage.NEED_PASSWORD, result.getMsg());
    }

    @Test
    public void testLogin_UserNotFound() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("pass");
        when(userMapper.selectUser("test@example.com", "pass")).thenReturn(null);
        // Should return WRONG_PASSWORD when user not found
        Result result = userService.login(dto);
        assertEquals(HttpStatus.FAILED, result.getCode());
        assertEquals(HttpMessage.WRONG_PASSWORD, result.getMsg());
    }

    @Test
    public void testLogin_Success_FirstLogin() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("pass");

        // Prepare a valid user
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setRole("USER");

        when(userMapper.selectUser("test@example.com", "pass")).thenReturn(user);
        // Simulate that no profile exists—this indicates a first login
        when(userProFileService.getById(1)).thenReturn(null);

        Result result = userService.login(dto);
        assertEquals(HttpStatus.SUCCESS, result.getCode());
        assertEquals(HttpMessage.SUCCESS, result.getMsg());

        // Verify that a UserVO is returned with isFirstLogin = "true"
        Object data = result.getData();
        assertTrue(data instanceof UserVO);
        UserVO vo = (UserVO) data;
        assertEquals("true", vo.getIsFirstLogin());
        assertEquals("test@example.com", vo.getUsername());
        assertEquals(1, vo.getUid());
        assertEquals("USER", vo.getRole());
    }

    @Test
    public void testLogin_Success_NotFirstLogin() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("pass");

        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setRole("USER");

        when(userMapper.selectUser("test@example.com", "pass")).thenReturn(user);

        // Simulate that the user profile exists (not first login)
        UserProfile profile = new UserProfile();
        profile.setUid(1);
        when(userProFileService.getById(1)).thenReturn(profile);

        Result result = userService.login(dto);
        assertEquals(HttpStatus.SUCCESS, result.getCode());
        assertEquals(HttpMessage.SUCCESS, result.getMsg());

        Object data = result.getData();
        assertTrue(data instanceof UserVO);
        UserVO vo = (UserVO) data;
        assertEquals("false", vo.getIsFirstLogin());
        assertEquals("test@example.com", vo.getUsername());
        assertEquals(1, vo.getUid());
        assertEquals("USER", vo.getRole());
    }

    // ----- Tests for register(UserDTO) -----
    @Test
    public void testRegister_MissingEmail() {
        UserDTO dto = new UserDTO();
        dto.setEmail("");
        dto.setPassword("pass123");
        dto.setRole("USER");

        Result result = userService.register(dto);
        assertEquals(HttpStatus.ERROR, result.getCode());
        assertEquals(HttpMessage.NEED_USER_NAME, result.getMsg());
    }

    @Test
    public void testRegister_MissingPassword() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("");
        dto.setRole("USER");

        Result result = userService.register(dto);
        assertEquals(HttpStatus.ERROR, result.getCode());
        assertEquals(HttpMessage.NEED_PASSWORD, result.getMsg());
    }

    @Test
    public void testRegister_MissingRole() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("pass123");
        dto.setRole("");
        Result result = userService.register(dto);

        assertEquals(HttpStatus.ERROR, result.getCode());
        assertEquals("can not be empty", result.getMsg());
    }

    @Test
    public void testRegister_PasswordTooShort() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("12345"); // less than 6 characters
        dto.setRole("USER");

        Result result = userService.register(dto);
        assertEquals(HttpStatus.FAILED, result.getCode());
        assertEquals(HttpMessage.PASSWORD_TOO_SHORT, result.getMsg());
    }

    @Test
    public void testRegister_UserExists() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("pass123");
        dto.setRole("USER");

        // Simulate that a user with the same email already exists
        User existingUser = new User();
        when(userMapper.selectByUsername("test@example.com")).thenReturn(existingUser);

        Result result = userService.register(dto);
        assertEquals(HttpStatus.FAILED, result.getCode());
        assertEquals(HttpMessage.NAME_EXISTED, result.getMsg());
    }

    @Test
    public void testRegister_InsertSuccess() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("pass123");
        dto.setRole("USER");

        when(userMapper.selectByUsername("test@example.com")).thenReturn(null);
        // Simulate successful insertion (insert returns 1)
        when(userMapper.insert(any(UserPO.class))).thenReturn(1);

        Result result = userService.register(dto);
        assertEquals(HttpStatus.SUCCESS, result.getCode());
        assertEquals(HttpMessage.SUCCESS, result.getMsg());
    }

    @Test
    public void testRegister_InsertFailure() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("pass123");
        dto.setRole("USER");

        when(userMapper.selectByUsername("test@example.com")).thenReturn(null);
        // Simulate insertion failure (insert returns 0)
        when(userMapper.insert(any(UserPO.class))).thenReturn(0);

        Result result = userService.register(dto);
        assertEquals(HttpStatus.ERROR, result.getCode());
        assertEquals(HttpMessage.SYSTEM_ERROR, result.getMsg());
    }

    // ----- Test for test(UserDTO) -----
    @Test
    public void testTestMethod() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");
        when(userMapper.selectPasswordByUsername("test@example.com")).thenReturn("encryptedPassword");

        String ret = userService.test(dto);
        assertEquals("encryptedPassword", ret);
    }

    // ----- Tests for updateByName(UserUpdateRequest) -----
    @Test
    public void testUpdateByName_WrongOldPassword() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("test@example.com");
        request.setOldPassword("wrongPassword");
        request.setConfirmPassword("newPassword");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("correctPassword");
        when(userMapper.selectByUsername("test@example.com")).thenReturn(user);

        Result result = userService.updateByName(request);
        assertEquals(HttpStatus.ERROR, result.getCode());
        assertEquals("original password wrong", result.getMsg());
    }

    // ----- Test for getUserList() -----
    @Test
    public void testGetUserList() {
        List<User> userList = Collections.singletonList(new User());
        when(userMapper.getUserList()).thenReturn(userList);

        Result result = userService.getUserList();
        assertEquals(HttpStatus.SUCCESS, result.getCode());
        assertEquals(HttpMessage.SUCCESS, result.getMsg());
        assertEquals(userList, result.getData());
    }
}
