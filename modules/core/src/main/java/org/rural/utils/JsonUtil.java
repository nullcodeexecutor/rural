package org.rural.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by yuantao on 2014/8/13.
 */
public class JsonUtil {
    private JsonUtil(){
    }

    public static String objectToString(Object value) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(value);
    }

}
