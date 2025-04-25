package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserUpdateRequestTest {

    private UserUpdateRequest createBase() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("test@example.com");
        request.setConfirmPassword("newpassword123");
        request.setOldPassword("oldpassword123");
        return request;
    }

    @Test
    public void testNoArgsConstructor() {
        UserUpdateRequest request = new UserUpdateRequest();
        assertNull(request.getEmail());
        assertNull(request.getConfirmPassword());
        assertNull(request.getOldPassword());
    }

    @Test
    public void testSettersAndGetters() {
        UserUpdateRequest request = new UserUpdateRequest();
        
        // Test with null values
        request.setEmail(null);
        assertNull(request.getEmail());

        request.setConfirmPassword(null);
        assertNull(request.getConfirmPassword());

        request.setOldPassword(null);
        assertNull(request.getOldPassword());

        // Test with actual values
        request.setEmail("test@example.com");
        assertEquals("test@example.com", request.getEmail());

        request.setConfirmPassword("newpassword123");
        assertEquals("newpassword123", request.getConfirmPassword());

        request.setOldPassword("oldpassword123");
        assertEquals("oldpassword123", request.getOldPassword());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        UserUpdateRequest request1 = new UserUpdateRequest();
        UserUpdateRequest request2 = new UserUpdateRequest();
        
        // Test equality with all null fields
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        
        // Test with one field set to null and other to value
        request1.setEmail("test@example.com");
        request2.setEmail(null);
        assertNotEquals(request1, request2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        UserUpdateRequest request = createBase();
        assertNotEquals(request, new Object());
        assertNotEquals(request, null);
        assertNotEquals(request, "not a UserUpdateRequest");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        UserUpdateRequest request = createBase();
        assertEquals(request, request);
        assertEquals(request.hashCode(), request.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        UserUpdateRequest request1 = createBase();
        UserUpdateRequest request2 = createBase();

        // Test each field individually
        request2.setEmail("different@example.com");
        assertNotEquals(request1, request2);

        request2 = createBase();
        request2.setConfirmPassword("different123");
        assertNotEquals(request1, request2);

        request2 = createBase();
        request2.setOldPassword("different456");
        assertNotEquals(request1, request2);
    }

    @Test
    public void testToStringWithNullValues() {
        UserUpdateRequest request = new UserUpdateRequest();
        String requestString = request.toString();
        assertNotNull(requestString);
        assertTrue(requestString.contains("email=null"));
        assertTrue(requestString.contains("confirmPassword=null"));
        assertTrue(requestString.contains("oldPassword=null"));
    }

    @Test
    public void testToStringWithAllValues() {
        UserUpdateRequest request = createBase();
        String requestString = request.toString();
        assertNotNull(requestString);
        assertTrue(requestString.contains("email=test@example.com"));
        assertTrue(requestString.contains("confirmPassword=newpassword123"));
        assertTrue(requestString.contains("oldPassword=oldpassword123"));
    }

    @Test
    public void testEdgeCases() {
        UserUpdateRequest request = new UserUpdateRequest();
        
        // Test with empty strings
        request.setEmail("");
        assertEquals("", request.getEmail());
        
        request.setConfirmPassword("");
        assertEquals("", request.getConfirmPassword());
        
        request.setOldPassword("");
        assertEquals("", request.getOldPassword());
        
        // Test with special characters
        request.setEmail("test.user+tag@example.com");
        assertEquals("test.user+tag@example.com", request.getEmail());
        
        request.setConfirmPassword("p@ssw0rd!123");
        assertEquals("p@ssw0rd!123", request.getConfirmPassword());
        
        request.setOldPassword("p@ssw0rd!456");
        assertEquals("p@ssw0rd!456", request.getOldPassword());
    }
}
