package br.edu.ifsp.finances.factory.entity;

import br.com.leonardoferreira.jbacon.JBacon;
import br.edu.ifsp.finances.domain.entity.AccountType;
import br.edu.ifsp.finances.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeFactory extends JBacon<AccountType> {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    protected AccountType getDefault() {
        var accountType = new AccountType();
        accountType.setName("testAccountType");
        return accountType;
    }

    @Override
    protected AccountType getEmpty() {
        return new AccountType();
    }

    @Override
    protected void persist(AccountType accountType) {
        accountTypeRepository.save(accountType);
    }
}
