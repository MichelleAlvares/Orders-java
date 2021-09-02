package com.group.orders.controller;

import com.group.orders.constants.CommonMethods;
import com.group.orders.dao.OrderDto;
import com.group.orders.model.OrderItem;
import com.group.orders.service.UploadService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static java.util.Map.of;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UploadController.class)
public class UploadControllerTest {

    @MockBean
    private UploadService uploadService;
    @MockBean
    private LinkDiscoverers linkDiscoverers;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testGetDisplay() throws Exception {
        when(uploadService.getOrders(anyInt())).thenReturn(CommonMethods.response());

        mockMvc.perform(get("/csv/{page}", 5))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadCSVFile() throws Exception {
        when(uploadService.uploadFile(any(), any())).thenReturn("index");

        MockMultipartFile csvFile = new MockMultipartFile("file","file.csv", MediaType.TEXT_PLAIN_VALUE, "Testing if csv file!".getBytes());

        MockMvc mockmvc  = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockmvc.perform(multipart("/csv").file(csvFile))
                .andExpect(status().isOk());
    }
}
