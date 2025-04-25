package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    private UserDTO createBase() {
        UserDTO user = new UserDTO();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole("USER");
        return user;
    }

    @Test
    public void testNoArgsConstructor() {
        UserDTO user = new UserDTO();
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    public void testSettersAndGetters() {
        UserDTO user = new UserDTO();
        
        // Test with null values
        user.setEmail(null);
        assertNull(user.getEmail());

        user.setPassword(null);
        assertNull(user.getPassword());

        user.setRole(null);
        assertNull(user.getRole());

        // Test with actual values
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());

        user.setPassword("password123");
        assertEquals("password123", user.getPassword());

        user.setRole("USER");
        assertEquals("USER", user.getRole());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        UserDTO user1 = new UserDTO();
        UserDTO user2 = new UserDTO();
        
        // Test equality with all null fields
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        
        // Test with one field set to null and other to value
        user1.setEmail("test@example.com");
        user2.setEmail(null);
        assertNotEquals(user1, user2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        UserDTO user = createBase();
        assertNotEquals(user, new Object());
        assertNotEquals(user, null);
        assertNotEquals(user, "not a UserDTO");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        UserDTO user = createBase();
        assertEquals(user, user);
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        UserDTO user1 = createBase();
        UserDTO user2 = createBase();

        // Test each field individually
        user2.setEmail("different@example.com");
        assertNotEquals(user1, user2);

        user2 = createBase();
        user2.setPassword("different123");
        assertNotEquals(user1, user2);

        user2 = createBase();
        user2.setRole("ADMIN");
        assertNotEquals(user1, user2);
    }

    @Test
    public void testToStringWithNullValues() {
        UserDTO user = new UserDTO();
        String userString = user.toString();
        assertNotNull(userString);
        assertTrue(userString.contains("email=null"));
        assertTrue(userString.contains("password=null"));
        assertTrue(userString.contains("role=null"));
    }

    @Test
    public void testToStringWithAllValues() {
        UserDTO user = createBase();
        String userString = user.toString();
        assertNotNull(userString);
        assertTrue(userString.contains("email=test@example.com"));
        assertTrue(userString.contains("password=password123"));
        assertTrue(userString.contains("role=USER"));
    }

    @Test
    public void testEdgeCases() {
        UserDTO user = new UserDTO();
        
        // Test with empty strings
        user.setEmail("");
        assertEquals("", user.getEmail());
        
        user.setPassword("");
        assertEquals("", user.getPassword());
        
        user.setRole("");
        assertEquals("", user.getRole());
        
        // Test with special characters
        user.setEmail("test.user+tag@example.com");
        assertEquals("test.user+tag@example.com", user.getEmail());
        
        user.setPassword("p@ssw0rd!123");
        assertEquals("p@ssw0rd!123", user.getPassword());
        
        user.setRole("ROLE_USER");
        assertEquals("ROLE_USER", user.getRole());
    }
}
