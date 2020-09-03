package com.axgrid.state.repository;

import com.axgrid.state.AxState;

import java.util.List;

public interface AxStateRepository<T extends AxState> {
    void  save(T state);
    T get(long stateId);
    void delete(long stateId);
    List<T> getAll(int status);
}
