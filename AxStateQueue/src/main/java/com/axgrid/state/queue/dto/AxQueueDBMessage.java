package com.axgrid.state.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AxQueueDBMessage {
    long id;
    String tube;
    String messageJson;

    public static class Mapper implements RowMapper<AxQueueDBMessage>
    {

        @Override
        public AxQueueDBMessage mapRow(ResultSet resultSet, int i) throws SQLException {
            return new AxQueueDBMessage(
                    resultSet.getLong("id"),
                    resultSet.getString("tube"),
                    resultSet.getString("message")
            );
        }
    }
}
