package com.kodentsev.task.domain.repository;

import com.kodentsev.task.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository{

    private final String ID_FIELD = "id";
    private final String LOCKED = "locked";

    private final MongoTemplate mongoTemplate;

    @Override
    public Account findAndLock(String accountId) {
        Criteria criteria = Criteria.where(ID_FIELD).is(accountId).and(LOCKED).ne(true);
        Update update = new Update().set(LOCKED, true);

        return mongoTemplate.findAndModify(
                Query.query(criteria),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Account.class);
    }
}
