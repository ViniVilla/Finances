package br.edu.ifsp.finances.domain.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponse {

    private Long id;

    private String name;

    private BigDecimal amount;

    private AccountResponse account;

    private TransactionCategoryResponse category;

}
