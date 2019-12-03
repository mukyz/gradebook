package com.softmetrix.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -741819858935419935L;

	public ResourceNotFoundException(String msg){
        super(msg);
    }
}
