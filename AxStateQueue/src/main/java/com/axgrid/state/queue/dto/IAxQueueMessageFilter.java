package com.axgrid.state.queue.dto;

import com.axgrid.state.dto.AxStateContext;

public interface IAxQueueMessageFilter<T extends AxQueueMessage, C extends AxStateContext> {
    boolean isValid(T message, C context);
    T transform(T message, C context);
}
