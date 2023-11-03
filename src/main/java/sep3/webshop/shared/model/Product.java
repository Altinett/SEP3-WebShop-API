package sep3.webshop.shared.model;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private int id, amount;
    private String name, description;
    private BigDecimal price;
    private List<Integer> categoryIds;

    public Product() {}


    public Product(int id, String name, String description, BigDecimal price, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }
    public Product(int id, String name, String description, BigDecimal price, List<Integer> categoryIds, int amount) {
        this(id, name, description, price, amount);
        this.categoryIds = categoryIds;
    }
    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public List<Integer> getCategoryIds() {
        return categoryIds;
    }
    public int getAmount() {
        return amount;
    }
}
