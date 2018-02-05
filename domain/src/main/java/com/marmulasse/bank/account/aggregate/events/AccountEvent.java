package com.marmulasse.bank.account.aggregate.events;

public interface AccountEvent<P> {
    P apply(P projection);
}
