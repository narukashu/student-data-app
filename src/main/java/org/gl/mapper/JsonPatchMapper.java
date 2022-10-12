package org.gl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import java.util.function.BiFunction;
@ApplicationScoped
public class JsonPatchMapper<T> implements BiFunction<T, JsonPatch,T> {

    private ObjectMapper objectMapper;

    public JsonPatchMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public T apply(T targetBean, JsonPatch jsonPatch) {
        // Convert the Java bean to a JSON document
        JsonStructure target = objectMapper.convertValue(targetBean, JsonStructure.class);

        // Apply the JSON Patch to the JSON document
        JsonValue patched = jsonPatch.apply(target);
        return objectMapper.convertValue(patched, (Class<T>)targetBean.getClass());
    }
}
