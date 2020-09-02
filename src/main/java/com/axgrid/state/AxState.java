package com.axgrid.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AxState {

    Long id;

    static final ObjectMapper mapper = new ObjectMapper();

    public static <T extends AxState> T decode(String data, Class<T> clazz) throws JsonProcessingException {
        return (T)mapper.readValue(data,clazz);
    }

    public String encode() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }
}
