package com.ws.spring.account.repository;

import com.ws.spring.account.domain.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Profile("mongo")
@Repository
public interface AccountMongoRepository extends MongoRepository<Account, String>, AccountRepository {

}
