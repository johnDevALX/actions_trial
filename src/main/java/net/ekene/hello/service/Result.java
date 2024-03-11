package net.ekene.hello.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    private boolean success;
    private String message;
    private byte[] data;

    public static Result success(String message, byte[] data) {
        return Result.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static Result failure(String message) {
        return Result.builder()
                .success(false)
                .message(message)
                .build();
    }
}