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

        // Origins
        assertEquals(
                1,
                cfg.getAllowedOrigins().size(),
                "Expected exactly one allowed origin"
        );
        assertTrue(
                cfg.getAllowedOrigins().contains("http://localhost:63342"),
                "Allowed origins did not contain http://localhost:63342"
        );

        // Methods
        assertEquals(
                4,
                cfg.getAllowedMethods().size(),
                "Expected exactly four allowed methods"
        );
        assertTrue(cfg.getAllowedMethods().contains("GET"));
        assertTrue(cfg.getAllowedMethods().contains("POST"));
        assertTrue(cfg.getAllowedMethods().contains("PUT"));
        assertTrue(cfg.getAllowedMethods().contains("DELETE"));
    }
}

