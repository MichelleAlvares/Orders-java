package com.group.orders.service;

import com.group.orders.constants.AppConstants;
import com.group.orders.constants.CommonMethods;
import com.group.orders.model.OrderItem;
import com.group.orders.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadServiceTest {

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private Model model;
    @Autowired
    private UploadService uploadService;

    private MockMultipartFile txtFile = new MockMultipartFile("file","file.txt", MediaType.TEXT_PLAIN_VALUE, "Testing if csv file!".getBytes());
    private MockMultipartFile csvFile = new MockMultipartFile("file","file.csv", MediaType.TEXT_PLAIN_VALUE, "Region,Country,Item Type,Sales Channel,Order Priority,Order Date,Order ID,Ship Date,Units Sold,Unit Price,Unit Cost,Total Revenue,Total cost,Total Profit\nEurope, Spain, Fruit, online, M, 12/3/2021, 12787878, 20/3/2021, 55, 55, 55, 700, 700, 90".getBytes());

    @Test
    public void testPageable() {
        uploadService.getPaginatedOrders(5);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(orderRepository).findAll(pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();
        assertNotNull(pageable);
        assertEquals(4, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
    }

    @Test
    public void testParseCsvFile() {
        when(orderRepository.findAll((Pageable) any())).thenReturn(getFindAllResponse());

        assertEquals("display", uploadService.uploadFile(csvFile, model));
        assertEquals("index", uploadService.uploadFile(txtFile, model));
    }

    private Page<OrderItem> getFindAllResponse(){
        List<OrderItem> list = CommonMethods.orders();
        return new PageImpl<>(list);
    }
}
