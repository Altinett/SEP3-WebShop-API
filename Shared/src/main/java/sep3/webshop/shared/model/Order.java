package sep3.webshop.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

public class Order implements Serializable {
    long millis = System.currentTimeMillis();
    private int orderId;
    private String firstname, lastname, address;
    private int postcode;
    private Timestamp date = new Timestamp(millis);
    private boolean status;
    private BigDecimal total;
    private int phonenumber;
    private String email;
    private Map<Integer, Integer> products;

    public Order() {}

    public Order(String firstname, String lastname, String address, int postcode, boolean status, BigDecimal total, int phonenumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.postcode = postcode;
        this.status = status;
        this.total = total;
        this.phonenumber = phonenumber;
        this.email = email;
        this.date = new Timestamp(millis);
    }

    public Order (int orderId, String firstname, String lastname, String address, int postcode, boolean status, BigDecimal total, int phoneNumber, String email, Timestamp date) {
        this(firstname, lastname, address, postcode, status, total, phoneNumber, email);
        this.orderId = orderId;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getDate() {
        if (date == null) {
            date = new Timestamp(millis);
        }
        return date;
    }
    public boolean getStatus() {
        return status;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public int getPhonenumber() {
        return phonenumber;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getAddress() {
        return address;
    }
    public int getPostcode() {
        return postcode;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }
    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }
}
