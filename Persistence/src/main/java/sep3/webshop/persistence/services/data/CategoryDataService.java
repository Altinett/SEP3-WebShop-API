package sep3.webshop.persistence.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.persistence.utils.Empty;
import sep3.webshop.persistence.utils.RequestHandler;
import sep3.webshop.shared.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Scope("singleton")
public class CategoryDataService {
    private final DatabaseHelper<Category> helper;

    @Autowired
    public CategoryDataService(
            DatabaseHelper<Category> helper,
            RequestQueueListener listener
    ) {
        this.helper = helper;

        listener.on("getCategories", RequestHandler.newObserver(this::getCategories));
        listener.on("editCategory", RequestHandler.newObserver(this::editCategory));
        listener.on("getCategory", RequestHandler.newObserver(this::getCategory));
    }
    private Category editCategory(Category category) throws SQLException {
        return helper.mapSingle(
            CategoryDataService::createCategory,
            "UPDATE Categories SET name=? WHERE id=? RETURNING *",
            category.getId(),
            category.getName()
        );
    }
    private Category getCategory(int id) throws SQLException {
        return helper.mapSingle(
            CategoryDataService::createCategory,
            "SELECT * FROM Categories WHERE id=? LIMIT 1",
            id
        );
    }

    private List<Category> getCategories(Empty empty) throws SQLException {
        return helper.map(
            CategoryDataService::createCategory,
            "SELECT * FROM Categories"
        );
    }

    private static Category createCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

}
