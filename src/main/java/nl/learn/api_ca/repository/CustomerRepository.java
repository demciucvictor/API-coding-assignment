package nl.learn.api_ca.repository;

import nl.learn.api_ca.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
