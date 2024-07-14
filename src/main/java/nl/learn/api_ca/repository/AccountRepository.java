package nl.learn.api_ca.repository;

import nl.learn.api_ca.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
