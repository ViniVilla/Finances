package br.edu.ifsp.finances.repository;

import br.edu.ifsp.finances.domain.entity.Account;
import br.edu.ifsp.finances.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(User user);

}
