package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.BaseTest;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.factory.entity.AccountFactory;
import br.edu.ifsp.finances.factory.entity.AccountTypeFactory;
import br.edu.ifsp.finances.factory.entity.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AccountServiceTest extends BaseTest {

    private AccountFactory accountFactory;

    private AccountTypeFactory typeFactory;

    private UserFactory userFactory;

    private AccountRepository repository;

    private AccountService service;

    @Autowired
    public AccountServiceTest(final AccountFactory accountFactory,
                              final AccountTypeFactory typeFactory,
                              final UserFactory userFactory,
                              final AccountRepository repository,
                              final AccountService service) {
        this.accountFactory = accountFactory;
        this.typeFactory = typeFactory;
        this.userFactory = userFactory;
        this.repository = repository;
        this.service = service;
    }

    @Test
    public void createNewAccount(){
        var user = userFactory.create();
        var type = typeFactory.create(empty -> empty.setUser(user));

        AccountRequest request = new AccountRequest();
        request.setName("testAccount");
        request.setTypeId(type.getId());

        service.create(request, "test@email.com");

        var account = repository.findAll().get(0);

        log.info("test=createNewAccount, account={}", account);

        Assertions.assertEquals("testAccount", account.getName());
        Assertions.assertEquals("test@email.com", account.getUser().getEmail());
    }

    @Test
    public void updateAccount(){
        var user = userFactory.create();
        var differentType = typeFactory.create(empty -> {empty.setUser(user);
                                                         empty.setName("differentType");});
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });


        AccountRequest request = new AccountRequest();
        request.setTypeId(differentType.getId());
        request.setName("updatedAccount");

        service.update(account.getId(), request, "test@email.com");
        var accountUpdated = repository.findAll().get(0);

        log.info("test=updateAccount, account={}, request={}, accountUpdated={}", account, request, accountUpdated);

        Assertions.assertEquals("testAccount", account.getName());
        Assertions.assertEquals("updatedAccount", accountUpdated.getName());
        Assertions.assertEquals("differentType", accountUpdated.getType().getName());
    }

    @Test
    public void dontUpdateAccountWrongUser(){
        var user = userFactory.create();
        var differentUser = userFactory.create(empty -> {
            empty.setEmail("different@email.com");
            empty.setUsername("different");
        });

        var differentType = typeFactory.create(empty -> {empty.setUser(user);
            empty.setName("differentType");});
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });


        AccountRequest request = new AccountRequest();
        request.setTypeId(differentType.getId());
        request.setName("updatedAccount");

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.update(account.getId(), request, "different@email.com");
        });
        var accountUpdated = repository.findAll().get(0);

        log.info("test=updateAccount, account={}, request={}, accountUpdated={}", account, request, accountUpdated);

        Assertions.assertEquals("testAccount", account.getName());
        Assertions.assertEquals("testAccount", accountUpdated.getName());
        Assertions.assertEquals("testAccountType", accountUpdated.getType().getName());
    }

    @Test
    public void deleteAccount(){
        var user = userFactory.create();
        var differentType = typeFactory.create(empty -> {empty.setUser(user);
            empty.setName("differentType");});
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });


        AccountRequest request = new AccountRequest();
        request.setTypeId(differentType.getId());
        request.setName("updatedAccount");

        service.delete(account.getId(), "test@email.com");
        var accountsDeleted = repository.findAll();

        log.info("test=updateAccount, account={}, request={}, accountsDeleted={}", account, request, accountsDeleted);

        Assertions.assertEquals(0, accountsDeleted.size());
    }

    @Test
    public void dontDeleteAccountWrongUser(){
        var user = userFactory.create();
        var differentUser = userFactory.create(empty -> {
            empty.setEmail("different@email.com");
            empty.setUsername("different");
        });

        var differentType = typeFactory.create(empty -> {empty.setUser(user);
            empty.setName("differentType");});
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });


        AccountRequest request = new AccountRequest();
        request.setTypeId(differentType.getId());
        request.setName("updatedAccount");

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.delete(account.getId(), "different@email.com");
        });
        var accountsDeleted = repository.findAll();

        log.info("test=updateAccount, account={}, request={}, accountsDeleted={}", account, request, accountsDeleted);

        Assertions.assertEquals(1, accountsDeleted.size());
    }

}
