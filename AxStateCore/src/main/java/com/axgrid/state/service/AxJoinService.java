package com.axgrid.state.service;

import com.axgrid.state.dto.AxJoin;

public interface AxJoinService {
    AxJoin save(AxJoin join);
    AxJoin get(long userId);
}
