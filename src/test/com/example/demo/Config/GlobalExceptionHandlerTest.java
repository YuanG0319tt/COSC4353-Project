package com.example.demo.Config;

import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    @Test
    public void testValidExceptionHandler() {
        // Instantiate the GlobalExceptionHandler
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Create a mock HTTP request.
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Set up an InputException with an error message.
        String expectedErrorMessage = "Test error occurred";
        // Using the existing InputException. The errorCode here is just an example.
        InputException exception = new InputException(400, expectedErrorMessage);

        // Invoke the exception handler method
        Result result = handler.validExceptionHandler(request, exception);

        // Verify that a Result object is returned
        assertNotNull(result);

        // Verify that the result's code matches HttpStatus.FAILED
        assertEquals(HttpStatus.FAILED, result.getCode());

        // Verify that the result's message matches HttpMessage.SYSTEM_ERROR
        assertEquals(HttpMessage.SYSTEM_ERROR, result.getMsg());

        // Verify that the data returned is the error message from the exception
        assertEquals(expectedErrorMessage, result.getData());
    }
}
