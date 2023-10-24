package sep3.webshop.shared.model;

public class Product {
    private int id, categoryId, amount;
    private String name, description;
    private double price;

    public Product() {}

    public Product(int id, String name, String description, double price, int categoryId, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.amount = amount;
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
    public double getPrice() {
        return price;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public int getAmount() {
        return amount;
    }
}
