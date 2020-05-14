package com.ws.spring.account.dto.response;

import com.ws.spring.account.dto.request.CreditRequestDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreditResponseDto extends CreditRequestDto {

    private LocalDateTime dateTime;
    private Integer transactionNumber;

}
