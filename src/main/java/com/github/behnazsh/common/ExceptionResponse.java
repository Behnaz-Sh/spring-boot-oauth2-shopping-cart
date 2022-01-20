package com.github.behnazsh.common;

import lombok.*;

import java.util.List;

/**
 * @author Behnaz Sh
 */

@Data
public class ExceptionResponse {

    private String exception;
    private List<String> message;
    private String details;

}
