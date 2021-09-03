package com.group.orders.repository;

import com.group.orders.model.OrderItem;
import com.group.orders.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BatchExecutor {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchInsertAsync(List<OrderItem> orders) throws InterruptedException, ExecutionException {
        StopWatch timer = new StopWatch();
        String sql = "INSERT INTO ORDER_ITEM (REGION, COUNTRY, ITEM_TYPE, SALES_CHANNEL, ORDER_PRIORITY, ORDER_DATE, ORDER_ID, SHIP_DATE, UNITS_SOLD, UNIT_PRICE, UNIT_COST, TOTAL_REVENUE, TOTAL_COST, TOTAL_PROFIT, NRIC)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final AtomicInteger batches = new AtomicInteger();
        CompletableFuture[] futures = orders.stream()
                .collect(Collectors.groupingBy(t -> batches.getAndIncrement() / batchSize))
                .values()
                .stream()
                .map(orderList -> runBatchInsert(orderList, sql))
                .toArray(CompletableFuture[]::new);
        CompletableFuture<Void> run = CompletableFuture.allOf(futures);
        timer.start();
        run.get();
        timer.stop();
        LOGGER.info("batchInsertAsync -> Time taken: " + timer.getTotalTimeSeconds());
    }

    private CompletableFuture<Void> runBatchInsert(List<OrderItem> orders, String sql) {
        return CompletableFuture.runAsync(() -> {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    OrderItem order = orders.get(i);
                    ps.setString(1, order.getRegion());
                    ps.setString(2, order.getCountry());
                    ps.setString(3, order.getItem_type());
                    ps.setString(4, order.getSales_channel());
                    ps.setString(5, order.getOrder_priority());
                    ps.setString(6, order.getOrder_date());
                    ps.setInt(7, order.getOrder_id());
                    ps.setString(8, order.getShip_date());
                    ps.setInt(9, order.getUnits_sold());
                    ps.setFloat(10, order.getUnit_price());
                    ps.setFloat(11, order.getUnit_cost());
                    ps.setFloat(12, order.getTotal_revenue());
                    ps.setFloat(13, order.getTotal_cost());
                    ps.setFloat(14, order.getTotal_profit());
                    ps.setString(15, order.getNric());
                }

                public int getBatchSize() {
                    return orders.size();
                }
            });
        }, executor);
    }
}