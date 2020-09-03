package com.axgrid.state.repository;

import com.axgrid.state.AxState;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public abstract class AxStateConcurrentHashMapRepository<T extends AxState> implements AxStateRepository<T> {

    AtomicLong lastId = new AtomicLong(0);
    Map<Long, String> data = new ConcurrentHashMap<>();
    Map<Integer, Set<Long>> dataStatus = new ConcurrentHashMap<>();

    private Class<T> clazz;

    public T save(T state) {
        try {
            if (state.getId() == null) {
                state.setId(lastId.incrementAndGet());
                String json = state.encode();
                data.compute(state.getId(), (k, v) -> json);

            } else {
                dataStatus.values().forEach(item -> item.remove(state.getId()));
                String json = state.encode();
                data.compute(state.getId(), (k, v) -> json);
            }
            dataStatus.compute(state.getStatus(), (k,v) -> {
                if (v == null) {
                    return new HashSet<>(Collections.singletonList(state.getId()));
                } else {
                    v.add(state.getId());
                    return v;
                }
            });
        } catch (JsonProcessingException e) {
        }
        return state;
    }

    public T get(long stateId) {
        String json = data.getOrDefault(stateId, null);
        try {
            return T.decode(json, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public List<T> getAll(int status) {
        return dataStatus.getOrDefault(status, Collections.emptySet())
                .stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long stateId) {
        dataStatus.values().forEach(item -> item.remove(stateId));
        data.remove(stateId);
    }

    protected AxStateConcurrentHashMapRepository() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
