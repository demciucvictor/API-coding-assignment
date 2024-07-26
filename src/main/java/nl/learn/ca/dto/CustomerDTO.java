package nl.learn.ca.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CustomerDTO {

    private String name;

    private String surname;

    private Double balance;

    private List<TransactionDto> transactions;

}
