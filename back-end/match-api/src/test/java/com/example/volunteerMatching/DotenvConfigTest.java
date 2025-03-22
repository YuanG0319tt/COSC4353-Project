package com.example.volunteerMatching;

import com.example.volunteerMatching.config.DotenvConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class DotenvConfigTest {

    @Test
    public void testLoadEnvVariables() {
        // Arrange
        DotenvConfig config = new DotenvConfig();

        // Act
        ReflectionTestUtils.invokeMethod(config, "loadEnvVariables");

        // Assert
        assertNotNull(System.getProperty("DB_HOST"));
        assertNotNull(System.getProperty("DB_USER"));
        assertNotNull(System.getProperty("DB_PASSWORD"));
        assertNotNull(System.getProperty("DB_NAME"));
    }
}