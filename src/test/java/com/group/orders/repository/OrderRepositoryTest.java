package com.group.orders.repository;

import com.group.orders.constants.CommonMethods;
import com.group.orders.model.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrderItem() {
        OrderItem order = orderRepository.save(CommonMethods.orders().get(0));
        assertEquals(12787878, order.getOrder_id());
    }

    @Test
    public void testCreateOrderItems() {
        List<OrderItem> orders = (List<OrderItem>) orderRepository.saveAll(CommonMethods.orders());

        assertEquals(12787878, orders.get(0).getOrder_id());
        assertEquals(12787879, orders.get(1).getOrder_id());
        assertEquals(2, orders.size());
    }

    @Test
    public void testListOrderItems() {
        List<OrderItem> orderItems = orderRepository.findAll();
        System.out.println((orderItems).size());
        assertEquals(0, orderItems.size());
    }

    @Test
    //@Rollback(false)
    public void testTruncate() {
        orderRepository.truncate();
        List<OrderItem> orderItems = orderRepository.findAll();
        System.out.println((orderItems).size());
        assertEquals(orderItems.size(), 0);
    }
}
