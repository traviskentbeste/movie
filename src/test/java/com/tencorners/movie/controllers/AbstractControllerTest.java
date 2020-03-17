package com.tencorners.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractControllerTest {

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
