package org.gl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonMergePatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import java.util.function.BiFunction;
@ApplicationScoped
public class JsonMergePatchMapper<T> implements BiFunction<T, JsonMergePatch, T> {

    private ObjectMapper objectMapper;

    public JsonMergePatchMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public T apply(T targetBean, JsonMergePatch patch) {
        // Convert the Java bean to a JSON document
        JsonStructure target = objectMapper.convertValue(targetBean, JsonStructure.class);

        // Apply the JSON Patch to the JSON document
        JsonValue patched = patch.apply(target);

        // Convert the JSON document to a Java bean and return it
        return objectMapper.convertValue(patched, (Class<T>)targetBean.getClass());
    }

}
