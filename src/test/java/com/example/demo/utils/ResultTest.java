package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {

    @Test
    public void testNoArgsConstructorAndSetters() {
        // Create an instance using the no-argument constructor and then set values.
        Result<String> result = new Result<>();
        result.setCode(200);
        result.setMsg("OK");
        result.setData("Some data");

        // Verify that getters return the set values.
        assertEquals(200, result.getCode());
        assertEquals("OK", result.getMsg());
        assertEquals("Some data", result.getData());
    }

    @Test
    public void testAllArgsConstructor() {
        // Create an instance using the all-arguments constructor.
        Result<String> result = new Result<>(404, "Not Found", "Error data");

        // Verify that the values are set correctly.
        assertEquals(404, result.getCode());
        assertEquals("Not Found", result.getMsg());
        assertEquals("Error data", result.getData());
    }

    @Test
    public void testCustomConstructor() {
        // The custom constructor sets code and data, msg retains its default ("success").
        Result<String> result = new Result<>(500, "Internal error");

        assertEquals(500, result.getCode());
        // msg is the field's default value from the declaration ("success")
        assertEquals("success", result.getMsg());
        assertEquals("Internal error", result.getData());
    }

    @Test
    public void testResponseStaticMethod() {
        // Test the static helper method to create a Result instance.
        Result<String> result = Result.response(201, "Created", "New data");

        assertEquals(201, result.getCode());
        assertEquals("Created", result.getMsg());
        assertEquals("New data", result.getData());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two identical instances and one with different values.
        Result<String> result1 = new Result<>(200, "OK", "Some data");
        Result<String> result2 = new Result<>(200, "OK", "Some data");
        Result<String> result3 = new Result<>(404, "Not Found", "Error data");

        // result1 and result2 should be equal and have the same hash code.
        assertTrue(result1.equals(result2));
        assertEquals(result1.hashCode(), result2.hashCode());

        // result1 should not equal result3.
        assertFalse(result1.equals(result3));
    }

    @Test
    public void testToString() {
        // Create a Result instance and invoke toString().
        Result<String> result = new Result<>(123, "Test", "Some data");
        String toStringOutput = result.toString();
        assertNotNull(toStringOutput);

        // Verify that the output contains the expected field values.
        assertTrue(toStringOutput.contains("123"));
        assertTrue(toStringOutput.contains("Test"));
        assertTrue(toStringOutput.contains("Some data"));
    }
}
