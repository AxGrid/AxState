package com.axgrid.state.jdbc.repository;

import com.axgrid.state.AxState;
import com.axgrid.state.exceptions.AxStateProcessingException;
import com.axgrid.state.repository.AxStateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

public abstract class AxStateJdbcRepository<T extends AxState> implements AxStateRepository<T> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private Class<T> clazz;

    @Override
    public void save(T state) {
        if (state.getId() == null) {
            create(state);
        } else {
            try {
                String json = state.encode();
                jdbcTemplate.update("UPDATE ax_state SET `data`=?, `update_time`=? WHERE id=?", json, new Date().getTime(), state.getId());
            } catch (JsonProcessingException e) {
                throw new AxStateProcessingException(e);
            }
        }
    }

    private T create(T state) {
        try {
            String json = state.encode();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO ax_state (`type`, `data`, `update_time`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, clazz.getName());
                ps.setString(2, json);
                ps.setLong(3, new Date().getTime());
                return ps;
            }, keyHolder);
            state.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        }catch (JsonProcessingException e) {
            throw new AxStateProcessingException(e);
        }
        return state;
    }


    @Override
    public T get(long id) {
        try {
            String data = jdbcTemplate.queryForObject("SELECT `data` FROM ax_state WHERE id=?", new Object[] {id}, String.class);
            T state = T.decode(data, clazz);
            state.setId(id);
            return state;
        }catch (JsonProcessingException e){
            throw new AxStateProcessingException(e);
        }catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    @Override
    public void delete(long stateId) {
        jdbcTemplate.update("DELETE FROM ax_state WHERE id=?", stateId);
    }


    protected AxStateJdbcRepository() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
