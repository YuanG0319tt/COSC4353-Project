package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        // Create a Result instance
        Result<String> original = new Result<>(200, "OK", "Test data");
        
        // Serialize
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(original);
        oos.flush();
        
        // Deserialize
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        @SuppressWarnings("unchecked")
        Result<String> deserialized = (Result<String>) ois.readObject();
        
        // Verify deserialized object matches original
        assertEquals(original.getCode(), deserialized.getCode());
        assertEquals(original.getMsg(), deserialized.getMsg());
        assertEquals(original.getData(), deserialized.getData());
    }

    @Test
    public void testNullValues() {
        // Test with null message
        Result<String> result1 = new Result<>(200, null, "data");
        assertNull(result1.getMsg());
        
        // Test with null data
        Result<String> result2 = new Result<>(200, "OK", null);
        assertNull(result2.getData());
        
        // Test with all null values
        Result<String> result3 = new Result<>(0, null, null);
        assertNull(result3.getMsg());
        assertNull(result3.getData());
    }

    @Test
    public void testDifferentGenericTypes() {
        // Test with Integer
        Result<Integer> intResult = new Result<>(200, "OK", 42);
        assertEquals(42, intResult.getData());
        
        // Test with List
        Result<List<String>> listResult = new Result<>(200, "OK", Arrays.asList("a", "b", "c"));
        assertEquals(3, listResult.getData().size());
        
        // Test with custom object
        class TestObject {
            private String value;
            public TestObject(String value) { this.value = value; }
            public String getValue() { return value; }
        }
        Result<TestObject> customResult = new Result<>(200, "OK", new TestObject("test"));
        assertEquals("test", customResult.getData().getValue());
    }

    @Test
    public void testDefaultMessageValue() {
        // Test default message when not specified
        Result<String> result1 = new Result<>(200, "data");
        assertEquals("success", result1.getMsg());
        
        // Test default message is not used when explicitly set
        Result<String> result2 = new Result<>(200, "custom message", "data");
        assertEquals("custom message", result2.getMsg());
        
        // Test default message with null
        Result<String> result3 = new Result<>(200, null, "data");
        assertNull(result3.getMsg());
    }

    @Test
    public void testEqualsWithNullFields() {
        Result<String> result1 = new Result<>();
        Result<String> result2 = new Result<>();
        
        // Test equals with both objects having null fields
        assertTrue(result1.equals(result2));
        
        // Test equals with one object having null data
        result1.setCode(200);
        result1.setMsg("OK");
        result2.setCode(200);
        result2.setMsg("OK");
        assertTrue(result1.equals(result2));
        
        // Test equals with different null fields
        result1.setData("data");
        assertFalse(result1.equals(result2));
    }

    @Test
    public void testHashCodeWithNullFields() {
        Result<String> result1 = new Result<>();
        Result<String> result2 = new Result<>();
        
        // Test hashCode with null fields
        assertEquals(result1.hashCode(), result2.hashCode());
        
        // Test hashCode with same non-null values
        result1.setCode(200);
        result1.setMsg("OK");
        result2.setCode(200);
        result2.setMsg("OK");
        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void testResponseStaticMethodEdgeCases() {
        // Test with all null values
        Result<String> result1 = Result.response(0, null, null);
        assertEquals(0, result1.getCode());
        assertNull(result1.getMsg());
        assertNull(result1.getData());
        
        // Test with empty string message
        Result<String> result2 = Result.response(200, "", "data");
        assertEquals("", result2.getMsg());
        
        // Test with negative code
        Result<String> result3 = Result.response(-1, "Error", "data");
        assertEquals(-1, result3.getCode());
    }

    @Test
    public void testConstructorEdgeCases() {
        // Test constructor with negative code
        Result<String> result1 = new Result<>(-1, "data");
        assertEquals(-1, result1.getCode());
        assertEquals("success", result1.getMsg());
        
        // Test constructor with zero code
        Result<String> result2 = new Result<>(0, "data");
        assertEquals(0, result2.getCode());
        
        // Test constructor with maximum integer code
        Result<String> result3 = new Result<>(Integer.MAX_VALUE, "data");
        assertEquals(Integer.MAX_VALUE, result3.getCode());
    }

    @Test
    public void testToStringWithNullFields() {
        Result<String> result = new Result<>();
        String toStringOutput = result.toString();
        assertNotNull(toStringOutput);
        assertTrue(toStringOutput.contains("code=0"));
        assertTrue(toStringOutput.contains("msg=success"));
        assertTrue(toStringOutput.contains("data=null"));
    }

    @Test
    public void testEqualsWithDifferentTypes() {
        Result<String> stringResult = new Result<>(200, "OK", "data");
        Result<Integer> intResult = new Result<>(200, "OK", 42);
        
        // Test equals with different generic types
        assertFalse(stringResult.equals(intResult));
        
        // Test equals with null
        assertFalse(stringResult.equals(null));
        
        // Test equals with different class
        assertFalse(stringResult.equals("not a Result"));
    }

    @Test
    public void testEqualsWithDifferentCode() {
        Result<String> result1 = new Result<>(200, "OK", "data");
        Result<String> result2 = new Result<>(404, "OK", "data");
        assertFalse(result1.equals(result2));
    }

    @Test
    public void testEqualsWithDifferentMsg() {
        Result<String> result1 = new Result<>(200, "OK", "data");
        Result<String> result2 = new Result<>(200, "Not OK", "data");
        assertFalse(result1.equals(result2));
    }

    @Test
    public void testEqualsWithDifferentData() {
        Result<String> result1 = new Result<>(200, "OK", "data1");
        Result<String> result2 = new Result<>(200, "OK", "data2");
        assertFalse(result1.equals(result2));
    }

    @Test
    public void testEqualsWithSelf() {
        Result<String> result = new Result<>(200, "OK", "data");
        assertTrue(result.equals(result));
    }

    @Test
    public void testHashCodeConsistency() {
        Result<String> result = new Result<>(200, "OK", "data");
        int hashCode1 = result.hashCode();
        int hashCode2 = result.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testHashCodeWithDifferentObjects() {
        Result<String> result1 = new Result<>(200, "OK", "data");
        Result<String> result2 = new Result<>(200, "OK", "data");
        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void testToStringWithAllFields() {
        Result<String> result = new Result<>(200, "OK", "data");
        String toStringOutput = result.toString();
        assertTrue(toStringOutput.contains("code=200"));
        assertTrue(toStringOutput.contains("msg=OK"));
        assertTrue(toStringOutput.contains("data=data"));
    }

    @Test
    public void testResponseWithNullData() {
        Result<String> result = Result.response(200, "OK", null);
        assertEquals(200, result.getCode());
        assertEquals("OK", result.getMsg());
        assertNull(result.getData());
    }

    @Test
    public void testResponseWithEmptyMsg() {
        Result<String> result = Result.response(200, "", "data");
        assertEquals(200, result.getCode());
        assertEquals("", result.getMsg());
        assertEquals("data", result.getData());
    }

    @Test
    public void testConstructorWithNullData() {
        Result<String> result = new Result<>(200, null);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMsg());
        assertNull(result.getData());
    }

    @Test
    public void testAllArgsConstructorWithNullValues() {
        Result<String> result = new Result<>(0, null, null);
        assertEquals(0, result.getCode());
        assertNull(result.getMsg());
        assertNull(result.getData());
    }

    @Test
    public void testNoArgsConstructor() {
        Result<String> result = new Result<>();
        assertEquals(0, result.getCode());
        assertEquals("success", result.getMsg());
        assertNull(result.getData());
    }
}
