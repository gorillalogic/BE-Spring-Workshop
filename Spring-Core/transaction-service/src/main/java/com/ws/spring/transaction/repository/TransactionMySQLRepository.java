package com.ws.spring.transaction.repository;

import com.ws.spring.transaction.domain.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Profile("sql")
@Repository
public interface TransactionMySQLRepository extends CrudRepository<Transaction, Integer>, TransactionRepository {

}
