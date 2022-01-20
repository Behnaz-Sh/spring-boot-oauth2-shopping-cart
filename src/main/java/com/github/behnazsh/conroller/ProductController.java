package com.github.behnazsh.conroller;

import com.github.behnazsh.common.ExceptionResponse;
import com.github.behnazsh.common.RestUtil;
import com.github.behnazsh.conroller.model.ProductDto;
import com.github.behnazsh.dao.entity.Product;
import com.github.behnazsh.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Behnaz Sh
 */

@RestController
@RequestMapping(value = "/behnazsh/v1/product")
@Api(tags = {"product"})
public class ProductController {
    private static final Logger LOG = getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageSource messageSource;

    @PostMapping(consumes = {"application/json"} , produces = {"application/json"})
    @ApiOperation(value = "Create a product", notes = "Returns created product")
    public ResponseEntity createProduct(@RequestBody @Valid ProductDto productDto) {
        Product crProduct = productService.saveProduct(modelMapper.map(productDto, Product.class));
        LOG.info("Created product with id: {}" , crProduct.getId());
        return ResponseEntity.ok(modelMapper.map(crProduct , ProductDto.class));
    }

    @GetMapping(value="/{id}", produces = {"application/json"})
    @ApiOperation(value = "Get a single product", notes = "You should provide a valid product id")
    public ResponseEntity getProduct(@PathVariable("id") Long id){
        Optional<Product> product= RestUtil.checkResourceAvailable(productService.findProductById(id),
                messageSource.getMessage("product.notFound", null, Locale.ENGLISH));
        return ResponseEntity.ok(modelMapper.map(product.orElse(new Product()), ProductDto.class));
    }

    @GetMapping(value="/code/{code}", produces = {"application/json"})
    @ApiOperation(value = "Get a single product by code", notes = "You should provide a valid product code")
    public ResponseEntity getProductByCode(@PathVariable String code){
        Optional<Product> product= RestUtil.checkResourceAvailable(productService.findProductByCode(code),
                messageSource.getMessage("product.notFound", null, Locale.ENGLISH));
        return ResponseEntity.ok(modelMapper.map(product.orElse(new Product()), ProductDto.class));
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"} , produces = {"application/json"})
    @ApiOperation(value = "Update a product", notes = "Returns updated product")
    public ResponseEntity updateProduct(@RequestBody @Valid ProductDto productDto) {
        RestUtil.checkResourceAvailable(productService.findProductById(productDto.getId()),
                messageSource.getMessage("product.notFound", null, Locale.ENGLISH));
        Product edProduct = productService.saveProduct(modelMapper.map(productDto, Product.class));
        LOG.info("Updated product details for id: {}" , edProduct.getId());
        return ResponseEntity.ok(modelMapper.map(edProduct, ProductDto.class));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @ApiOperation(value = "Delete a product", notes = "You should provide a valid product Id")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> product= RestUtil.checkResourceAvailable(productService.findProductById(id),
                messageSource.getMessage("product.notFound", null, Locale.ENGLISH));
        if (null != productService.cartHasItem(product.get().getId())) {
            ExceptionResponse response = new ExceptionResponse();
            response.setException(HttpStatus.FORBIDDEN.name());
            response.setMessage(Collections.singletonList(messageSource.getMessage("deleteForbidden", null, Locale.ENGLISH)));
            response.setDetails("");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        productService.deleteProduct(product.get().getId());
        LOG.info("Deleted product details for id: {}" , product.get().getId());
        return new ResponseEntity<>(OK);
    }
}