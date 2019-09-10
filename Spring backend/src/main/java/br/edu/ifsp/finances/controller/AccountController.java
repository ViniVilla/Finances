package br.edu.ifsp.finances.controller;

import br.edu.ifsp.finances.domain.request.AccountRequest;
import br.edu.ifsp.finances.domain.response.AccountResponse;
import br.edu.ifsp.finances.security.UserPrincipal;
import br.edu.ifsp.finances.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Saves a new Account", authorizations = @Authorization("JWT"))
    public void create(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @Valid @RequestBody final AccountRequest accountRequest){
        accountService.create(accountRequest, userPrincipal.getEmail());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Deletes an Account", authorizations = @Authorization("JWT"))
    public void delete(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id){
        accountService.delete(id, userPrincipal.getEmail());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Updates an Account", authorizations = @Authorization("JWT"))
    public void update(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id,
                       @Valid @RequestBody AccountRequest accountRequest){
        accountService.update(id, accountRequest, userPrincipal.getEmail());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns all Accounts that the user created", authorizations = @Authorization("JWT"))
    public List<AccountResponse> findAll(@AuthenticationPrincipal final UserPrincipal userPrincipal){
        return accountService.findAll(userPrincipal.getEmail());
    }

}
