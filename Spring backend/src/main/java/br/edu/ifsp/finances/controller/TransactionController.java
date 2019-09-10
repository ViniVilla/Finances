package br.edu.ifsp.finances.controller;

import br.edu.ifsp.finances.domain.request.TransactionRequest;
import br.edu.ifsp.finances.domain.response.TransactionResponse;
import br.edu.ifsp.finances.security.UserPrincipal;
import br.edu.ifsp.finances.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Saves a new Transaction", authorizations = @Authorization("JWT"))
    public void create(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @Valid @RequestBody final TransactionRequest transactionRequest){
        transactionService.create(transactionRequest, userPrincipal.getEmail());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Deletes a Transaction", authorizations = @Authorization("JWT"))
    public void delete(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id){
        transactionService.delete(id, userPrincipal.getEmail());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Updates a Transaction", authorizations = @Authorization("JWT"))
    public void update(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id,
                       @Valid @RequestBody TransactionRequest transactionRequest){
        transactionService.update(id, transactionRequest, userPrincipal.getEmail());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns all Transactions that the user created", authorizations = @Authorization("JWT"))
    public List<TransactionResponse> findAll(@AuthenticationPrincipal final UserPrincipal userPrincipal){
        return transactionService.findAll(userPrincipal.getEmail());
    }

}
