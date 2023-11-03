package sep3.webshop.shared.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private String firstname, lastname;
    private String address;
    private int postcode;
    private Date date;
    private boolean status;
    private int total;
    private int phoneNumber;
    private String email;
    private List<Integer> productIds;

    public Order() {}

    public Order(
        String firstname,
        String lastname,
        String address,
        int postcode,
        Date date,
        boolean status,
        int total,
        int phoneNumber,
        String email
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.postcode = postcode;
        this.date = date;
        this.status = status;
        this.total = total;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Order (
        int orderId,
        String firstname,
        String lastname,
        String address,
        int postcode,
        Date date,
        boolean status,
        int total,
        int phoneNumber,
        String email,
        List<Integer> productIds
    ) {
        this(firstname, lastname, address, postcode, date, status, total, phoneNumber, email);
        this.orderId = orderId;
        this.productIds = productIds;
    }

    public Order (
            int orderId,
            String firstname,
            String lastname,
            String address,
            int postcode,
            Date date,
            boolean status,
            int total,
            int phoneNumber,
            String email
    ) {
        this(firstname, lastname, address, postcode, date, status, total, phoneNumber, email);
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public int getPhoneNumber() {
        return phoneNumber;
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

    public List<Integer> getProductIds() {
        return productIds;
    }
    public void setProductIds(List<Integer> ids) {
        productIds = ids;
    }
}
