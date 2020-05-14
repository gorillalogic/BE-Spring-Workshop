package com.ws.spring.account.repository;

import com.ws.spring.account.domain.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Profile("sql")
@Repository
public interface AccountMySQLRepository extends CrudRepository<Account, Integer>, AccountRepository {

}
