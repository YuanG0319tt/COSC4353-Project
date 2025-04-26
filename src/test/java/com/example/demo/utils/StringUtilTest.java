package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {

    @Test
    public void testIsNotNullOrEmpty() {
        // Test with null string
        assertFalse(StringUtil.isNotNullOrEmpty(null));
        
        // Test with empty string
        assertFalse(StringUtil.isNotNullOrEmpty(""));
        
        // Test with whitespace string
        assertTrue(StringUtil.isNotNullOrEmpty(" "));
        
        // Test with single character
        assertTrue(StringUtil.isNotNullOrEmpty("a"));
        
        // Test with multiple characters
        assertTrue(StringUtil.isNotNullOrEmpty("abc"));
        
        // Test with special characters
        assertTrue(StringUtil.isNotNullOrEmpty("!@#$%^&*()"));
        
        // Test with unicode characters
        assertTrue(StringUtil.isNotNullOrEmpty("你好"));
        
        // Test with newline characters
        assertTrue(StringUtil.isNotNullOrEmpty("\n"));
        
        // Test with tab characters
        assertTrue(StringUtil.isNotNullOrEmpty("\t"));
        
        // Test with carriage return
        assertTrue(StringUtil.isNotNullOrEmpty("\r"));
        
        // Test with combination of whitespace characters
        assertTrue(StringUtil.isNotNullOrEmpty(" \t\n\r"));
        
        // Test with very long string
        assertTrue(StringUtil.isNotNullOrEmpty("a".repeat(1000)));
    }
} 