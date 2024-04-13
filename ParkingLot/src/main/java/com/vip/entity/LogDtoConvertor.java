package com.vip.entity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.vip.dto.Logs;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
@Slf4j
public class LogDtoConvertor implements AttributeConverter<List<Logs>,String> {

    @Override
    public String convertToDatabaseColumn(List<Logs> logs) {
        if(logs==null) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(logs);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException",e);
            return null;
        }
    }

    @Override
    public List<Logs> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class,Logs.class);
            return new ObjectMapper().readValue(dbData,typeReference);
        } catch (JsonParseException e) {
            log.error("JsonProcessingException",e);
        }
        catch (JsonMappingException e) {
            log.error("JsonMappingException",e);
        }
        catch (IOException e) {
            log.error("IOException",e);
        }
        return null;
    }
}
