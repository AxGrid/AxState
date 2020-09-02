package com.axgrid.state.demo;

import com.axgrid.state.AxState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AxDemoState extends AxState {

    List<List<Integer>> players = new ArrayList<>();
    List<Integer> table = new ArrayList<>();

}
