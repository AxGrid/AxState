package com.axgrid.state.queue.repository;

import com.axgrid.state.queue.dto.AxQueueDBMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class AxQueueRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public AxQueueDBMessage create(AxQueueDBMessage message) {
        final String insertSQL = "INSERT INTO axq_message (tube, message) VALUES (?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, message.getTube());
            ps.setString(2, message.getMessageJson());
            return ps;
        }, keyHolder);

        message.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return message;
    }

    public List<AxQueueDBMessage> getAll(String tube, long lastId) {
        return jdbcTemplate.query("SELECT id, tube, message FROM axq_message WHERE tube=? AND id>? ORDER BY id LIMIT 1000",
                new Object[] {tube, lastId},
                new AxQueueDBMessage.Mapper());
    }

    public void delete(String tube) {
        jdbcTemplate.update("DELETE FROM axq_message WHERE tube=?", tube);
    }

    public void cleanup() {
        //TODO:
    }

}
