package com.axgrid.state.queue;

import com.axgrid.state.queue.dto.AxQueueMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AxQueueCustomMessage extends AxQueueMessage {
    String playerName;
    long owner;
    String message;
}
