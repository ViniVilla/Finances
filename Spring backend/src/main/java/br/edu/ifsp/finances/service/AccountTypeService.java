package br.edu.ifsp.finances.service;

import br.edu.ifsp.finances.domain.entity.AccountType;
import br.edu.ifsp.finances.domain.response.AccountTypeResponse;
import br.edu.ifsp.finances.exception.BadRequestException;
import br.edu.ifsp.finances.exception.ResourceNotFoundException;
import br.edu.ifsp.finances.mapper.AccountTypeMapper;
import br.edu.ifsp.finances.repository.AccountTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountTypeService {

    private AccountTypeRepository accountTypeRepository;

    private AccountTypeMapper accountTypeMapper;

    private UserService userService;

    @Autowired
    public AccountTypeService(final AccountTypeRepository accountTypeRepository,
                              final AccountTypeMapper accountTypeMapper,
                              final UserService userService) {
        this.accountTypeRepository = accountTypeRepository;
        this.accountTypeMapper = accountTypeMapper;
        this.userService = userService;
    }

    public AccountType getAccountType(final Long id, final String email){
        log.info("method=getAccountType, id={}", id);

        var accountType = accountTypeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Account type not found"));

        if(email.equals(accountType.getUser().getEmail())){
            return accountType;
        } else {
            throw new BadRequestException("The account type is not from this user");
        }
    }

    public void create(final String requestName, final String email){
        log.info("method=create, requestName={}, email={}", requestName, email);

        var user = userService.findByEmail(email);
        var accountType = new AccountType();
        accountType.setName(requestName);
        accountType.setUser(user);

        accountTypeRepository.save(accountType);
    }

    public void delete(final Long id, final String email){
        log.info("method=delete, id={}, email={}", id, email);

        var accountType = getAccountType(id, email);

        accountTypeRepository.delete(accountType);
    }

    public void update(final Long id, final String newName, final String email){
        log.info("method=update, id={}, newName={}, email={}", id, newName, email);

        var accountType = getAccountType(id, email);
        accountType.setName(newName);

        accountTypeRepository.save(accountType);
    }

    public List<AccountTypeResponse> findAll(final String email){
        log.info("method=findAll, email={}", email);

        var user = userService.findByEmail(email);
        return accountTypeMapper.entitiesToResponses(accountTypeRepository.findByUser(user));
    }

}
