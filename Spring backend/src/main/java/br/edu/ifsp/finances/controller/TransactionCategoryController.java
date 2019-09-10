package br.edu.ifsp.finances.controller;

import br.edu.ifsp.finances.domain.request.TransactionCategoryRequest;
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse;
import br.edu.ifsp.finances.security.UserPrincipal;
import br.edu.ifsp.finances.service.TransactionCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-categories")
public class TransactionCategoryController {

    private TransactionCategoryService transactionCategoryService;

    @Autowired
    public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Saves a new transactionCategory", authorizations = @Authorization("JWT"))
    public void create(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @RequestBody final TransactionCategoryRequest transactionCategoryRequest){
        transactionCategoryService.create(transactionCategoryRequest.getName(), userPrincipal.getEmail());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Deletes a transactionCategory", authorizations = @Authorization("JWT"))
    public void delete(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id){
        transactionCategoryService.delete(id, userPrincipal.getEmail());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Updates a transactionCategory", authorizations = @Authorization("JWT"))
    public void update(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                       @PathVariable final Long id,
                       @RequestBody final TransactionCategoryRequest transactionCategoryRequest){
        transactionCategoryService.update(id, transactionCategoryRequest.getName(), userPrincipal.getEmail());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns all TransactionCategories that the user created", authorizations = @Authorization("JWT"))
    public List<TransactionCategoryResponse> findAll(@AuthenticationPrincipal final UserPrincipal userPrincipal){
        return transactionCategoryService.findAll(userPrincipal.getEmail());
    }

}
