package nl.learn.api_ca.repository;

import nl.learn.api_ca.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
