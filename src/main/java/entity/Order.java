package entity;

import java.time.LocalDate;

public class Order {
    private String _id;
    private LocalDate date;
    private double total;
    private Customer customer;

    public Order(String _id, LocalDate date, double total, Customer customer) {
        this._id = _id;
        this.date = date;
        this.total = total;
        this.customer = customer;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "_id='" + _id + '\'' +
                ", date=" + date +
                ", total=" + total +
                ", customer=" + customer +
                '}';
    }
}
