package com.axgrid.state.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AxJoin implements Serializable {
    long userId;
    String type;
    int playerCount;
    Long stateId;
    long updateTime;
}
