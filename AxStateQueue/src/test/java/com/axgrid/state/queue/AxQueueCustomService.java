package com.axgrid.state.queue;

import com.axgrid.state.queue.dto.IAxQueueMessageFilter;
import com.axgrid.state.queue.service.AxQueueService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AxQueueCustomService extends AxQueueService<AxQueueCustomMessage, AxQueueContext> {
    @Override
    public Class<AxQueueCustomMessage> getXType() {
        return AxQueueCustomMessage.class;
    }

    @Override
    public List<IAxQueueMessageFilter<AxQueueCustomMessage, AxQueueContext>> getFilters() {
        return Collections.singletonList(new AxQueueOwnerFilter());
    }
}
