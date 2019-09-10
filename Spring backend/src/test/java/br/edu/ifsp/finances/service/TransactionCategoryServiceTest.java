package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.BaseTest;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.factory.entity.TransactionCategoryFactory;
import br.edu.ifsp.finances.factory.entity.UserFactory;
import br.edu.ifsp.finances.repository.TransactionCategoryRepository;
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
public class TransactionCategoryServiceTest extends BaseTest {

    private TransactionCategoryService service;

    private TransactionCategoryRepository repository;

    private TransactionCategoryFactory categoryFactory;

    private UserFactory userFactory;

    @Autowired
    public TransactionCategoryServiceTest(final TransactionCategoryService service,
                                          final TransactionCategoryRepository repository,
                                          final TransactionCategoryFactory categoryFactory,
                                          final UserFactory userFactory) {
        this.service = service;
        this.repository = repository;
        this.categoryFactory = categoryFactory;
        this.userFactory = userFactory;
    }

    @Test
    public void createNewTransactionCategory(){
        var user = userFactory.create();

        service.create("testCategory", "test@email.com");

        var category = repository.findAll().get(0);
        log.info("test=createNewTransactionCategory, user={}, category={}", user, category);

        Assertions.assertEquals("testCategory", category.getName());
        Assertions.assertEquals(user, category.getUser());
    }

    @Test
    public void updateTransactionCategory(){
        var category = categoryFactory.create(empty ->
                empty.setUser(userFactory.create()));

        service.update(category.getId(), "updatedCategory", "test@email.com");

        var categoryUpdated = repository.findAll().get(0);
        log.info("test=updateTransactionCategory, category={}, categoryUpdated={}", category, categoryUpdated);

        Assertions.assertEquals("testCategory", category.getName());
        Assertions.assertEquals("updatedCategory", categoryUpdated.getName());
    }

    @Test
    public void dontUpdateTransactionCategoryWrongUser(){
        var category = categoryFactory.create(empty ->
                empty.setUser(userFactory.create()));
        userFactory.create(empty -> {
            empty.setUsername("differentUser");
            empty.setEmail("differentTest@email.com");
        });

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.update(category.getId(), "updatedCategory", "differentTest@email.com");
        });

        var categoryUpdated = repository.findAll().get(0);

        Assertions.assertEquals("testCategory", category.getName());
        Assertions.assertEquals("testCategory", categoryUpdated.getName());
    }

    @Test
    public void deleteTransactionCategory(){
        var category = categoryFactory.create(empty ->
                empty.setUser(userFactory.create()));

        service.delete(category.getId(), "test@email.com");
        var categoriesDeleted = repository.findAll();

        log.info("test=deleteTransactionCategory, category={}, categoriesDeleted={}", category, categoriesDeleted);

        Assertions.assertEquals(0, categoriesDeleted.size());
    }

    @Test
    public void dontDeleteTransactionCategoryWrongUser(){
        var category = categoryFactory.create(empty ->
                empty.setUser(userFactory.create()));
        userFactory.create(empty -> {
            empty.setUsername("differentUser");
            empty.setEmail("differentTest@email.com");
        });

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.delete(category.getId(), "differentTest@email.com");
        });
        var categoriesDeleted = repository.findAll();

        log.info("test=dontDeleteTransactionCategoryWrongUser, category={}, categoriesDeleted={}", category, categoriesDeleted);

        Assertions.assertEquals(1, categoriesDeleted.size());
    }
}
