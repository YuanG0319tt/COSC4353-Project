package com.example.demo.Config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

class InputExceptionTest {

    private InputException make(int code, String msg) {
        return new InputException(code, msg);
    }

    @Test
    void equals_reflexive_null_and_diffType() {
        InputException ex = make(400, "Bad");
        // reflexive
        assertTrue(ex.equals(ex), "an object must equal itself");
        // null
        assertFalse(ex.equals(null), "must not equal null");
        // different type
        assertFalse(ex.equals("someString"), "must not equal a different type");
    }

    @Test
    void equals_and_hashCode_for_same_fields() {
        InputException a = make(123, "Oops");
        InputException b = make(123, "Oops");
        // symmetric
        assertTrue(a.equals(b), "same field values → equals");
        assertTrue(b.equals(a), "symmetric");
        // hashCode
        assertEquals(a.hashCode(), b.hashCode(), "equal objects must have same hashCode");
    }

    @Test
    void equals_fieldMismatch() {
        InputException base = make(1, "Msg");
        // different code
        InputException diffCode = make(2, "Msg");
        assertFalse(base.equals(diffCode));
        // different message
        InputException diffMsg = make(1, "Other");
        assertFalse(base.equals(diffMsg));
    }

    @Test
    void toString_containsClassAndFields() {
        InputException ex = make(500, "Server Error");
        String s = ex.toString();
        // default Lombok toString is: InputException(errorCode=500, errorMessage=Server Error)
        assertTrue(s.contains("InputException"),       "should contain class name");
        assertTrue(s.contains("errorCode=500"),         "should contain errorCode");
        assertTrue(s.contains("errorMessage=Server Error"), "should contain errorMessage");
    }

    @Test
    void canEqual_invocation_branches() throws Exception {
        InputException ex = make(0, "x");
        Method canEqual = InputException.class.getDeclaredMethod("canEqual", Object.class);
        canEqual.setAccessible(true);
        // same class → true
        assertTrue((Boolean) canEqual.invoke(ex, new InputException()), "canEqual(InputException) should be true");
        // other type → false
        assertFalse((Boolean) canEqual.invoke(ex, "string"),       "canEqual(non‑InputException) should be false");
    }

    @Test
    void isRuntimeException() {
        InputException ex = make(0, "x");
        assertTrue(ex instanceof RuntimeException, "should extend RuntimeException");
    }


    @Test
    void testAllArgsConstructor() {
        InputException ex = new InputException(400, "Bad Request");
        assertEquals(400, ex.getErrorCode());
        assertEquals("Bad Request", ex.getErrorMessage());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        InputException ex = new InputException();
        ex.setErrorCode(404);
        ex.setErrorMessage("Not Found");

        assertEquals(404, ex.getErrorCode());
        assertEquals("Not Found", ex.getErrorMessage());
    }

    @Test
    void testIsRuntimeException() {
        InputException ex = new InputException();
        assertTrue(ex instanceof RuntimeException);
    }
}
