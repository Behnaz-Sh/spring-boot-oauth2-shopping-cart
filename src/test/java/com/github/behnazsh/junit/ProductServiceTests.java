package com.github.behnazsh.junit;

import com.github.behnazsh.dao.entity.Product;
import com.github.behnazsh.dao.repository.ProductRepository;
import com.github.behnazsh.service.ProductService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Behnaz Sh
 */
public class ProductServiceTests {

    private ProductService productService;
    private ProductRepository productRepositoryMock;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Before
    public void setUp() {
        productRepositoryMock = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepositoryMock);
    }

    @Test
    public void createProductSuccessfully() throws Exception {
        when(productRepositoryMock.findById(eq(Long.parseLong("1")))).thenReturn(Optional.empty());
        doAnswer(returnsFirstArg()).when(productRepositoryMock).save(any(Product.class));

        Product product = productService.saveProduct(mockCreateProduct());
        assertEquals("Book", product.getName());
        assertNotNull(product.getCode());

        assertNotNull(productService.findProductById(product.getId()));
        productService.deleteProduct(product.getId());
        assertFalse(productService.findProductById(product.getId()).isPresent());
    }

    private Product mockCreateProduct() {
        Product product = new Product();
        product.setId(Long.parseLong("1"));
        product.setVersion(Long.parseLong("0"));
        product.setCode("P1000000001");
        product.setName("Book");
        product.setDesc("Hello");
        product.setPrice(Double.parseDouble("10.99"));
        return product;
    }

}