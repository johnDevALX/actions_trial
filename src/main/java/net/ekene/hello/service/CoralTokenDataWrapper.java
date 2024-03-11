package net.ekene.hello.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoralTokenDataWrapper {
    @JsonProperty("tokenData")
    private TokenData tokenData;

}
