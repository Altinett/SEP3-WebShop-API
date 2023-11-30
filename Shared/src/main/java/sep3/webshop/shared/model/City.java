package sep3.webshop.shared.model;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private int zipcode;

    public City() {}

    public City(String name, int zipcode) {
        this.name = name;
        this.zipcode = zipcode;
    }
    public String getName() {
        return name;
    }
    public int getZipcode() {
        return zipcode;
    }
}
