package com.axgrid.state.join.repository;

import com.axgrid.state.dto.AxJoin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AxJoinMapper implements RowMapper<AxJoin> {

    @Override
    public AxJoin mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AxJoin(
                resultSet.getLong("user_id"),
                resultSet.getString("type"),
                resultSet.getInt("player_count"),
                resultSet.getObject("state_id", Long.class),
                resultSet.getLong("update_time")
        );
    }
}
