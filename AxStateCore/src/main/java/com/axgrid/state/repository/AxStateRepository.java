package com.axgrid.state.repository;

import com.axgrid.state.dto.AxState;

import java.util.List;

public interface AxStateRepository<T extends AxState> {
    T save(T state);
    T get(long stateId);
    void delete(long stateId);
    List<T> getAll(int status);
}
