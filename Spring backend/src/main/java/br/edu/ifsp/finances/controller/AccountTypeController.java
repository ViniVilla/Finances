package br.edu.ifsp.finances.controller;

import br.edu.ifsp.finances.domain.request.AccountTypeRequest;
import br.edu.ifsp.finances.domain.response.AccountTypeResponse;
import br.edu.ifsp.finances.security.UserPrincipal;
import br.edu.ifsp.finances.service.AccountTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-types")
public class AccountTypeController {

    private AccountTypeService accountTypeService;

    @Autowired
    public AccountTypeController(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Saves a new AccountType", authorizations = @Authorization("JWT"))
    public void create(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @RequestBody final AccountTypeRequest accountTypeRequest){
        accountTypeService.create(accountTypeRequest.getName(), userPrincipal.getEmail());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Deletes an AccountType", authorizations = @Authorization("JWT"))
    public void delete(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id){
        accountTypeService.delete(id, userPrincipal.getEmail());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Updates an AccountType", authorizations = @Authorization("JWT"))
    public void update(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id,
                       @RequestBody final AccountTypeRequest accountTypeRequest){
        accountTypeService.update(id, accountTypeRequest.getName(), userPrincipal.getEmail());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns all AccountTypes that the user created", authorizations = @Authorization("JWT"))
    public List<AccountTypeResponse> findAll(@AuthenticationPrincipal final UserPrincipal userPrincipal){
        return accountTypeService.findAll(userPrincipal.getEmail());
    }
}
