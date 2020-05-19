package com.ws.spring.account.client;

import com.ws.spring.account.dto.TransactionDto;
import com.ws.spring.account.dto.request.TransactionCreateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${transaction-service.name}", url = "${transaction-service.url}")
public interface TransactionClient {

    @PostMapping(value = "/")
    TransactionDto create(@RequestBody TransactionCreateRequestDto request);

}
