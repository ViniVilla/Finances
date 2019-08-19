package br.edu.ifsp.finances.factory.entity;

import br.com.leonardoferreira.jbacon.JBacon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionCategoryFactory extends JBacon<TransactionCategory> {

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Override
    protected TransactionCategory getDefault() {
        var transactionCategory = new TransactionCategory();
        transactionCategory.setName("testCategory");
        return transactionCategory;
    }

    @Override
    protected TransactionCategory getEmpty() {
        return new TransactionCategory();
    }

    @Override
    protected void persist(TransactionCategory transactionCategory) {
        transactionCategoryRepository.save(transactionCategory);
    }
}
