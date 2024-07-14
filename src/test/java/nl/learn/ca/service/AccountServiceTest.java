package nl.learn.ca.service;

import nl.learn.ca.entity.Account;
import nl.learn.ca.entity.Customer;
import nl.learn.ca.entity.Transaction;
import nl.learn.ca.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewAccount_ShouldReturnTrue_WhenCustomerExists() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(transactionService.createTransaction(any(Account.class), anyDouble())).thenReturn(new Transaction());

        Boolean result = accountService.createNewAccount(1L, 1000.0);

        assertTrue(result);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(transactionService, times(1)).createTransaction(any(Account.class), anyDouble());
    }

    @Test
    void createNewAccount_ShouldReturnFalse_WhenCustomerDoesNotExist() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Boolean result = accountService.createNewAccount(1L, 1000.0);

        assertFalse(result);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, never()).save(any(Customer.class));
        verify(transactionService, never()).createTransaction(any(Account.class), anyDouble());
    }
}
