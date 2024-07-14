package nl.learn.ca.service;

import nl.learn.ca.entity.Account;
import nl.learn.ca.entity.Customer;
import nl.learn.ca.entity.Transaction;
import nl.learn.ca.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Account account, Double amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        executeTransaction(transaction);
        transactionRepository.save(transaction);
        return transaction;
    }

    public void executeTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        Customer customer = account.getCustomer();
        if(customer.getBalance() >= transaction.getAmount()) {
            customer.setBalance(customer.getBalance() - transaction.getAmount());
            account.setInitialCredit(transaction.getAmount());
            transaction.setIsSuccess(true);
        } else {
            transaction.setIsSuccess(false);
            transaction.setDescription("Transaction failed due to amount exceeding customer's balance");
        }
    }
}
