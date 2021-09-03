package com.group.orders.util;

import com.group.orders.constants.AppConstants;
import com.group.orders.model.OrderItem;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class Util {
    @Value("${file.extension}")
    private String csvExtension;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public void validateAndAddRecord(CSVRecord record, List<OrderItem> orders) {

        if (validRecord(record)) {
            OrderItem order = new OrderItem(record.get("Region"), record.get("Country"), record.get("Item Type"), record.get("Sales Channel"),
                    record.get("Order Priority"), record.get("Order Date"), Integer.parseInt(record.get("Order ID")), record.get("Ship Date"),
                    Integer.parseInt(record.get("Units Sold")), Float.parseFloat(record.get("Unit Price")), Float.parseFloat(record.get("Unit Cost")),
                    Float.parseFloat(record.get("Total Revenue")), Float.parseFloat(record.get("Total cost")), Float.parseFloat(record.get("Total Profit")),
                    generateNRIC());
            orders.add(order);
        }
    }

    boolean validRecord(CSVRecord record) {
        for (int i = 0; i < 14; i++) {
            if (record.get(i) == null)
                return false;
            if (i == 5 || i == 7) //Date records
                if (!validDate(record.get(i))) {
                    return false;
                }
            if (i == 3) //Sales channel
                if (!AppConstants.salesChannels.contains(record.get(i)))
                    return false;
            if (i == 4) //order priority
                if (!AppConstants.orderPriorityList.contains(record.get(i)))
                    return false;
        }
        return true;
    }

    boolean validDate(String date) {
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public boolean isCSVFile(MultipartFile file) {
        if (file.isEmpty())
            return false;
        String[] extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        return extension.length > 1 && extension[1].equals(csvExtension);
    }

    private String generateNRIC() {
        return appendLastChar(getFirstChar(), random7DigitNumber());
    }

    char getFirstChar() {
        Random random = new Random();
        return AppConstants.nricSartingCharacter.get(random.nextInt(AppConstants.nricSartingCharacter.size()));
    }

    int random7DigitNumber() {
        int start = (int) Math.pow(10, 7 - 1);
        return start + new Random().nextInt(9 * start);
    }

    String appendLastChar(char firstLetter, int num) {
        int sum;
        int remainder;
        String strNum = String.valueOf(num);

        sum = IntStream.range(0, strNum.length()).map(i -> AppConstants.multipliers.get(i) * Integer.parseInt(String.valueOf(strNum.charAt(i)))).sum();

        if (AppConstants.add4Characters.contains(firstLetter))
            sum += 4;

        remainder = sum % 11;

        if (AppConstants.foreignerCharacters.contains(firstLetter))
            return firstLetter + strNum + AppConstants.foreigner.get(remainder);
        else
            return firstLetter + strNum + AppConstants.singaporean.get(remainder);
    }

}
