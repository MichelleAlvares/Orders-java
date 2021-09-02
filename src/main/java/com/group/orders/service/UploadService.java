package com.group.orders.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.group.orders.repository.OrderRepository;
import com.group.orders.dao.OrderDto;
import com.group.orders.model.OrderItem;
import com.group.orders.util.Util;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    private Logger LOGGER = LoggerFactory.getLogger(UploadService.class);
    private OrderRepository orderRepository;
    private Util util;

    @Value("${page.size}")
    private int PAGE_SIZE;

    public UploadService(@Autowired OrderRepository orderRepository, @Autowired Util util) {
        this.orderRepository = orderRepository;
        this.util = util;
    }

    public String uploadFile(MultipartFile file, Model model) {
        List<OrderItem> orders;
        if (util.isCSVFile(file)) {
            try {
                orders = parseCsvFile(file.getInputStream());
            } catch (Exception e) {
                model.addAttribute("message", "Error while processing file");
                model.addAttribute("status", false);
                return "index";
            }
            LOGGER.info("Start Truncate");
            orderRepository.truncate();
            LOGGER.info("End Truncate");
            LOGGER.info("Inserting data");
            orderRepository.saveAll(orders);
            LOGGER.info("Data Inserted");
            model.addAttribute("status", true);
            model.addAttribute("responseEntity", getOrders(1));
            return "display";
        }else {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
            return "index";
        }
    }

    private List<OrderItem> parseCsvFile(InputStream is) {
        LOGGER.info("Parsing file");
        BufferedReader fileReader = null;
        CSVParser csvParser = null;

        List<OrderItem> orders = new ArrayList<>();

        try {
            fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withNullString(""));

            Iterable<CSVRecord> records = csvParser.getRecords();

            for (CSVRecord record : records) {
                util.validateRecord(record, orders);
            }
            LOGGER.info("NRIC numbers generated {}", orders.size());

        } catch (Exception e) {
            LOGGER.debug("Reading CSV Error!");
            e.printStackTrace();
        } finally {
            try {
                assert fileReader != null;
                fileReader.close();
                assert csvParser != null;
                csvParser.close();
            } catch (IOException e) {
                LOGGER.debug("Closing fileReader/csvParser Error!");
                e.printStackTrace();
            }
        }
        return orders;
    }

    public OrderDto getOrders(final int pageNumber) {
        LOGGER.info("Getting orders for page-number = {} and page-size = {}.", pageNumber, PAGE_SIZE);
        final Page<OrderItem> paginatedOrders = getPaginatedOrders(pageNumber);
        return createResponseDto(paginatedOrders, pageNumber);
    }

    Page<OrderItem> getPaginatedOrders(final int pageNumber) {
        LOGGER.info("Fetching the paginated residents from the dB.");
        final Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return orderRepository.findAll(pageable);
    }

    public OrderDto createResponseDto(final Page<OrderItem> orderItemPage, final int pageNumber) {
        final Map<String, Integer> page = new HashMap<>();
        page.put("currentPage", pageNumber);
        page.put("totalPages", orderItemPage.getTotalPages());
        page.put("totalElements", (int) orderItemPage.getTotalElements());
        LOGGER.debug("pageNumber {}", pageNumber);
        LOGGER.debug("totalPages {}", orderItemPage.getTotalPages());
        LOGGER.debug("totalElements {}", (int) orderItemPage.getTotalElements());
        return OrderDto.create(orderItemPage.getContent(), page);
    }


}
