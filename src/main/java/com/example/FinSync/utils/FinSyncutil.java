package com.example.FinSync.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.logging.Logger;

public class FinSyncutil {

    private static final Logger logger = Logger.getLogger(FinSyncutil.class.getName());

    public static Object convertObjectToJson(Object retquestDto){
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String json = null;
        try{
            json = mapper.writeValueAsString(retquestDto);
        }catch (JsonProcessingException e){
            logger.info(" | ERROR | Convertion : OBJECT to JSON  | ");
        }
        return json;
    }
}
