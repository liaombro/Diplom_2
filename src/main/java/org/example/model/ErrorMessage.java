package org.example.model;

import org.assertj.core.api.Assertions;

public class ErrorMessage {
    boolean success;
    String message;

    public ErrorMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void assertEqualResponses(ErrorMessage expected, ErrorMessage actual){
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
