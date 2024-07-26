package nl.learn.ca.service;

import nl.learn.ca.dto.CustomerDTO;
import nl.learn.ca.dto.TransactionDto;
import nl.learn.ca.entity.Account;
import nl.learn.ca.entity.Customer;
import nl.learn.ca.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public Optional<CustomerDTO> getCustomerInfo(Long customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = customerOpt.get();

        return Optional.of(createCustomerInfoObject(customer));
    }

    private CustomerDTO createCustomerInfoObject(Customer customer) {

        List<TransactionDto> transactions = customer.getAccounts().stream()
                .map(Account::getTransactions)
                .flatMap(List::stream)
                .map(transaction -> TransactionDto.builder()
                        .amount(transaction.getAmount())
                        .isSuccess(transaction.getIsSuccess())
                        .description(transaction.getDescription())
                        .accountId(transaction.getAccount().getAccountId())
                        .build())
                .toList();

        return CustomerDTO.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .balance(customer.getBalance())
                .transactions(transactions)
                .build();
    }

}
