package com.example.demo.entity.PO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserPOTest {

    @Test
    public void testSettersAndGetters() {
        UserPO userPO = new UserPO();
        userPO.setEmail("test@example.com");
        userPO.setPassword("password123");
        userPO.setRole("admin");

        assertEquals("test@example.com", userPO.getEmail());
        assertEquals("password123", userPO.getPassword());
        assertEquals("admin", userPO.getRole());
    }

    @Test
    public void testEqualsAndHashCode() {
        UserPO user1 = new UserPO();
        user1.setEmail("test@example.com");
        user1.setPassword("password123");
        user1.setRole("admin");

        UserPO user2 = new UserPO();
        user2.setEmail("test@example.com");
        user2.setPassword("password123");
        user2.setRole("admin");

        UserPO user3 = new UserPO();
        user3.setEmail("different@example.com");
        user3.setPassword("password456");
        user3.setRole("user");

        // user1 and user2 have the same values so they should be equal.
        assertTrue(user1.equals(user2));
        assertEquals(user1.hashCode(), user2.hashCode());

        // user1 and user3 have different values so they should not be equal.
        assertFalse(user1.equals(user3));
        assertNotEquals(user1.hashCode(), user3.hashCode());

        // Additional checks for equals method robustness.
        assertFalse(user1.equals(null));
        assertFalse(user1.equals("Not a UserPO object"));
    }

    @Test
    public void testToString() {
        UserPO userPO = new UserPO();
        userPO.setEmail("test@example.com");
        userPO.setPassword("password123");
        userPO.setRole("admin");

        String toStringResult = userPO.toString();
        assertNotNull(toStringResult);
        // Verify that the toString() output contains key field values.
        assertTrue(toStringResult.contains("test@example.com"));
        assertTrue(toStringResult.contains("password123"));
        assertTrue(toStringResult.contains("admin"));
    }
}
