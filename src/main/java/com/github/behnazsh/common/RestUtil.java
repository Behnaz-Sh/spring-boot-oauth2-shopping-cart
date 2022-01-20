package com.github.behnazsh.common;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

/**
 * @author Behnaz Sh
 */

public class RestUtil {

    private RestUtil() {
    }

    public static <T> T checkResourceAvailable(final T resource, final String message) {
        if (resource == null || resource.equals(Optional.empty())) {
            throw new ResourceNotFoundException(message);
        }
        return resource;
    }
}