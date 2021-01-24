package com.kodentsev.task.domain.repository;

import com.kodentsev.task.domain.entity.Account;

public interface CustomAccountRepository {

    Account findAndLock(String accountId);
}
