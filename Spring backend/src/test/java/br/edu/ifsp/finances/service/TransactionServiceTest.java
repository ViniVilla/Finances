package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.BaseTest;
import br.edu.ifsp.finances.domain.request.TransactionRequest;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.factory.entity.AccountFactory;
import br.edu.ifsp.finances.factory.entity.AccountTypeFactory;
import br.edu.ifsp.finances.factory.entity.TransactionCategoryFactory;
import br.edu.ifsp.finances.factory.entity.TransactionFactory;
import br.edu.ifsp.finances.factory.entity.UserFactory;
import br.edu.ifsp.finances.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TransactionServiceTest extends BaseTest {

    private AccountFactory accountFactory;

    private AccountTypeFactory typeFactory;

    private UserFactory userFactory;

    private TransactionCategoryFactory categoryFactory;

    private TransactionFactory transactionFactory;

    private TransactionService service;

    private TransactionRepository repository;

    @Autowired
    public TransactionServiceTest(final AccountFactory accountFactory,
                                  final AccountTypeFactory typeFactory,
                                  final UserFactory userFactory,
                                  final TransactionCategoryFactory categoryFactory,
                                  final TransactionFactory transactionFactory,
                                  final TransactionService service,
                                  final TransactionRepository repository) {
        this.accountFactory = accountFactory;
        this.typeFactory = typeFactory;
        this.userFactory = userFactory;
        this.categoryFactory = categoryFactory;
        this.transactionFactory = transactionFactory;
        this.service = service;
        this.repository = repository;
    }

    @Test
    public void createNewTransaction(){
        var user = userFactory.create();
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });
        var category = categoryFactory.create(empty -> empty.setUser(user));

        var request = new TransactionRequest();
        request.setAccount(account.getId());
        request.setCategory(category.getId());
        request.setName("testTransaction");
        request.setAmount(BigDecimal.TEN);

        service.create(request, "test@email.com");
        var transaction = repository.findAll().get(0);

        log.info("test=createNewTransaction, transaction={}",transaction);

        Assertions.assertEquals("testTransaction", transaction.getName());
        Assertions.assertEquals("testAccount", transaction.getAccount().getName());
        Assertions.assertEquals("testCategory", transaction.getCategory().getName());
    }

    @Test
    public void deleteTransaction(){
        var user = userFactory.create();
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });
        var category = categoryFactory.create(empty -> empty.setUser(user));

        var transaction = transactionFactory.create(empty -> {
                empty.setCategory(category);
                empty.setAccount(account);
                empty.setUser(user);
        });
        repository.save(transaction);

        service.delete(transaction.getId(), "test@email.com");
        var transactionsDeleted = repository.findAll();

        Assertions.assertEquals(0, transactionsDeleted.size());

    }

    @Test
    public void dontDeleteTransactionWrongUser(){
        var user = userFactory.create();
        var userDifferent = userFactory.create(empty -> {
            empty.setEmail("different@email.com");
            empty.setUsername("different");
        });
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });
        var category = categoryFactory.create(empty -> empty.setUser(user));

        var transaction = transactionFactory.create(empty -> {
            empty.setCategory(category);
            empty.setAccount(account);
            empty.setUser(user);
        });
        repository.save(transaction);

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.delete(transaction.getId(), "different@email.com");
        });
        var transactionsDeleted = repository.findAll();

        Assertions.assertEquals(1, transactionsDeleted.size());

    }

    @Test
    public void updateTransaction(){
        var user = userFactory.create();
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });
        var category = categoryFactory.create(empty -> empty.setUser(user));
        var differentCategory = categoryFactory.create(empty -> {
            empty.setUser(user);
            empty.setName("differentCategory");
        });

        var transaction = transactionFactory.create(empty -> {
            empty.setCategory(category);
            empty.setAccount(account);
            empty.setUser(user);
        });
        repository.save(transaction);

        var request = new TransactionRequest();
        request.setAccount(account.getId());
        request.setCategory(differentCategory.getId());
        request.setName("updatedTransaction");
        request.setAmount(BigDecimal.ONE);

        service.update(transaction.getId(), request, "test@email.com");
        var updatedTransaction = repository.findAll().get(0);

        Assertions.assertEquals("updatedTransaction", updatedTransaction.getName());
        Assertions.assertEquals("differentCategory", updatedTransaction.getCategory().getName());
        Assertions.assertEquals(0, updatedTransaction.getAmount().compareTo(BigDecimal.ONE));
    }

    @Test
    public void dontUpdateTransactionWrongUser(){
        var user = userFactory.create();
        var userDifferent = userFactory.create(empty -> {
            empty.setEmail("different@email.com");
            empty.setUsername("different");
        });
        var account = accountFactory.create(empty -> {
            empty.setUser(user);
            empty.setType(typeFactory.create(emptyType ->
                    emptyType.setUser(user)));
        });
        var category = categoryFactory.create(empty -> empty.setUser(user));
        var differentCategory = categoryFactory.create(empty -> {
            empty.setUser(user);
            empty.setName("differentCategory");
        });

        var transaction = transactionFactory.create(empty -> {
            empty.setCategory(category);
            empty.setAccount(account);
            empty.setUser(user);
        });
        repository.save(transaction);

        var request = new TransactionRequest();
        request.setAccount(account.getId());
        request.setCategory(differentCategory.getId());
        request.setName("updatedTransaction");
        request.setAmount(BigDecimal.ONE);

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.update(transaction.getId(), request, "different@email.com");
        });
        var updatedTransaction = repository.findAll().get(0);

        Assertions.assertEquals("transactionTest", updatedTransaction.getName());
        Assertions.assertEquals("testCategory", updatedTransaction.getCategory().getName());
        Assertions.assertEquals(1 , updatedTransaction.getAmount().compareTo(BigDecimal.ONE));
    }

}
