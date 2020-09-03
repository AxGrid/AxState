package com.axgrid.state.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AxStateContainer {
    long id;
    String json;
    int status;
}
