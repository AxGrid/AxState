package com.axgrid.state.join.service;

import com.axgrid.state.dto.AxJoin;
import com.axgrid.state.dto.AxJoinGroup;
import com.axgrid.state.join.repository.AxJoinRepository;
import com.axgrid.state.service.AxBotService;
import com.axgrid.state.service.AxJoinListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
public class AxJoinService {

    final List<AxJoinListener> listeners;

    @Autowired
    AxJoinRepository joinRepository;

    private Long getStateId(AxJoinGroup group, List<AxJoin> joinPlayers) {
        for(AxJoinListener l : listeners) {
            Long res = l.createState(group, joinPlayers);
            if (res != null) return res;
        }
        return null;
    }

    @Scheduled(fixedDelayString = "${ax.join.interval:2000}")
    protected void matchmaking() {
        List<AxJoinGroup> groups = joinRepository.getGroups();
        for(AxJoinGroup group : groups) {
            Queue<AxJoin> queue = new LinkedBlockingQueue<>(joinRepository.getAll(group.getType(), group.getPlayerCount()));
            while (queue.size() > 0) {
                List<AxJoin> joinPlayers = new ArrayList<>();
                while (joinPlayers.size() < group.getPlayerCount() && queue.size() > 0) {
                    joinPlayers.add(queue.poll());
                }
                Long stateId = getStateId(group, joinPlayers);
                if (log.isDebugEnabled()) log.debug("Create new state {} for {}.{} players: {}/{}", stateId, group.getType(), group.getCount(), joinPlayers, group.getPlayerCount());
                if (stateId != null) {
                    joinPlayers.forEach(player -> {
                        player.setStateId(stateId);
                        joinRepository.save(player);
                    });
                }
            }
        }
    }

    @Autowired(required = false)
    public AxJoinService(List<AxJoinListener> listeners) {
        if (listeners == null)
            this.listeners = Collections.emptyList();
        else
            this.listeners = listeners;
    }

}
