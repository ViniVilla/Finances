package br.edu.ifsp.finances.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class AccountRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Long typeId;

}
