package nl.learn.api_ca.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransactionDto {

    private Double amount;

    private Boolean isSuccess;

    private String description;

    private Long accountId;
}
