package org.example.model;

import org.assertj.core.api.Assertions;

import java.util.List;

public class GetOrderResponse {

    boolean success;
    List<OrderWithHashes> orders;
    int total;
    int totalToday;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OrderWithHashes> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderWithHashes> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }

    public static void assertEqualResponses(GetOrderResponse expected, GetOrderResponse actual){
        Assertions.assertThat(actual).usingRecursiveComparison().comparingOnlyFields(
                "success", "orders.ingredients", "orders.number", "orders._id", "orders.status", "orders.name", "orders.createdAt", "orders.updatedAt")
                .isEqualTo(expected);
    }

    public static void assertEqualComparingField(GetOrderResponse expected, GetOrderResponse actual, String fieldName){
        Assertions.assertThat(actual).usingRecursiveComparison().comparingOnlyFields("success", fieldName).isEqualTo(expected);
    }
}
