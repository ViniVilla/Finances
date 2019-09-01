package br.edu.ifsp.finances.domain.response;

import lombok.Data;

@Data
public class AccountResponse {

    private Long id;

    private String name;

    private AccountTypeResponse type;

}
