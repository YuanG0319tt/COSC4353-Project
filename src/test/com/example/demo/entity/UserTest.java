package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setPassword("secret");
        user.setRole("user");

        assertEquals(1, user.getId());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("secret", user.getPassword());
        assertEquals("user", user.getRole());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("user@example.com");
        user1.setPassword("secret");
        user1.setRole("user");

        User user2 = new User();
        user2.setId(1);
        user2.setEmail("user@example.com");
        user2.setPassword("secret");
        user2.setRole("user");

        User user3 = new User();
        user3.setId(2);
        user3.setEmail("other@example.com");
        user3.setPassword("secret");
        user3.setRole("user");

        // Verify user1 is equal to user2
        assertTrue(user1.equals(user2));
        assertEquals(user1.hashCode(), user2.hashCode());

        // Verify user1 is not equal to user3
        assertFalse(user1.equals(user3));
        // Optionally check hash codes are different if they are computed from the same values
        assertNotEquals(user1.hashCode(), user3.hashCode());

        // Check equals with null and different type
        assertFalse(user1.equals(null));
        assertFalse(user1.equals("Some String"));
    }

    @Test
    public void testToString() {
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setPassword("secret");
        user.setRole("user");

        String userString = user.toString();

        assertNotNull(userString);
        // Check that important field values appear in the toString output
        assertTrue(userString.contains("user@example.com"));
        assertTrue(userString.contains("secret"));
        // Optionally, verify the class name appears
        assertTrue(userString.contains("User"));
    }
}
