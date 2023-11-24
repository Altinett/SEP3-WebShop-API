package sep3.webshop.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Product implements Serializable {
    private int id, amount;
    private String name, description;
    private BigDecimal price;
    private String image;
    private List<Integer> categoryIds;
    private boolean flagged;

    public Product() {}

    public Product(int id, String name, String description, BigDecimal price, int amount, String image, boolean flagged) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.image = image;
        this.flagged = flagged;
    }
    public Product(int id, String name, String description, BigDecimal price, List<Integer> categoryIds, int amount, String image, boolean flagged) {
        this(id, name, description, price, amount, image ,flagged);
        this.categoryIds = categoryIds;
    }
    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getImage() {
        return image;
    }
    public boolean isFlagged() {
        return flagged;
    }
}
