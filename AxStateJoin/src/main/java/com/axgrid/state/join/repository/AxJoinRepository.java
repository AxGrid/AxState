package com.axgrid.state.join.repository;

import com.axgrid.state.dto.AxJoin;
import com.axgrid.state.dto.AxJoinGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AxJoinRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public AxJoin save(AxJoin join) {
        jdbcTemplate.update("INSERT INTO ax_join (user_id, `type`, `state_id`, `update_time`) VALUES (?,?,?,?) " +
                "ON DUPLICATE KEY UPDATE `type`=VALUES(type), `state_id`=VALEUS(state_id), `update_time`=VALUES(`update_time`)",
                join.getUserId(), join.getType(), join.getStateId(), new Date().getTime());
        return join;
    }

    public List<AxJoinGroup> getGroups() {
        return jdbcTemplate.query("SELECT count(*) as count, type, player_count FROM ax_join WHERE state_id IS NOT NULL AND update_time>? AND count >= player_count GROUP BY `type`, `player_count`",
                new AxJoinGroup.Mapper(), new Date().getTime() - 10_000);
    }

    public AxJoin get(long userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT user_id, `type`, `state_id`, `update_time` FROM ax_join WHERE user_id=?", new AxJoinMapper(), userId);
        }catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public List<AxJoin> getAll(String type, int playerCount) {
        return jdbcTemplate.query("SELECT user_id, `type`, `state_id`, `update_time` FROM ax_join WHERE state_id IS NULL AND update_time>? AND type=? AND player_count=? LIMIT 1000",
                new AxJoinMapper(), type, playerCount, new Date().getTime() - 10_000);
    }

    public void delete(long userId) {
        jdbcTemplate.update("DELETE FROM ax_join WHERE user_id=?", userId);
    }

    public void deleteByRoom(long stateId) {
        jdbcTemplate.update("DELETE FROM ax_join WHERE state_id=?", stateId);
    }

    public void clean() {
        jdbcTemplate.update("DELETE FROM ax_join WHERE (update_time>? AND state_id IS NULL) OR (update_time>? AND state_id IS NOT NULL)",
                new Date().getTime() - 10_000, new Date().getTime() - (1000 * 60 * 60 * 12));
    }
}
