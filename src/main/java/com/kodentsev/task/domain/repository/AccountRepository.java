package com.kodentsev.task.domain.repository;

import com.kodentsev.task.domain.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String>, CustomAccountRepository {

    List<Account> findByUserId(String userId);
}
