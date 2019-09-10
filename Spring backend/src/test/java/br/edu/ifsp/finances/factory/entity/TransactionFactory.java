package br.edu.ifsp.finances.factory.entity;

import br.com.leonardoferreira.jbacon.JBacon;
import br.edu.ifsp.finances.domain.entity.Transaction;
import br.edu.ifsp.finances.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionFactory extends JBacon<Transaction> {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    protected Transaction getDefault() {
        var transaction = new Transaction();
        transaction.setName("transactionTest");
        transaction.setAmount(BigDecimal.TEN);
        return transaction;
    }

    @Override
    protected Transaction getEmpty() {
        return new Transaction();
    }

    @Override
    protected void persist(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
