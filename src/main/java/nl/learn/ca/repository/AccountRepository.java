package nl.learn.ca.repository;

import nl.learn.ca.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
