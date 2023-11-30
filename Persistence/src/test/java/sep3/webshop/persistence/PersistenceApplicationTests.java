package sep3.webshop.persistence;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import sep3.webshop.persistence.main.PersistenceApplication;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.shared.model.Order;

@SpringBootTest(classes = PersistenceApplication.class)
@SpringJUnitConfig
class PersistenceApplicationTests {
	@Autowired @Qualifier("ORDER_TEST") private DatabaseHelper<Order> orderHelper;

	@Before
	public void Setup() {

	}
	@Test
	void contextLoads() {
		assertNotNull(orderHelper);
	}

}

