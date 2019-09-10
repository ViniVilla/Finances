package br.edu.ifsp.finances.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotBlank
    private String name;

    @Positive
    private BigDecimal amount;

    @NotNull
    @Positive
    private Long account;

    @NotNull
    @Positive
    private Long category;

}
