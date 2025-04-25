package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VolHistoryTest {

    private VolHistory createBase() {
        return new VolHistory(
            1L,
            "John Doe",
            "john@example.com",
            "123-456-7890",
            "Community Cleanup",
            "2024-01-01",
            5,
            "Completed"
        );
    }

    @Test
    public void testNoArgsConstructor() {
        VolHistory history = new VolHistory();
        assertNull(history.getId());
        assertNull(history.getName());
        assertNull(history.getEmail());
        assertNull(history.getPhoneNumber());
        assertNull(history.getEventName());
        assertNull(history.getParticipationDate());
        assertEquals(0, history.getHoursVolunteered());
        assertNull(history.getStatus());
    }

    @Test
    public void testAllArgsConstructor() {
        VolHistory history = createBase();
        
        assertEquals(1L, history.getId());
        assertEquals("John Doe", history.getName());
        assertEquals("john@example.com", history.getEmail());
        assertEquals("123-456-7890", history.getPhoneNumber());
        assertEquals("Community Cleanup", history.getEventName());
        assertEquals("2024-01-01", history.getParticipationDate());
        assertEquals(5, history.getHoursVolunteered());
        assertEquals("Completed", history.getStatus());
    }

    @Test
    public void testSettersAndGetters() {
        VolHistory history = new VolHistory();
        
        // Test with null values
        history.setId(null);
        assertNull(history.getId());

        history.setName(null);
        assertNull(history.getName());

        history.setEmail(null);
        assertNull(history.getEmail());

        history.setPhoneNumber(null);
        assertNull(history.getPhoneNumber());

        history.setEventName(null);
        assertNull(history.getEventName());

        history.setParticipationDate(null);
        assertNull(history.getParticipationDate());

        history.setStatus(null);
        assertNull(history.getStatus());

        // Test with actual values
        history.setId(1L);
        assertEquals(1L, history.getId());

        history.setName("John Doe");
        assertEquals("John Doe", history.getName());

        history.setEmail("john@example.com");
        assertEquals("john@example.com", history.getEmail());

        history.setPhoneNumber("123-456-7890");
        assertEquals("123-456-7890", history.getPhoneNumber());

        history.setEventName("Community Cleanup");
        assertEquals("Community Cleanup", history.getEventName());

        history.setParticipationDate("2024-01-01");
        assertEquals("2024-01-01", history.getParticipationDate());

        history.setHoursVolunteered(5);
        assertEquals(5, history.getHoursVolunteered());

        history.setStatus("Completed");
        assertEquals("Completed", history.getStatus());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        VolHistory history1 = new VolHistory();
        VolHistory history2 = new VolHistory();
        
        // With default Object equals/hashCode, different instances are never equal
        assertFalse(history1.equals(history2));
        assertNotEquals(history1.hashCode(), history2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        VolHistory history = createBase();
        assertFalse(history.equals(new Object()));
        assertFalse(history.equals(null));
        assertFalse(history.equals("not a VolHistory"));
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        VolHistory history = createBase();
        assertTrue(history.equals(history));
        assertEquals(history.hashCode(), history.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        VolHistory history1 = createBase();
        VolHistory history2 = createBase();

        // With default Object equals/hashCode, different instances are never equal
        assertFalse(history1.equals(history2));
        assertNotEquals(history1.hashCode(), history2.hashCode());
    }

    @Test
    public void testToStringWithNullValues() {
        VolHistory history = new VolHistory();
        String historyString = history.toString();
        assertNotNull(historyString);
        // Default Object toString format
        assertTrue(historyString.startsWith("com.example.demo.entity.DTO.VolHistory@"));
    }

    @Test
    public void testToStringWithAllValues() {
        VolHistory history = createBase();
        String historyString = history.toString();
        assertNotNull(historyString);
        // Default Object toString format
        assertTrue(historyString.startsWith("com.example.demo.entity.DTO.VolHistory@"));
    }

    @Test
    public void testEdgeCases() {
        VolHistory history = new VolHistory();
        
        // Test with empty strings
        history.setName("");
        assertEquals("", history.getName());
        
        history.setEmail("");
        assertEquals("", history.getEmail());
        
        history.setPhoneNumber("");
        assertEquals("", history.getPhoneNumber());
        
        history.setEventName("");
        assertEquals("", history.getEventName());
        
        history.setParticipationDate("");
        assertEquals("", history.getParticipationDate());
        
        history.setStatus("");
        assertEquals("", history.getStatus());
        
        // Test with special characters
        history.setName("John O'Connor");
        assertEquals("John O'Connor", history.getName());
        
        history.setEmail("john.o'connor@example.com");
        assertEquals("john.o'connor@example.com", history.getEmail());
        
        history.setPhoneNumber("(123) 456-7890");
        assertEquals("(123) 456-7890", history.getPhoneNumber());
        
        history.setEventName("Event's Name");
        assertEquals("Event's Name", history.getEventName());
        
        history.setStatus("In-Progress");
        assertEquals("In-Progress", history.getStatus());
        
        // Test with minimum and maximum hours
        history.setHoursVolunteered(0);
        assertEquals(0, history.getHoursVolunteered());
        
        history.setHoursVolunteered(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, history.getHoursVolunteered());
    }
} 