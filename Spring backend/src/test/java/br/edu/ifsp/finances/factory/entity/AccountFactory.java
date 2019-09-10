package br.edu.ifsp.finances.factory.entity;

import br.com.leonardoferreira.jbacon.JBacon;
import br.edu.ifsp.finances.domain.entity.Account;
import br.edu.ifsp.finances.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory extends JBacon<Account> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected Account getDefault() {
        var account = new Account();
        account.setName("testAccount");
        return account;
    }

    @Override
    protected Account getEmpty() {
        return new Account();
    }

    @Override
    protected void persist(Account account) {
        accountRepository.save(account);
    }
}
