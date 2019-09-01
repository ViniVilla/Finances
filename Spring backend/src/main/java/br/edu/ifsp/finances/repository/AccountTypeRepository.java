package br.edu.ifsp.finances.repository;

import br.edu.ifsp.finances.domain.entity.AccountType;
import br.edu.ifsp.finances.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    List<AccountType> findByUser(User user);

}
