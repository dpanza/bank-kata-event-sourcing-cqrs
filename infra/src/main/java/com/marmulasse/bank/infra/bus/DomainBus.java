package com.marmulasse.bank.infra.bus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;
import com.marmulasse.bank.account.events.NewWithdrawMade;
import com.marmulasse.bank.query.account.AccountEventHandler;

import java.io.IOException;


public class DomainBus {

    private final EventBus eventBus;

    public DomainBus(AccountEventHandler accountEventHandler) {
        eventBus = new EventBus();
        eventBus.register(new AccountDomainDispatcher(accountEventHandler));
    }

    public void dispatch(AccountEvent accountEvent) {
        eventBus.post(accountEvent);
    }

    public class AccountDomainDispatcher {
        private AccountEventHandler accountEventHandler;
        private final ObjectMapper mapper;

        public AccountDomainDispatcher(AccountEventHandler accountEventHandler) {
            this.accountEventHandler = accountEventHandler;
            this.mapper = new ObjectMapper();
            this.mapper.setVisibility(this.mapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        }

        @Subscribe
        public void handle(NewAccountCreated newAccountCreated) throws IOException {
            byte[] asBytes = mapper.writeValueAsBytes(newAccountCreated);
            accountEventHandler.newAccountCreated(mapper.readValue(asBytes, com.marmulasse.bank.query.account.events.NewAccountCreated.class));
        }

        @Subscribe
        public void handle(NewDepositMade newDepositMade) throws IOException {
            byte[] asBytes = mapper.writeValueAsBytes(newDepositMade);
            accountEventHandler.aDepositMade(mapper.readValue(asBytes, com.marmulasse.bank.query.account.events.NewDepositMade.class));
        }

        @Subscribe
        public void handle(NewWithdrawMade newWithdrawMade) throws IOException {
            byte[] asBytes = mapper.writeValueAsBytes(newWithdrawMade);
            accountEventHandler.aWithdrawalMade(mapper.readValue(asBytes, com.marmulasse.bank.query.account.events.NewWithdrawMade.class));
        }
    }
}
