package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.domain.entity.TransactionCategory;
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.exception.ResourceNotFoundException;
import br.edu.ifsp.finances.mapper.TransactionCategoryMapper;
import br.edu.ifsp.finances.repository.TransactionCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionCategoryService {

    private TransactionCategoryRepository transactionCategoryRepository;

    private TransactionCategoryMapper transactionCategoryMapper;

    private UserService userService;

    @Autowired
    public TransactionCategoryService(final TransactionCategoryRepository transactionCategoryRepository,
                                      final TransactionCategoryMapper transactionCategoryMapper,
                                      final UserService userService) {
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.transactionCategoryMapper = transactionCategoryMapper;
        this.userService = userService;
    }

    public TransactionCategory getTransactionCategory(final Long id, final String email){
        log.info("method=getTransactionCategory, id={}, email={}", id, email);

        var transactionCategory = transactionCategoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Transaction category not found"));

        if(email.equals(transactionCategory.getUser().getEmail())){
            return transactionCategory;
        } else {
            throw new BadRequestException("The transaction category is not from this user");
        }
    }

    public void create(final String requestName, final String email){
        log.info("method=create, requestName={}, email={}", requestName, email);

        var user = userService.findByEmail(email);
        var category = new TransactionCategory();
        category.setName(requestName);
        category.setUser(user);

        transactionCategoryRepository.save(category);
    }

    public void delete(final Long id, final String email){
        log.info("method=create, id={}, email={}", id, email);

        var category = getTransactionCategory(id, email);

        transactionCategoryRepository.delete(category);
    }

    public void update(final Long id, final String requestName, final String email){
        log.info("method=update, id={}, requestName={}, email={}", id, requestName, email);

        var category = getTransactionCategory(id, email);

        category.setName(requestName);
        transactionCategoryRepository.save(category);
    }

    public List<TransactionCategoryResponse> findAll(String email){
        log.info("method=findAll, email={}", email);

        var user = userService.findByEmail(email);
        return transactionCategoryMapper.entitiesToResponses(transactionCategoryRepository.findByUser(user));
    }
}
