package com.github.behnazsh;

import com.github.behnazsh.common.ExceptionResponse;
import com.github.behnazsh.conroller.model.ProductDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

/**
 * @author Behnaz Sh
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTests {

    private URI uri;
    private HttpHeaders headers = new HttpHeaders();
    private ProductDto productDto = mockProduct_Create();

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Before
    public void initTests() throws Exception {
        final String baseUrl = "http://localhost:"+ randomServerPort +"/behnazsh/v1/product";
        uri = new URI(baseUrl);
        headers.set("X-COM-PERSIST", "true");
    }

    @Test
    public void testCreateUpdateDeleteProductSuccess() {
        HttpEntity<ProductDto> request;
        ResponseEntity<ProductDto> response;

        request = new HttpEntity<>(productDto, headers);
        response = this.restTemplate.exchange(uri, HttpMethod.POST, request, ProductDto.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        productDto.setCode(response.getBody().getCode());
        productDto.setId(response.getBody().getId());
        productDto.setDesc("Pen-Cat-Two");
        request = new HttpEntity<>(productDto, headers);
        response= this.restTemplate.exchange(uri + "/1", HttpMethod.PUT, request, ProductDto.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        response= this.restTemplate.exchange(uri + "/1", HttpMethod.DELETE, request, ProductDto.class);
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        ResponseEntity<ExceptionResponse> responseEx = this.restTemplate.exchange(uri + "/1", HttpMethod.GET, request, ExceptionResponse.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEx.getStatusCode());
    }

    @Test
    public void testCreateProductAlreadyNameExist() {
        HttpEntity<ProductDto> request;
        ResponseEntity<String> response;

        productDto.setId((long) 2);
        productDto.setName("Note");

        request = new HttpEntity<>(productDto, headers);
        response = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        productDto.setId((long) 3);
        productDto.setName ("Note");

        request = new HttpEntity<>(productDto, headers);
        response = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateProductInvalidName() {
        HttpEntity<ProductDto> request;
        ResponseEntity<String> response;

        productDto.setName(null);
        request = new HttpEntity<>(productDto, headers);
        response = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        productDto.setName("");
        request = new HttpEntity<>(productDto, headers);
        response = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateProductInvalidPrice() {
        HttpEntity<ProductDto> request;
        ResponseEntity<String> response;

        productDto.setPrice(null);
        request = new HttpEntity<>(productDto, headers);
        response = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        productDto.setPrice(0.00);
        request = new HttpEntity<>(productDto, headers);
        response = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private ProductDto mockProduct_Create() {
        ProductDto productDto = new ProductDto();
        productDto.setId((long) 1);
        productDto.setVersion((long) 0);
        productDto.setName("Pen");
        productDto.setPrice(Double.parseDouble("10.99"));
        productDto.setDesc("Pen-Cat-One");
        return productDto;
    }
}