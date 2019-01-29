package com.desperado.chapter1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Chapter1ApplicationTests {

    @LocalServerPort
    private int port;
    private URL base;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Before
    public void setup() throws Exception{
        this.base = new URL("http://localhost:"+port+"/chapter1/demo1");
    }
    @Test
    public void contextLoads() {
        ResponseEntity<String> entity = testRestTemplate.getForEntity(base.toString(), String.class);
        assertEquals(entity.getBody(),"hello desperado");
    }


}

