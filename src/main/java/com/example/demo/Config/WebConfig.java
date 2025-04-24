package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342")
                .allowedMethods("GET","POST","PUT","DELETE");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Path to your front-end directory (adjust if needed)
        String externalPath = Paths.get("front-end").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/**")
                .addResourceLocations(externalPath);
    }
}
