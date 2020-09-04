package com.axgrid.state.demo;

import com.axgrid.state.dto.AxStateTransaction;
import lombok.Data;
import lombok.val;

@Data
public class AxDemoTurnTransaction extends AxStateTransaction<AxDemoState, AxDemoContext> {

    long stateId;
    int playerIndex;
    int item;



    public AxDemoTurnTransaction(
            long stateId,
            int playerIndex,
            int item
    ) {
       this.stateId = stateId;
       this.playerIndex = playerIndex;
       this.item = item;
    }

    @Override
    public long getStateId(AxDemoContext context) {
        return stateId;
    }

    @Override
    public void apply(AxDemoState state, AxDemoContext context) {
        val _item = state.players.get(playerIndex).get(item);
        state.players.get(playerIndex).remove(_item);
        state.table.add(_item);
    }
}
