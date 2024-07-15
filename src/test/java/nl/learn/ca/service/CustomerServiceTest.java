package nl.learn.ca.service;

import nl.learn.ca.dto.CustomerDTO;
import nl.learn.ca.dto.TransactionDto;
import nl.learn.ca.entity.Account;
import nl.learn.ca.entity.Customer;
import nl.learn.ca.entity.Transaction;
import nl.learn.ca.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCustomerInfo_ShouldReturnCustomerInfo_WhenCustomerExists() {
        // Arrange
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Snow");
        customer.setBalance(50.0);
        customer.setAccounts(Collections.singletonList(createAccount()));

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // Act
        Optional<CustomerDTO> result = customerService.getCustomerInfo(1L);

        // Assert
        assertTrue(result.isPresent());
        CustomerDTO customerDTO = result.get();
        assertTrue(customerDTO.getName().equals("John"));
        assertTrue(customerDTO.getSurname().equals("Snow"));
        assertTrue(customerDTO.getBalance().equals(50.0));
        assertTrue(customerDTO.getTransactions().size() == 1);

        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    void getCustomerInfo_ShouldReturnEmpty_WhenCustomerDoesNotExist() {
        // Arrange
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<CustomerDTO> result = customerService.getCustomerInfo(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(anyLong());
    }

    private Account createAccount() {
        Account account = new Account();
        account.setAccountId(1L);
        account.setInitialCredit(1000.0);

        Transaction transaction = new Transaction();
        transaction.setAmount(1000.0);
        transaction.setIsSuccess(true);
        transaction.setDescription("Initial Credit");
        transaction.setAccount(account);

        account.setTransactions(Collections.singletonList(transaction));
        return account;
    }
}
