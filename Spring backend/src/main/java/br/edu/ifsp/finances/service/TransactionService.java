package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.domain.entity.Transaction;
import br.edu.ifsp.finances.domain.request.TransactionRequest;
import br.edu.ifsp.finances.domain.response.TransactionResponse;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.exception.ResourceNotFoundException;
import br.edu.ifsp.finances.mapper.TransactionMapper;
import br.edu.ifsp.finances.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionService {

    private UserService userService;

    private AccountService accountService;

    private TransactionCategoryService transactionCategoryService;

    private TransactionRepository transactionRepository;

    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(final UserService userService,
                              final AccountService accountService,
                              final TransactionCategoryService transactionCategoryService,
                              final TransactionRepository transactionRepository,
                              final TransactionMapper transactionMapper) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionCategoryService = transactionCategoryService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public Transaction getTransaction(final Long id,
                                      final String email){
        log.info("method=getTransaction, id={}, email={}", id, email);

        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if(email.equals(transaction.getUser().getEmail())){
            return transaction;
        } else {
            throw new BadRequestException("The transaction is not from this user");
        }
    }

    public void create(final TransactionRequest transactionRequest,
                       final String email){
        log.info("method=create, transactionRequest={}, email={}", transactionRequest, email);

        var transaction = transactionMapper.requestToEntity(transactionRequest);
        var user = userService.findByEmail(email);
        transaction.setUser(user);

        transaction.setAccount(accountService.
                getAccount(transactionRequest.getAccount(), email));
        transaction.setCategory(transactionCategoryService.
                getTransactionCategory(transactionRequest.getCategory(), email));
        transactionRepository.save(transaction);
    }

    public void delete(final Long id,
                       final String email){
        log.info("method=delete, id={}, email={}", id, email);

        var transaction = getTransaction(id, email);
        transactionRepository.delete(transaction);
    }

    public void update(final Long id,
                       final TransactionRequest transactionRequest,
                       final String email){
        log.info("method=update, id={}, transactionRequest={}, email={}", id, transactionRequest, email);

        var transaction = getTransaction(id, email);

        transaction.setName(transactionRequest.getName());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setAccount(accountService.
                getAccount(transactionRequest.getAccount(), email));
        transaction.setCategory(transactionCategoryService.
                getTransactionCategory(transactionRequest.getCategory(), email));
        transactionRepository.save(transaction);
    }

    public List<TransactionResponse> findAll(String email){
        log.info("method=findAll, email={}", email);

        var user = userService.findByEmail(email);
        return transactionMapper.entitiesToResponses(transactionRepository.findByUser(user));
    }

}
