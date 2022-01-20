package com.github.behnazsh.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Behnaz Sh
 */

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public UnauthorizedUserException(String message) {
        super(message);
    }
}
