package sep3.webshop.persistence.utils;

import sep3.webshop.shared.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

public class ProductFilter {
    public static Predicate<Product> categoryFilter(List<Integer> categories) {
        return product -> categories.isEmpty() || categories.stream().anyMatch(product.getCategoryIds()::contains);
    }
    public static Predicate<Product> priceRangeFilter(Integer min, Integer max) {
        return product -> {
            BigDecimal price = product.getPrice();
            return (min == null || price.compareTo(BigDecimal.valueOf(min)) >= 0) &&
                    (max == null || price.compareTo(BigDecimal.valueOf(max)) <= 0);
        };
    }
    public static List<Product> filterProducts(List<Product> products, Predicate<Product>... filters) {
        return products.stream().filter(product -> {
            for (Predicate<Product> filter : filters) {
                if (!filter.test(product)) {
                    return false;
                }
            }
            return true;
        }).toList();
    }
}
