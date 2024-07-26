package nl.learn.ca.controller;

import nl.learn.ca.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void createNewAccount_ShouldReturnOk_WhenAccountCreated() throws Exception {
        Mockito.when(accountService.createNewAccount(anyLong(), anyDouble())).thenReturn(true);

        mockMvc.perform(post("/api/accounts/new-account/1")
                        .param("initialCredit", "1000"))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewAccount_ShouldReturnBadRequest_WhenAccountNotCreated() throws Exception {
        Mockito.when(accountService.createNewAccount(anyLong(), anyDouble())).thenReturn(false);

        mockMvc.perform(post("/api/accounts/new-account/1")
                        .param("initialCredit", "1000"))
                .andExpect(status().isBadRequest());
    }
}
