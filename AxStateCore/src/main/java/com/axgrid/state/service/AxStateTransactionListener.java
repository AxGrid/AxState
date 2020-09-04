package com.axgrid.state.service;

import com.axgrid.state.dto.AxState;
import com.axgrid.state.dto.AxStateContext;
import com.axgrid.state.dto.AxStateTransaction;

public interface AxStateTransactionListener<T extends AxState, C extends AxStateContext> {
    void process(AxStateTransaction<T, C> transaction, T state, C context);
}
