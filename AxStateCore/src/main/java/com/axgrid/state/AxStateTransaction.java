package com.axgrid.state;

public abstract class AxStateTransaction<T extends AxState, C extends AxStateContext> {
    public abstract long getStateId();
    public abstract void apply(T state, C context);
    public void postEffect(T state, C context) {}
}
