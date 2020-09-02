package com.axgrid.state.repository;

import com.axgrid.state.AxState;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AxStateConcurrentHashMapRepository<T extends AxState> implements AxStateRepository<T> {

    AtomicLong lastId = new AtomicLong(0);
    Map<Long, String> data = new ConcurrentHashMap<>();

    private Class<T> clazz;

    public void save(AxState state) {
        try {
            if (state.getId() == null) {
                state.setId(lastId.incrementAndGet());
                String json = state.encode();
                data.compute(state.getId(), (k, v) -> json);
            } else {
                String json = state.encode();
                data.compute(state.getId(), (k, v) -> json);
            }
        } catch (JsonProcessingException e) {
        }
    }

    public T get(long stateId) {
        String json = data.getOrDefault(stateId, null);
        try {
            return T.decode(json, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public void delete(long stateId) {
        data.remove(stateId);
    }

    protected AxStateConcurrentHashMapRepository() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
