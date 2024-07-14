package nl.learn.ca.controller;

import nl.learn.ca.dto.CustomerDTO;
import nl.learn.ca.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void getCustomerInfo_ShouldReturnCustomerInfo_WhenCustomerExists() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().name("John").surname("Snow").balance(10.0).build();

        Mockito.when(customerService.getCustomerInfo(anyLong())).thenReturn(Optional.of(customerDTO));

        mockMvc.perform(get("/customerinfo").param("customerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Snow"))
                .andExpect(jsonPath("$.balance").value(10.0));
    }

    @Test
    public void getCustomerInfo_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        Mockito.when(customerService.getCustomerInfo(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/customerinfo").param("customerId", "1"))
                .andExpect(status().isNotFound());
    }
}
