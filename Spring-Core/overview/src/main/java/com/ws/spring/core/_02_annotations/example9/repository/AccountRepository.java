package com.ws.spring.core._02_annotations.example9.repository;

import com.ws.spring.core._02_annotations.example9.domain.Account;

public interface AccountRepository {

    Account getById(String id);

}
