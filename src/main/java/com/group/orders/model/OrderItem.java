package com.group.orders.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class OrderItem {

    @CsvBindByName(column = "Region")
    private String region;
    @CsvBindByName(column = "Country")
    private String country;
    @CsvBindByName(column = "Item Type")
    private String item_type;
    @CsvBindByName(column = "Sales Channel")
    private String sales_channel;
    @CsvBindByName(column = "Order Priority")
    private String order_priority;
    @CsvBindByName(column = "Order Date")
    private String order_date;
    @Id
    @CsvBindByName(column = "Order ID")
    private int order_id;
    @CsvBindByName(column = "Ship Date")
    private String ship_date;
    @CsvBindByName(column = "Units Sold")
    private int units_sold;
    @CsvBindByName(column = "Unit Price")
    private float unit_price;
    @CsvBindByName(column = "Unit Cost")
    private float unit_cost;
    @CsvBindByName(column = "Total Revenue")
    private float total_revenue;
    @CsvBindByName(column = "Total Cost")
    private float total_cost;
    @CsvBindByName(column = "Total Profit")
    private float total_profit;
    private String nric;

    public OrderItem() {
    }

    public OrderItem(String region, String country, String item_type, String sales_channel, String order_priority, String order_date, int order_id, String ship_date, int units_sold, float unit_price, float unit_cost, float total_revenue, float total_cost, float total_profit, String nric) {
        this.region = region;
        this.country = country;
        this.item_type = item_type;
        this.sales_channel = sales_channel;
        this.order_priority = order_priority;
        this.order_date = order_date;
        this.order_id = order_id;
        this.ship_date = ship_date;
        this.units_sold = units_sold;
        this.unit_price = unit_price;
        this.unit_cost = unit_cost;
        this.total_revenue = total_revenue;
        this.total_cost = total_cost;
        this.total_profit = total_profit;
        this.nric = nric;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getSales_channel() {
        return sales_channel;
    }

    public void setSales_channel(String sales_channel) {
        this.sales_channel = sales_channel;
    }

    public String getOrder_priority() {
        return order_priority;
    }

    public void setOrder_priority(String order_priority) {
        this.order_priority = order_priority;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getShip_date() {
        return ship_date;
    }

    public void setShip_date(String ship_date) {
        this.ship_date = ship_date;
    }

    public int getUnits_sold() {
        return units_sold;
    }

    public void setUnits_sold(int units_sold) {
        this.units_sold = units_sold;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public float getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(float unit_cost) {
        this.unit_cost = unit_cost;
    }

    public float getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(float total_revenue) {
        this.total_revenue = total_revenue;
    }

    public float getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost = total_cost;
    }

    public float getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(float total_profit) {
        this.total_profit = total_profit;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    @Override
    public String toString() {
        return "Order{" +
                "region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", item_type='" + item_type + '\'' +
                ", sales_channel='" + sales_channel + '\'' +
                ", order_priority='" + order_priority + '\'' +
                ", order_date='" + order_date + '\'' +
                ", order_id=" + order_id +
                ", ship_date='" + ship_date + '\'' +
                ", units_sold=" + units_sold +
                ", unit_price=" + unit_price +
                ", unit_cost=" + unit_cost +
                ", total_revenue=" + total_revenue +
                ", total_cost=" + total_cost +
                ", total_profit=" + total_profit +
                ", nric='" + nric + '\'' +
                '}';
    }
}
