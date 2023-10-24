package sep3.webshop.shared.model;

import java.sql.Date;

public class Order {
    private int id;
    private String fullname;
    private String street;
    private int postcode;
    private Date date;
    private boolean status;
    private int total;
    private int phoneNumber;

    public Order(
            int id,
            String fullname,
            String street,
            int postcode,
            Date date,
            boolean status,
            int total,
            int phoneNumber
    ) {
        this.id = id;
        this.fullname = fullname;
        this.street = street;
        this.postcode = postcode;
        this.date = date;
        this.status = status;
        this.total = total;
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
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

    public String getFullname() {
        return fullname;
    }

    public String getStreet() {
        return street;
    }

    public int getPostcode() {
        return postcode;
    }
}
