package com.microservices.chapter04;

import org.junit.Before;
import org.junit.Test;

public class Chapter04ApplicationTests {

    @Before
    public void setUp() {
        System.setProperty("server.port", "11212");
    }

    @Test
    public void contextLoads() {
        Chapter04Application.main(new String[]{});
    }

}
