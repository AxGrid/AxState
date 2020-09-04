package com.axgrid.state.queue;


import com.axgrid.state.dto.AxStateContext;
import lombok.Data;

@Data
public class AxQueueContext implements AxStateContext {
    public String getQueueTube() { return "demo"; }
    long user = 0;
}
