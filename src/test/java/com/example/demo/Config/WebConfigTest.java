package com.example.demo.Config;

import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebConfigTest {

    @Test
    void addCorsMappingsRegistersExpectedConfiguration() throws Exception {
        // 1. Arrange
        WebConfig webConfig = new WebConfig();
        CorsRegistry registry = new CorsRegistry();

        // 2. Act
        webConfig.addCorsMappings(registry);

        // Use reflection to call the protected getCorsConfigurations()
        Method getCorsConfigs = CorsRegistry.class
                .getDeclaredMethod("getCorsConfigurations");
        getCorsConfigs.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, CorsConfiguration> configs = (Map<String, CorsConfiguration>)
                getCorsConfigs.invoke(registry);

        // 3. Assert
        // Should only have one entry, for /**
        assertEquals(1, configs.size(), "Expected exactly one CORS registration");
        CorsConfiguration cfg = configs.get("/**");
        assertNotNull(cfg, "No CORS configuration found for /**");

        // Verify permissive configuration
        assertTrue(cfg.getAllowedOriginPatterns().contains("*"), "Expected wildcard origin pattern");
        assertTrue(cfg.getAllowedMethods().contains("*"), "Expected wildcard methods");
        assertTrue(cfg.getAllowedHeaders().contains("*"), "Expected wildcard headers");
        assertTrue(cfg.getAllowCredentials(), "Expected credentials to be allowed");
    }
}

