package com.group.orders.util;

import com.group.orders.constants.AppConstants;
import com.group.orders.model.OrderItem;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Service
public class Util {
    @Value("${file.extension}")
    private String csvExtension;

    public static Predicate<Object> isNotNull = Objects::nonNull;

    public void validateRecord(CSVRecord record, List<OrderItem> orders){
        for(int i =0; i<14; i++){
            if(record.get(i) == null)
                return;
        }
        OrderItem order = new OrderItem(record.get("Region"), record.get("Country"), record.get("Item Type"), record.get("Sales Channel"),
                record.get("Order Priority"), record.get("Order Date"), Integer.parseInt(record.get("Order ID")), record.get("Ship Date"),
                Integer.parseInt(record.get("Units Sold")), Float.parseFloat(record.get("Unit Price")), Float.parseFloat(record.get("Unit Cost")),
                Float.parseFloat(record.get("Total Revenue")), Float.parseFloat(record.get("Total cost")), Float.parseFloat(record.get("Total Profit")),
                generateNRIC());
        orders.add(order);
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
