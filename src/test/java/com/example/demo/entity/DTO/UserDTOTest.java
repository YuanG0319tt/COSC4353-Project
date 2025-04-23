package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    @Test
    public void testSettersAndGetters() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("secret");
        userDTO.setRole("user");

        assertEquals("test@example.com", userDTO.getEmail());
        assertEquals("secret", userDTO.getPassword());
        assertEquals("user", userDTO.getRole());
    }

    @Test
    public void testEqualsAndHashCode() {
        UserDTO dto1 = new UserDTO();
        dto1.setEmail("test@example.com");
        dto1.setPassword("secret");
        dto1.setRole("user");

        UserDTO dto2 = new UserDTO();
        dto2.setEmail("test@example.com");
        dto2.setPassword("secret");
        dto2.setRole("user");

        UserDTO dto3 = new UserDTO();
        dto3.setEmail("other@example.com");
        dto3.setPassword("secret");
        dto3.setRole("admin");

        // Test equality of identical DTOs
        assertTrue(dto1.equals(dto2));
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Test inequality with a different DTO
        assertFalse(dto1.equals(dto3));
        // Optionally, check hashCode inequality if hashCode is based on the same fields
        assertNotEquals(dto1.hashCode(), dto3.hashCode());

        // Verify equals against null and different type
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("Not a UserDTO"));
    }

    @Test
    public void testToString() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("secret");
        userDTO.setRole("user");

        String result = userDTO.toString();
        assertNotNull(result);
        // Verify that the output of toString() contains key field values
        assertTrue(result.contains("test@example.com"));
        assertTrue(result.contains("secret"));
        assertTrue(result.contains("user"));
    }
}
