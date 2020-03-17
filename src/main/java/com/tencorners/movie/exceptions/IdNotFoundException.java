package com.tencorners.movie.exceptions;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(Long id) {
        super("id not found : " + id);
    }

}
