package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTest {

    /**
     * Simply attempts to start the entire Spring context.
     * This will pick up @SpringBootApplication and @EnableWebMvc on DemoApplication.
     */
    @Test
    void contextLoads() {
        // no-op: if the context fails to start, this test will fail
    }

    /**
     * Directly invokes the main() method to get coverage on it.
     */
    @Test
    void mainRuns() {
        DemoApplication.main(new String[]{});
    }
}
