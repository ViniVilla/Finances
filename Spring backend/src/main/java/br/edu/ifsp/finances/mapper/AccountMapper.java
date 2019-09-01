package br.edu.ifsp.finances.mapper;

import br.edu.ifsp.finances.domain.entity.Account;
import br.edu.ifsp.finances.domain.request.AccountRequest;
import br.edu.ifsp.finances.domain.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = AccountTypeMapper.class)
public interface AccountMapper {

    @Mappings({
            @Mapping(target = "type.id", source = "typeId"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    Account requestToEntity(AccountRequest accountRequest);


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "type", source = "type")
    })
    AccountResponse entityToResposne(Account account);

    List<AccountResponse> entitiesToResponses(List<Account> accounts);

}
