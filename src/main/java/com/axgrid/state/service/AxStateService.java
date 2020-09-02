package com.axgrid.state.service;

import com.axgrid.state.AxState;
import com.axgrid.state.AxStateContext;
import com.axgrid.state.AxStateTransaction;
import com.axgrid.state.exceptions.AxStateNotFoundException;
import com.axgrid.state.repository.AxStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;
import java.util.List;


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

    public long getStateId(AxStateTransaction<T, C> transaction, C context) { return transaction.getStateId(); }

    public void applyTransaction(AxStateTransaction<T, C> transaction, C context) {
        T state = get(getStateId(transaction, context));
        transaction.apply(state, context);
        if (stateTransactionListeners != null) stateTransactionListeners.forEach(listener -> listener.process(transaction, state, context));
        transaction.postEffect(state, context);
        save(state);
    }

    protected AxStateService() {
        this.stateClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
