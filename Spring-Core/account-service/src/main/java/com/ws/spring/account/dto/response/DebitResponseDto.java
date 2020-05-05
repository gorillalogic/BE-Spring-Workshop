package com.ws.spring.account.dto.response;

import com.ws.spring.account.dto.request.DebitRequestDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DebitResponseDto extends DebitRequestDto {

    private LocalDateTime dateTime;
    private String transactionNumber;

}
