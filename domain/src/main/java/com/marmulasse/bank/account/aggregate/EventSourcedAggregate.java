package com.marmulasse.bank.account.aggregate;

import com.marmulasse.bank.account.aggregate.events.AccountEvent;

import java.util.ArrayList;
import java.util.List;

public class EventSourcedAggregate<P extends AccountProjection> {

    protected P projection;
    protected List<AccountEvent<P>> uncommittedChanged = new ArrayList<>();

    public EventSourcedAggregate(P projection) {
        this.projection = projection;
    }

    public EventSourcedAggregate(P projection, List<AccountEvent<P>> changes) {
        this.projection = changes.stream()
                .reduce(projection,
                        (p, event) -> event.apply(projection),
                        (p1, p2) -> p2);
    }

    protected void applyDecision(AccountEvent<P> event) {
        event.apply(projection);
        saveUncommittedChange(event);
    }

    public List<AccountEvent<P>> getUncommittedChanged() {
        return uncommittedChanged;
    }

    private void saveUncommittedChange(AccountEvent accountEvent) {
        this.uncommittedChanged.add(accountEvent);
    }

    public P getProjection() {
        return projection;
    }
}
