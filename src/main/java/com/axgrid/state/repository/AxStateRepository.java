package com.axgrid.state.repository;

import com.axgrid.state.AxState;

public interface AxStateRepository<T extends AxState> {
    void  save(T state);
    T get(long stateId);
    void delete(long stateId);
}
