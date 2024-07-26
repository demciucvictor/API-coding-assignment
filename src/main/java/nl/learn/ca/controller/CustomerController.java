package nl.learn.ca.controller;

import nl.learn.ca.dto.CustomerDTO;
import nl.learn.ca.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customerinfo")
    public ResponseEntity<CustomerDTO> getCustomerInfo(@RequestParam Long customerId) {
        Optional<CustomerDTO> customerInfoDTO = customerService.getCustomerInfo(customerId);
        return customerInfoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
