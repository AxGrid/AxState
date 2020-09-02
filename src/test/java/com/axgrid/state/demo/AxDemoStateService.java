package com.axgrid.state.demo;

import com.axgrid.state.service.AxStateService;
import org.springframework.stereotype.Service;

@Service
public class AxDemoStateService extends AxStateService<AxDemoState, AxDemoContext> {
    @Override
    public AxDemoState create() {
        AxDemoState ds = new AxDemoState();
        this.stateRepository.save(ds);
        return ds;
    }


}
