package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.BaseTest;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.factory.entity.AccountTypeFactory;
import br.edu.ifsp.finances.factory.entity.UserFactory;
import br.edu.ifsp.finances.repository.AccountTypeRepository;
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
public class AccountTypeServiceTest extends BaseTest {

    private AccountTypeService service;

    private AccountTypeRepository repository;

    private AccountTypeFactory accountTypeFactory;

    private UserFactory userFactory;

    @Autowired
    public AccountTypeServiceTest(final AccountTypeService service,
                                  final AccountTypeRepository repository,
                                  final AccountTypeFactory accountTypeFactory,
                                  final UserFactory userFactory) {
        this.service = service;
        this.repository = repository;
        this.accountTypeFactory = accountTypeFactory;
        this.userFactory = userFactory;
    }

    @Test
    public void createNewAccountType(){
        var user = userFactory.create();

        service.create("testAccountType", "test@email.com");

        var accountType = repository.findAll().get(0);
        log.info("test=createNewAccount, user={}, accountType={}", user, accountType);

        Assertions.assertEquals("testAccountType", accountType.getName());
        Assertions.assertEquals(user, accountType.getUser());
    }

    @Test
    public void updateAccountType(){
        var accountType = accountTypeFactory.create(empty ->
                empty.setUser(userFactory.create()));

        service.update(accountType.getId(), "updatedAccountTypeName", "test@email.com");

        var accountTypeUpdated = repository.findAll().get(0);
        log.info("test=updateAccountType, accountType={}, accountTypeUpdated={}", accountType, accountTypeUpdated);

        Assertions.assertEquals("testAccountType", accountType.getName());
        Assertions.assertEquals("updatedAccountTypeName", accountTypeUpdated.getName());
    }

    @Test
    public void dontUpdateAccountTypeWrongUser(){
        var accountType = accountTypeFactory.create(empty ->
                empty.setUser(userFactory.create()));
        userFactory.create(empty -> {
            empty.setUsername("differentUser");
            empty.setEmail("differentTest@email.com");
        });

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.update(accountType.getId(), "updatedAccountTypeName", "differentTest@email.com");
        });

        var accountTypeUpdated = repository.findAll().get(0);
        log.info("test=updateAccountType, accountType={}, accountTypeUpdated={}", accountType, accountTypeUpdated);

        Assertions.assertEquals("testAccountType", accountType.getName());
        Assertions.assertEquals("testAccountType", accountTypeUpdated.getName());
    }

    @Test
    public void deleteAccountType(){
        var accountType = accountTypeFactory.create(empty ->
                empty.setUser(userFactory.create()));

        service.delete(accountType.getId(), "test@email.com");

        var accountTypesNone = repository.findAll();
        log.info("test=deleteAccountType, accountType={}, accountTypesNone={}", accountType, accountTypesNone);

        Assertions.assertEquals(0, accountTypesNone.size());
    }

    @Test
    public void dontDeleteAccountTypeWrongUser(){
        var accountType = accountTypeFactory.create(empty ->
                empty.setUser(userFactory.create()));
        userFactory.create(empty -> {
            empty.setUsername("differentUser");
            empty.setEmail("differentTest@email.com");
        });

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.delete(accountType.getId(), "updatedAccountTypeName");
        });

        var accountTypeDeleted = repository.findAll().get(0);
        log.info("test=dontDeleteAccountTypeWrongUser, accountType={}, accountTypeDeleted={}", accountType, accountTypeDeleted);

        Assertions.assertEquals("testAccountType", accountType.getName());
        Assertions.assertEquals("testAccountType", accountTypeDeleted.getName());
    }
}
