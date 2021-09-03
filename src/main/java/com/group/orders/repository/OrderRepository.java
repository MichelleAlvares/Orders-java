package com.group.orders.repository;

import com.group.orders.model.OrderItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.util.*;

@RepositoryRestResource
public interface OrderRepository extends PagingAndSortingRepository<OrderItem, Integer> {
    List<OrderItem> findAll();
    @Modifying
    @Query(value = "truncate table order_item", nativeQuery = true)
    @Transactional
    void truncate();
}
