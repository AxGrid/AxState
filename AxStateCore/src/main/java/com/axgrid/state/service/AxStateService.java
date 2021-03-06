package com.axgrid.state.service;

import com.axgrid.state.dto.AxState;
import com.axgrid.state.dto.AxStateContext;
import com.axgrid.state.dto.AxStateTransaction;
import com.axgrid.state.exceptions.AxStateNotFoundException;
import com.axgrid.state.repository.AxStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
public abstract class AxStateService<T extends AxState, C extends AxStateContext> {

    @Autowired
    protected AxStateRepository<T> stateRepository;

    @Autowired(required = false)
    List<AxStateTransactionListener<T, C>> stateTransactionListeners;

    protected Class<T> stateClass;

    public abstract T create();

    public void save(T state) {
        stateRepository.save(state);
    }

    public T get(long id) {
        T state = stateRepository.get(id);
        if (state == null) throw new AxStateNotFoundException(id, stateClass.getName());
        return state;
    }

    public long getStateId(AxStateTransaction<T, C> transaction, C context) { return transaction.getStateId(context); }

    public void applyTransaction(AxStateTransaction<T, C> transaction, C context) {
        T state = get(getStateId(transaction, context));
        try {
            transaction.apply(state, context);
            save(state);
            transaction.postEffect(state, context);
            if (stateTransactionListeners != null)
                stateTransactionListeners.forEach(listener -> listener.process(transaction, state, context));
        }catch (Exception e) {
            if (log.isDebugEnabled()) log.debug("apply transaction error", e);
            transaction.error(e);
        }
    }

    protected AxStateService() {
        this.stateClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
