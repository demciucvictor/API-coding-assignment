package nl.learn.ca.service;

import nl.learn.ca.entity.Account;
import nl.learn.ca.entity.Customer;
import nl.learn.ca.entity.Transaction;
import nl.learn.ca.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final CustomerRepository customerRepository;

    private final TransactionService transactionService;


    @Autowired
    public AccountService(CustomerRepository customerRepository, TransactionService transactionService) {
        this.customerRepository = customerRepository;
        this.transactionService = transactionService;
    }

    public Boolean createNewAccount(Long customerId, Double initialCredit) {
        Account accountToCreate = new Account();
        Customer customer;
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
           customer = customerOpt.get();
        } else {
            log.error("Invalid customer id: {}", customerId);
            return false;
        }
        accountToCreate.setCustomer(customer);

        setInitialAccountCredit(accountToCreate, initialCredit);

        customer.addAccount(accountToCreate);
        customerRepository.save(customer);
        return true;
    }

    private void setInitialAccountCredit(Account account, Double initialCredit) {
        if (initialCredit == 0) {
            account.setInitialCredit(0.0);
        } else if (initialCredit > 0) {
            Transaction transaction = transactionService.createTransaction(account, initialCredit);
            account.addTransaction(transaction);
        } else if (initialCredit < 0) {
            log.warn("Initial Credit of a new account should not be below 0: {}", initialCredit);
        }
    }
}
