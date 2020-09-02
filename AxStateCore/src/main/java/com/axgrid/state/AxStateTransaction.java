package com.axgrid.state;

import com.axgrid.state.exceptions.AxStateProcessingException;

public abstract class AxStateTransaction<T extends AxState, C extends AxStateContext> {
    public abstract long getStateId();
    public abstract void apply(T state, C context);
    public void postEffect(T state, C context) {}
    public void error(Throwable t) { throw new AxStateProcessingException(t); }
}
