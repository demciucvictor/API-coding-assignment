package nl.learn.ca.service;

import nl.learn.ca.entity.Account;
import nl.learn.ca.entity.Customer;
import nl.learn.ca.entity.Transaction;
import nl.learn.ca.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction_ShouldCreateAndSaveTransaction_WhenBalanceIsSufficient() {
        // Arrange
        Account account = new Account();
        Customer customer = new Customer();
        customer.setBalance(2000.0);
        account.setCustomer(customer);

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Transaction transaction = transactionService.createTransaction(account, 1000.0);

        // Assert
        assertNotNull(transaction);
        assertTrue(transaction.getIsSuccess());
        assertEquals(1000.0, transaction.getAmount());
        assertEquals(1000.0, account.getInitialCredit());
        assertEquals(1000.0, customer.getBalance());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void createTransaction_ShouldFailTransaction_WhenBalanceIsInsufficient() {
        // Arrange
        Account account = new Account();
        Customer customer = new Customer();
        customer.setBalance(500.0);
        account.setCustomer(customer);

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Transaction transaction = transactionService.createTransaction(account, 1000.0);

        // Assert
        assertNotNull(transaction);
        assertFalse(transaction.getIsSuccess());
        assertEquals(1000.0, transaction.getAmount());
        assertNull(account.getInitialCredit());
        assertEquals(500.0, customer.getBalance());
        assertEquals("Transaction failed due to amount exceeding customer's balance", transaction.getDescription());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void executeTransaction_ShouldUpdateAccountAndCustomer_WhenBalanceIsSufficient() {
        // Arrange
        Account account = new Account();
        Customer customer = new Customer();
        customer.setBalance(2000.0);
        account.setCustomer(customer);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(1000.0);

        // Act
        transactionService.executeTransaction(transaction);

        // Assert
        assertTrue(transaction.getIsSuccess());
        assertEquals(1000.0, transaction.getAmount());
        assertEquals(1000.0, account.getInitialCredit());
        assertEquals(1000.0, customer.getBalance());
    }

    @Test
    void executeTransaction_ShouldFailTransaction_WhenBalanceIsInsufficient() {
        // Arrange
        Account account = new Account();
        Customer customer = new Customer();
        customer.setBalance(500.0);
        account.setCustomer(customer);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(1000.0);

        // Act
        transactionService.executeTransaction(transaction);

        // Assert
        assertFalse(transaction.getIsSuccess());
        assertEquals(1000.0, transaction.getAmount());
        assertNull(account.getInitialCredit());
        assertEquals(500.0, customer.getBalance());
        assertEquals("Transaction failed due to amount exceeding customer's balance", transaction.getDescription());
    }
}
