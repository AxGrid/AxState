package com.axgrid.state.queue;

import com.axgrid.state.queue.dto.IAxQueueMessageFilter;
import org.apache.commons.lang3.StringUtils;

public class AxQueueOwnerFilter implements IAxQueueMessageFilter<AxQueueCustomMessage, AxQueueContext> {
    @Override
    public boolean isValid(AxQueueCustomMessage message, AxQueueContext context) {
        return context.getUser() < 100;
    }

    @Override
    public AxQueueCustomMessage transform(AxQueueCustomMessage message, AxQueueContext context) {
        if(message.getOwner() != context.getUser())
            message.setMessage(StringUtils.leftPad("", message.getMessage().length(), "*"));
        return message;
    }


}
