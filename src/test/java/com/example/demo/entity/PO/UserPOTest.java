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

    @Test
    public void testEqualsWithNullFields() {
        UserPO user1 = new UserPO();
        UserPO user2 = new UserPO();
        
        // Test equals with both objects having null fields
        assertTrue(user1.equals(user2));
        
        // Test equals with one object having null email
        user1.setEmail("test@example.com");
        assertFalse(user1.equals(user2));
        
        // Test equals with one object having null password
        user1 = new UserPO();
        user2 = new UserPO();
        user1.setPassword("password123");
        assertFalse(user1.equals(user2));
        
        // Test equals with one object having null role
        user1 = new UserPO();
        user2 = new UserPO();
        user1.setRole("admin");
        assertFalse(user1.equals(user2));
    }

    @Test
    public void testHashCodeWithNullFields() {
        UserPO user1 = new UserPO();
        UserPO user2 = new UserPO();
        
        // Test hashCode with null fields
        assertEquals(user1.hashCode(), user2.hashCode());
        
        // Test hashCode with same non-null values
        user1.setEmail("test@example.com");
        user1.setPassword("password123");
        user1.setRole("admin");
        user2.setEmail("test@example.com");
        user2.setPassword("password123");
        user2.setRole("admin");
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToStringWithNullFields() {
        UserPO userPO = new UserPO();
        String toStringOutput = userPO.toString();
        assertNotNull(toStringOutput);
        assertTrue(toStringOutput.contains("email=null"));
        assertTrue(toStringOutput.contains("password=null"));
        assertTrue(toStringOutput.contains("role=null"));
    }

    @Test
    public void testEqualsWithDifferentIds() {
        UserPO user1 = new UserPO();
        user1.setId(1);
        user1.setEmail("test@example.com");
        
        UserPO user2 = new UserPO();
        user2.setId(2);
        user2.setEmail("test@example.com");
        
        assertFalse(user1.equals(user2));
    }

    @Test
    public void testEqualsWithSelf() {
        UserPO user = new UserPO();
        user.setEmail("test@example.com");
        assertTrue(user.equals(user));
    }

    @Test
    public void testHashCodeConsistency() {
        UserPO user = new UserPO();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole("admin");
        
        int hashCode1 = user.hashCode();
        int hashCode2 = user.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testAllFields() {
        UserPO user = new UserPO();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole("admin");
        
        assertEquals(1, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("admin", user.getRole());
    }

    @Test
    public void testToStringWithAllFields() {
        UserPO user = new UserPO();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole("admin");
        
        String toStringOutput = user.toString();
        assertTrue(toStringOutput.contains("id=1"));
        assertTrue(toStringOutput.contains("email=test@example.com"));
        assertTrue(toStringOutput.contains("password=password123"));
        assertTrue(toStringOutput.contains("role=admin"));
    }
}
