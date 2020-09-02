package com.axgrid.state.service;

import com.axgrid.state.AxState;
import com.axgrid.state.AxStateContext;
import com.axgrid.state.AxStateTransaction;

public interface AxStateTransactionListener<T extends AxState, C extends AxStateContext> {
    void process(AxStateTransaction<T, C> transaction, T state, C context);
}
