package com.group.orders.dao;
import com.group.orders.model.OrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDto {

    private final List<OrderItem> orders;
    private final Map<String, Integer> page;

    public OrderDto(final List<OrderItem> orders, final Map<String, Integer> page) {
        this.orders = new ArrayList<>(orders);
        this.page = new HashMap<>(page);
    }

    public static OrderDto create(final List<OrderItem> orders, final Map<String, Integer> page) {
        return new OrderDto(orders, page);
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public Map<String, Integer> getPage() {
        return page;
    }
}