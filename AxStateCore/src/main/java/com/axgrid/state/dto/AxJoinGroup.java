package com.axgrid.state.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AxJoinGroup {
    long count;
    String type;
    int playerCount;
}
