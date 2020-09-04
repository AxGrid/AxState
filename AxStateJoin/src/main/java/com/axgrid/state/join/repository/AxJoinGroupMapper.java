package com.axgrid.state.join.repository;

import com.axgrid.state.dto.AxJoinGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AxJoinGroupMapper implements RowMapper<AxJoinGroup> {

    @Override
    public AxJoinGroup mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AxJoinGroup(
                resultSet.getLong("count"),
                resultSet.getString("type"),
                resultSet.getInt("player_count")
        );
    }
}
