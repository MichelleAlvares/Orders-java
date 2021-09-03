package com.group.orders.util;

import com.group.orders.constants.AppConstants;
import com.group.orders.repository.OrderRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {


    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private Model model;
    @Autowired
    private Util util;

    private MockMultipartFile txtFile = new MockMultipartFile("file", "file.txt", MediaType.TEXT_PLAIN_VALUE, "Testing if csv file!".getBytes());
    private MockMultipartFile csvFile = new MockMultipartFile("file", "file.csv", MediaType.TEXT_PLAIN_VALUE, ("Region,Country,Item Type,Sales Channel,Order Priority,Order Date,Order ID,Ship Date,Units Sold,Unit Price,Unit Cost,Total Revenue,Total cost,Total Profit" +
            "\nEurope, Spain, Fruit, Online, M, 12/3/2021, 12787878, 10/3/2021, 55, 55, 55, 700, 700, 90" +
            "\nEurope, Spain, Fruit, Online, H, 12/3/2021, 12787878, 20/3/2021, 55, 55, 55, 700, 700, 90").getBytes());

    @Test
    public void appendLastCharTest() {
        assertEquals("G5659479K", util.appendLastChar('G', 5659479));
        assertEquals("T5682265D", util.appendLastChar('T', 5682265));
        assertEquals("S2205487B", util.appendLastChar('S', 2205487));
        assertEquals("F8569444T", util.appendLastChar('F', 8569444));
    }

    @Test
    public void getFirstCharTest() {
        assertTrue(AppConstants.nricSartingCharacter.contains(util.getFirstChar()));
    }

    @Test
    public void random7DigitNumberTest() {
        assertEquals(7, String.valueOf(util.random7DigitNumber()).length());
    }

    @Test
    public void testIsCSV() {
        assertFalse(util.isCSVFile(txtFile));
        assertTrue(util.isCSVFile(csvFile));
    }

    @Test
    public void testDateValidator() {
        assertTrue(util.validDate("12/30/2020"));
        assertFalse(util.validDate("14/30/2020"));
        assertFalse(util.validDate("2/30/2020"));
        assertFalse(util.validDate("1/35/2020"));
        assertFalse(util.validDate("12-30-2020"));
    }

    @Test
    public void testValidRecord() throws IOException {
        BufferedReader fileReader = null;
        CSVParser csvParser = null;
        fileReader = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8));
        csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withNullString(""));

        Iterable<CSVRecord> records = csvParser.getRecords();
        records.forEach(record -> {
            if (record.getRecordNumber() == 1)
                assertTrue(util.validRecord(record));
            else assertFalse(util.validRecord(record));
        });
    }
}
