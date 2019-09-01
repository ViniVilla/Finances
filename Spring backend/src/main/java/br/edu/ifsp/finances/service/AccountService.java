package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.domain.entity.Account;
import br.edu.ifsp.finances.domain.request.AccountRequest;
import br.edu.ifsp.finances.domain.response.AccountResponse;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.exception.ResourceNotFoundException;
import br.edu.ifsp.finances.mapper.AccountMapper;
import br.edu.ifsp.finances.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountService {

    private AccountTypeService accountTypeService;

    private UserService userService;

    private AccountRepository accountRepository;

    private AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountTypeService accountTypeService,
                          UserService userService,
                          AccountRepository accountRepository,
                          AccountMapper accountMapper) {
        this.accountTypeService = accountTypeService;
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public Account getAccount(final Long id, final String email){

        var account = accountRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Account not found"));

        if(email.equals(account.getUser().getEmail())){
            return account;
        } else {
            throw new BadRequestException("The account is not from this user");
        }
    }

    public void create(final AccountRequest accountRequest,
                       final String email){
        log.info("method=create, accountRequest={}, email={}", accountRequest, email);

        var account = accountMapper.requestToEntity(accountRequest);
        var user = userService.findByEmail(email);
        account.setUser(user);

        account.setType(accountTypeService
                .getAccountType(accountRequest.getTypeId(), email));
        accountRepository.save(account);
    }

    public void delete(final Long id,
                       final String email) {
        log.info("method=delete, id={}, email={}", id, email);

        var account = getAccount(id, email);
        accountRepository.delete(account);
    }

    public void update(final Long id,
                       final AccountRequest accountRequest,
                       final String email){
        log.info("method=update, id={}, accountRequest={}, email={}", id, accountRequest, email);

        var account = getAccount(id, email);

        account.setName(accountRequest.getName());
        account.setType(accountTypeService
                .getAccountType(accountRequest.getTypeId(), email));

        accountRepository.save(account);
    }

    public List<AccountResponse> findAll(final String email){
        log.info("method=findAll, email={}", email);

        var user = userService.findByEmail(email);
        return accountMapper.entitiesToResponses(accountRepository.findByUser(user));
    }
}
