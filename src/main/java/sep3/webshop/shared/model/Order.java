package sep3.webshop.shared.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.sql.Date;
import java.util.List;

public class Order {
    long millis = System.currentTimeMillis();
    private int orderId;
    private String firstname, lastname, address;
    private int postcode;
    private Date date = new Date(millis);
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
        boolean status,
        int total,
        int phoneNumber,
        String email
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.postcode = postcode;
        this.date = new java.sql.Date(millis);
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
        boolean status,
        int total,
        int phoneNumber,
        String email,
        List<Integer> productIds
    ) {
        this(firstname, lastname, address, postcode, status, total, phoneNumber, email);
        this.orderId = orderId;
        this.date = new Date(millis);
        System.out.println(new Date(millis));
        this.productIds = productIds;
    }

    public Order (
            int orderId,
            String firstname,
            String lastname,
            String address,
            int postcode,
            boolean status,
            int total,
            int phoneNumber,
            String email
    ) {
        this(firstname, lastname, address, postcode, status, total, phoneNumber, email);
        this.orderId = orderId;
        this.date = new java.sql.Date(millis);
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getDate() {
        return date;
    }

    public boolean getStatus() {
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
