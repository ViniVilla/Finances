package br.edu.ifsp.finances.mapper;

import br.edu.ifsp.finances.domain.entity.AccountType;
import br.edu.ifsp.finances.domain.response.AccountTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name")
    })
    AccountTypeResponse entityToResponse(AccountType accountType);

    List<AccountTypeResponse> entitiesToResponses(List<AccountType> accountTypes);

}
