package com.axgrid.state.jdbc.repository;

import com.axgrid.state.AxState;
import com.axgrid.state.jdbc.dto.AxStateContainer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AxDataRowMapper implements RowMapper<AxStateContainer> {

    @Override
    public AxStateContainer mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AxStateContainer(
                resultSet.getLong("id"),
                resultSet.getString("data"),
                resultSet.getInt("status")
        );
    }

}
