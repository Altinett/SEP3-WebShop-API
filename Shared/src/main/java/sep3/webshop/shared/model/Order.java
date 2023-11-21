package sep3.webshop.shared.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {
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
    private Map<Integer, Integer> products;

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
        this.date = new Date(millis);
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
        this.date = new Date(millis);
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
            Map<Integer, Integer> products
    ) {
        this(firstname, lastname, address, postcode, status, total, phoneNumber, email);
        this.orderId = orderId;
        this.date = new Date(millis);
        this.products = products;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
    public Map<Integer, Integer> getProducts() {
        return products;
    }
}
