package br.edu.ifsp.finances.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountTypeRequest {

    @NotBlank
    private String name;

}
