package com.group.orders.constants;

import com.group.orders.dto.OrderDto;
import com.group.orders.model.OrderItem;

import java.util.List;
import java.util.Map;

import static java.util.Map.of;

public class CommonMethods {
    public static OrderDto response() {
        return OrderDto.create(orders(), getPageMap());
    }

    private static Map<String, Integer> getPageMap() {
        return of("totalPages", 3, "currentPage", 1, "totalElements", 27);
    }

    public static List<OrderItem> orders() {
        OrderItem orderItem1 = new OrderItem("Europe", "Spain", "Fruit", "online", "M", "12/3/2021", 12787878, "20/3/2021", 55, 55, 55, 700, 700, 90, "S5555555W");
        OrderItem orderItem2 = new OrderItem("Europe", "Spain", "Fruit", "offline", "M", "12/3/2021", 12787879, "20/3/2021", 55, 55, 55, 700, 700, 90, "S5555555W");
        return List.of(orderItem1, orderItem2);
    }
}
