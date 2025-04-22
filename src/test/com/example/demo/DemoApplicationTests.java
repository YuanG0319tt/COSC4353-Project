package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        int[][] points = new int[3][2];
        int counter = 10;
        for (int i = 0; i < points.length; i++) {
            for (int i1 = 0; i1 < points[i].length; i1++) {
                points[i][i1] = counter--;
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                System.out.print(points[i][j] + " ");
            }
            System.out.println();
        }

    }

}
