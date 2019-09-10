package br.edu.ifsp.finances.repository;

import br.edu.ifsp.finances.domain.entity.Transaction;
import br.edu.ifsp.finances.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);

}
