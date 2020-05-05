package com.ws.spring.account.dto.response;

import com.ws.spring.account.dto.request.TransferRequestDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferResponseDto extends TransferRequestDto {

    private LocalDateTime dateTime;
    private String transactionNumber;

}
