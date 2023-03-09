package org.example.model;

import org.assertj.core.api.Assertions;

public class UserResponse {
    boolean success;
    User user;
    String accessToken;
    String refreshToken;

    public boolean isSuccess() {
        return success;
    }

    public UserResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String toString(){
        return String.format("UserResponse object with values: success=%s, user =  %s, accessToken = %s, refreshToken = %s",
                success, user, accessToken, refreshToken);
    }

    public static void assertEqualResponses(UserResponse expected, UserResponse actual){
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("accessToken", "refreshToken")
                .isEqualTo(expected);
    }

}
