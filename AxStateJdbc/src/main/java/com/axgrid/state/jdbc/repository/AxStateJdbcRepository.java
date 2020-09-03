package com.axgrid.state.jdbc.repository;

import com.axgrid.state.AxState;
import com.axgrid.state.exceptions.AxStateProcessingException;
import com.axgrid.state.jdbc.dto.AxStateContainer;
import com.axgrid.state.repository.AxStateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                jdbcTemplate.update("UPDATE ax_state SET `data`=?, status=?, `update_time`=? WHERE id=?", json, state.getStatus(), new Date().getTime(), state.getId());
            } catch (JsonProcessingException e) {
                throw new AxStateProcessingException(e);
            }
        }
    }

    private void create(T state) {
        try {
            String json = state.encode();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO ax_state (`type`, `data`, `status`, `update_time`) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, clazz.getName());
                ps.setString(2, json);
                ps.setInt(3, state.getStatus());
                ps.setLong(4, new Date().getTime());
                return ps;
            }, keyHolder);
            state.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        }catch (JsonProcessingException e) {
            throw new AxStateProcessingException(e);
        }
    }

    @Override
    public List<T> getAll(int status) {
        List<AxStateContainer> datas = jdbcTemplate.query("SELECT `data` FROM ax_state WHERE status=?", new AxDataRowMapper(), status);
        return datas.stream().map(sc -> {
            try {
                T state = T.decode(sc.getJson(), clazz);
                state.setId(sc.getId());
                return state;
            }catch (JsonProcessingException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
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


    @Scheduled(fixedDelay = 1000L)
    protected void cleanUp() {
        long dt = new Date().getTime() - 1000 * 60 * 60 * 12; //12 hours
        jdbcTemplate.update("DELETE FROM ax_state WHERE update_time<? LIMIT 500;", dt);
    }

    protected AxStateJdbcRepository() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
