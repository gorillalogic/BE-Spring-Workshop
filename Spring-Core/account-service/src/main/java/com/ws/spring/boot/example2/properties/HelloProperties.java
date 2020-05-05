package com.ws.spring.boot.example2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//@Validated
@Data
@ConfigurationProperties("example2.property.hello")
@Component
public class HelloProperties {

    @NotNull @NotEmpty
    private String worldX;
    private String moon;

}
