package br.edu.ifsp.finances.mapper;

import br.edu.ifsp.finances.domain.entity.Transaction;
import br.edu.ifsp.finances.domain.request.TransactionRequest;
import br.edu.ifsp.finances.domain.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TransactionCategoryMapper.class, AccountMapper.class})
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "account.id", source = "account"),
            @Mapping(target = "category.id", source = "category"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    Transaction requestToEntity(TransactionRequest transactionRequest);

    List<Transaction> requestsToEntities(List<TransactionRequest> transactionRequests);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "account", source = "account"),
            @Mapping(target = "category", source = "category")
    })
    TransactionResponse entityToResponse(Transaction transaction);

    List<TransactionResponse> entitiesToResponses(List<Transaction> transactions);
}
