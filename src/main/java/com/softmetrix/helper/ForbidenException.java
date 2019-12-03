
package com.softmetrix.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbidenException extends RuntimeException{
	private static final long serialVersionUID = -3759092413652966469L;

	public ForbidenException(String message) {
        super(message);
    }
    
}
