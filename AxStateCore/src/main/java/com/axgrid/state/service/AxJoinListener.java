package com.axgrid.state.service;

import com.axgrid.state.dto.AxJoin;
import com.axgrid.state.dto.AxJoinGroup;

import java.util.List;

public interface AxJoinListener {
    Long createState(AxJoinGroup group, List<AxJoin> joins);
}
