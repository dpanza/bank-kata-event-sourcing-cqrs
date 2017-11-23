package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

public class FileAccountRepositoryShould {

    private static final String TARGET_ACCOUNT_EVENTS_TXT = "./target/account-events.txt";

    @Before
    public void setUp() throws Exception {
        Files.createFile(Paths.get(TARGET_ACCOUNT_EVENTS_TXT));
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(Paths.get(TARGET_ACCOUNT_EVENTS_TXT));
    }

    @Test
    public void persist_an_account_event_in_file() throws Exception {
        UUID uuid = UUID.fromString("5343478c-812a-49c5-acbc-d75ce70acdff");
        Account account = spy(Account.with(AccountId.create(uuid)));
        account.deposit(Amount.of(10.0));

        FileAccountRepository fileAccountRepository = new FileAccountRepository(TARGET_ACCOUNT_EVENTS_TXT);
        fileAccountRepository.save(account);

        assertThat(new File(TARGET_ACCOUNT_EVENTS_TXT))
                .hasContent(
                        "NewAccountCreated;{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"}}\n" +
                        "NewDepositMade;{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"},\"amount\":{\"value\":10.0}}\n");
    }

}