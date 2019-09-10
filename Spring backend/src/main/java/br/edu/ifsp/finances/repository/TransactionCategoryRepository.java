package br.edu.ifsp.finances.repository;

import br.edu.ifsp.finances.domain.entity.TransactionCategory;
import br.edu.ifsp.finances.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {

    List<TransactionCategory> findByUser(User user);

}
