package br.edu.ifsp.finances.mapper;

import br.edu.ifsp.finances.domain.entity.TransactionCategory;
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionCategoryMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name")
    })
    TransactionCategoryResponse entityToResponse(TransactionCategory transactionCategory);

    List<TransactionCategoryResponse> entitiesToResponses(List<TransactionCategory> transactionCategories);

}
