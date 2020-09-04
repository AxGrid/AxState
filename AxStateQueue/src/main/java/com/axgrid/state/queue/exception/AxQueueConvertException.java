package com.axgrid.state.queue.exception;

public class AxQueueConvertException extends AxQueueException {
    public AxQueueConvertException(Throwable t) {
        super("Convert exception", t);
    }
}
