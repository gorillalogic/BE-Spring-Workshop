package com.ws.spring.transaction.repository;

import com.ws.spring.transaction.domain.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Profile("mongo")
@Repository
public interface TransactionMongoRepository extends MongoRepository<Transaction, Integer>, TransactionRepository {

}
