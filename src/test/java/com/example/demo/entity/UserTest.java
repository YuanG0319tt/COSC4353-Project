package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User createBaseUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setPassword("secret");
        user.setRole("user");
        return user;
    }

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        
        // Test with null values
        user.setId(null);
        assertNull(user.getId());

        user.setEmail(null);
        assertNull(user.getEmail());

        user.setPassword(null);
        assertNull(user.getPassword());

        user.setRole(null);
        assertNull(user.getRole());

        // Test with actual values
        user.setId(1);
        assertEquals(1, user.getId());

        user.setEmail("user@example.com");
        assertEquals("user@example.com", user.getEmail());

        user.setPassword("secret");
        assertEquals("secret", user.getPassword());

        user.setRole("user");
        assertEquals("user", user.getRole());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        User user1 = new User();
        User user2 = new User();
        
        // Test equality with all null fields
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        
        // Test with one field set to null and other to value
        user1.setId(1);
        user2.setId(null);
        assertNotEquals(user1, user2);
        
        user1 = new User();
        user2 = new User();
        user1.setEmail("user@example.com");
        user2.setEmail(null);
        assertNotEquals(user1, user2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        User user = createBaseUser();
        assertNotEquals(user, new Object());
        assertNotEquals(user, null);
        assertNotEquals(user, "not a User");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        User user = createBaseUser();
        assertEquals(user, user);
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        User user1 = createBaseUser();
        User user2 = createBaseUser();

        // Test each field individually
        user2.setId(2);
        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());

        user2 = createBaseUser();
        user2.setEmail("other@example.com");
        assertNotEquals(user1, user2);

        user2 = createBaseUser();
        user2.setPassword("different");
        assertNotEquals(user1, user2);

        user2 = createBaseUser();
        user2.setRole("admin");
        assertNotEquals(user1, user2);
    }

    @Test
    public void testToStringWithNullValues() {
        User user = new User();
        String userString = user.toString();
        assertNotNull(userString);
        assertTrue(userString.contains("id=null"));
        assertTrue(userString.contains("email=null"));
        assertTrue(userString.contains("password=null"));
        assertTrue(userString.contains("role=null"));
    }

    @Test
    public void testToStringWithAllValues() {
        User user = createBaseUser();
        String userString = user.toString();
        assertNotNull(userString);
        assertTrue(userString.contains("id=1"));
        assertTrue(userString.contains("email=user@example.com"));
        assertTrue(userString.contains("password=secret"));
        assertTrue(userString.contains("role=user"));
    }

    @Test
    public void testCanEqual() {
        User user1 = createBaseUser();
        User user2 = new User();
        
        // Test canEqual with same type
        assertTrue(user1.canEqual(user2));
        
        // Test canEqual with different type
        assertFalse(user1.canEqual(new Object()));
        assertFalse(user1.canEqual(null));
    }

    @Test
    public void testEdgeCases() {
        User user = new User();
        
        // Test with empty strings
        user.setEmail("");
        assertEquals("", user.getEmail());
        
        user.setPassword("");
        assertEquals("", user.getPassword());
        
        user.setRole("");
        assertEquals("", user.getRole());
        
        // Test with special characters
        user.setEmail("user.name+tag@example.com");
        assertEquals("user.name+tag@example.com", user.getEmail());
        
        user.setPassword("p@ssw0rd!123");
        assertEquals("p@ssw0rd!123", user.getPassword());
        
        user.setRole("admin-user");
        assertEquals("admin-user", user.getRole());
    }

    @Test
    public void testEmailEdgeCases() {
        User user = new User();
        
        // Test various email formats
        user.setEmail("user@example.com");
        assertEquals("user@example.com", user.getEmail());
        
        user.setEmail("user.name@example.com");
        assertEquals("user.name@example.com", user.getEmail());
        
        user.setEmail("user+tag@example.com");
        assertEquals("user+tag@example.com", user.getEmail());
        
        user.setEmail("user@subdomain.example.com");
        assertEquals("user@subdomain.example.com", user.getEmail());
    }

    @Test
    public void testRoleEdgeCases() {
        User user = new User();
        
        // Test various role formats
        user.setRole("admin");
        assertEquals("admin", user.getRole());
        
        user.setRole("user");
        assertEquals("user", user.getRole());
        
        user.setRole("moderator");
        assertEquals("moderator", user.getRole());
        
        user.setRole("super-admin");
        assertEquals("super-admin", user.getRole());
    }
}
