package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.aggregate.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileAccountRepositoryShould {

    private static final String TARGET_ACCOUNT_EVENTS_TXT = "./target/account-events.txt";
    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.ofEpochMilli(1513778931434L), ZoneId.systemDefault());
    private FileAccountRepository sut;

    @Before
    public void setUp() throws Exception {
        Files.createFile(Paths.get(TARGET_ACCOUNT_EVENTS_TXT));
        sut = new FileAccountRepository(TARGET_ACCOUNT_EVENTS_TXT, FIXED_CLOCK);
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(Paths.get(TARGET_ACCOUNT_EVENTS_TXT));
    }

    @Test
    public void persist_an_account_event_in_file() throws Exception {
        Account account = AccountFactoryTest.existingAccount(AccountId.from("5343478c-812a-49c5-acbc-d75ce70acdff"));
        account.deposit(Amount.of(10.0));

        sut.save(account);

        assertThat(new File(TARGET_ACCOUNT_EVENTS_TXT))
                .hasContent(new EventDescriptor("5343478c-812a-49c5-acbc-d75ce70acdff", "NewDepositMade", 1513778931434L, "{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"},\"amount\":{\"value\":10.0}}").toString());
    }

    @Test
    public void throw_an_exception_when_file_not_found_on_save() throws Exception {
        Account account = AccountFactoryTest.existingAccount(AccountId.from("5343478c-812a-49c5-acbc-d75ce70acdff"));
        account.deposit(Amount.of(10.0));

        FileAccountRepository sut = new FileAccountRepository(TARGET_ACCOUNT_EVENTS_TXT + "fake", FIXED_CLOCK);
        assertThatThrownBy(() -> sut.save(account))
                .hasMessage("Cannot print to file ")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void return_no_account_from_file_when_account_not_in_file() throws Exception {
        Files.write(Paths.get(TARGET_ACCOUNT_EVENTS_TXT), "11111111-8275-49b1-89cd-c9bbb4e5376b;1513778931434;NewAccountCreated;{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"},\"balance\":{\"value\":0.0}}\n".getBytes());

        Optional<Account> account = sut.get(AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b"));

        assertThat(account).isNotPresent();
    }

    @Test
    public void get_an_account_from_file_containing_only_one_account() throws Exception {
        Files.write(Paths.get(TARGET_ACCOUNT_EVENTS_TXT), "568ec631-8275-49b1-89cd-c9bbb4e5376b;1513778931434;NewAccountCreated;{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"}}\n".getBytes());

        Optional<Account> account = sut.get(AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b"));

        assertThat(account).isPresent().contains(AccountFactoryTest.existingAccount(AccountId.from("5343478c-812a-49c5-acbc-d75ce70acdff")));
    }

    @Test
    public void get_an_account_from_file_containing_multiple_account() throws Exception {
        Files.write(Paths.get(TARGET_ACCOUNT_EVENTS_TXT),
                ("568ec631-8275-49b1-89cd-c9bbb4e5376b;1513778931434;NewAccountCreated;{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"}}\n" +
                        "13371337-8275-49b1-89cd-c9bbb4e6789c;1513778931434;NewAccountCreated;{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"},\"balance\":{\"value\":0.0}}").getBytes());

        Optional<Account> account = sut.get(AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b"));

        assertThat(account).isPresent().hasValue(AccountFactoryTest.existingAccount(AccountId.from("5343478c-812a-49c5-acbc-d75ce70acdff")));
    }

    @Test
    public void throw_an_exception_when_file_not_found_on_get() throws Exception {
        FileAccountRepository sut = new FileAccountRepository(TARGET_ACCOUNT_EVENTS_TXT + "fake", FIXED_CLOCK);
        Files.write(Paths.get(TARGET_ACCOUNT_EVENTS_TXT),
                ("568ec631-8275-49b1-89cd-c9bbb4e5376b;1513778931434;NewAccountCreated;{\"accountId\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"},\"balance\":{\"value\":0.0}}\n").getBytes());

        assertThatThrownBy(() -> sut.get(AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b")))
                .hasMessage("Cannot read rebuildFrom specified file in path : ./target/account-events.txtfake")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void throw_an_exception_when_event_cannot_be_deserialize_on_get() throws Exception {
        Files.write(Paths.get(TARGET_ACCOUNT_EVENTS_TXT),
                ("568ec631-8275-49b1-89cd-c9bbb4e5376b;1513778931434;NewAccountCreated;{\"\":{\"value\":\"5343478c-812a-49c5-acbc-d75ce70acdff\"}}\n").getBytes());

        assertThatThrownBy(() -> sut.get(AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b")))
                .hasMessage("Some event deserialization failed")
                .isInstanceOf(RuntimeException.class);
    }
}