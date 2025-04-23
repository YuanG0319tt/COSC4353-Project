package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserUpdateRequestTest {

    @Test
    public void testSettersAndGetters() {
        UserUpdateRequest request = new UserUpdateRequest();

        request.setEmail("user@example.com");
        request.setConfirmPassword("newPass123");
        request.setOldPassword("oldPass456");

        assertEquals("user@example.com", request.getEmail());
        assertEquals("newPass123", request.getConfirmPassword());
        assertEquals("oldPass456", request.getOldPassword());
    }

    @Test
    public void testEqualsAndHashCode() {
        UserUpdateRequest req1 = new UserUpdateRequest();
        req1.setEmail("user@example.com");
        req1.setConfirmPassword("newPass123");
        req1.setOldPassword("oldPass456");

        UserUpdateRequest req2 = new UserUpdateRequest();
        req2.setEmail("user@example.com");
        req2.setConfirmPassword("newPass123");
        req2.setOldPassword("oldPass456");

        UserUpdateRequest req3 = new UserUpdateRequest();
        req3.setEmail("other@example.com");
        req3.setConfirmPassword("diffPass123");
        req3.setOldPassword("diffPass456");

        // Same values should be equal
        assertTrue(req1.equals(req2));
        assertEquals(req1.hashCode(), req2.hashCode());

        // Different values should not be equal
        assertFalse(req1.equals(req3));
        assertNotEquals(req1.hashCode(), req3.hashCode());

        // Comparison to null and different types should be false
        assertFalse(req1.equals(null));
        assertFalse(req1.equals("Not a UserUpdateRequest"));
    }

    @Test
    public void testToString() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("user@example.com");
        request.setConfirmPassword("newPass123");
        request.setOldPassword("oldPass456");

        String result = request.toString();
        assertNotNull(result);
        // Check that the toString output contains the key field values
        assertTrue(result.contains("user@example.com"));
        assertTrue(result.contains("newPass123"));
        assertTrue(result.contains("oldPass456"));
    }
}
