package com.axgrid.state.queue.exception;

public abstract class AxQueueException extends RuntimeException {
    public AxQueueException(String message) { super(message); }
    public AxQueueException(String message, Throwable t) { super(message, t); }
}
