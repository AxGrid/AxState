package com.axgrid.state.queue.service;

import com.axgrid.state.dto.AxStateContext;
import com.axgrid.state.queue.dto.AxQueueDBMessage;
import com.axgrid.state.queue.dto.AxQueueMessage;
import com.axgrid.state.queue.dto.IAxQueueMessageFilter;
import com.axgrid.state.queue.exception.AxQueueConvertException;
import com.axgrid.state.queue.repository.AxQueueRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class AxQueueService<T extends AxQueueMessage, C extends AxStateContext> {

    final List<IAxQueueMessageFilter<T, C>> filters;
    final ObjectMapper objectMapper;

    @Autowired
    AxQueueRepository repository;

    public abstract Class<T> getXType();

    public abstract List<IAxQueueMessageFilter<T, C>> getFilters();

    private T fromDb(AxQueueDBMessage msg) {
        try {
            T m = objectMapper.readValue(msg.getMessageJson(), getXType());
            m.setId(msg.getId());
            m.setTube(msg.getTube());
            return m;
        }catch (JsonProcessingException e) {
            log.error("Json decode exception: {}", e.getMessage());
            throw new AxQueueConvertException(e);
        }
    }

    public List<T> get(C context, long lastId) {
        return repository.getAll(context.getQueueTube(), lastId)
                .stream()
                .map(this::fromDb)
                .filter(item -> getFilters().stream().allMatch(filter -> filter.isValid(item, context)))
                .map(item -> getFilters().stream().reduce(item, (currentItem, filter) -> filter.transform(currentItem, context), (a,b) -> b))
                .collect(Collectors.toList());
    }

    public T put(C context, T message) {
        try {
            AxQueueDBMessage dbMessage = new AxQueueDBMessage();
            dbMessage.setTube(context.getQueueTube());
            dbMessage.setMessageJson(objectMapper.writeValueAsString(message));
            dbMessage = repository.create(dbMessage);
            message.setId(dbMessage.getId());
            return message;
        }catch (JsonProcessingException e) {
            log.error("Json encode exception: {}", e.getMessage());
            throw new AxQueueConvertException(e);
        }
    }

    public void clear(C context) {
        clear(context.getQueueTube());
    }

    public void clear(String tube) {
        repository.delete(tube);
    }


    protected AxQueueService() {
        this.filters = getFilters();
        this.objectMapper = new ObjectMapper();
    }

}
