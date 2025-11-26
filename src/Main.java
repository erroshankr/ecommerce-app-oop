import com.ecommerce.AppRunner;
import com.ecommerce.repo.*;
import com.ecommerce.repo.impl.csv.*;
import com.ecommerce.service.*;
import com.ecommerce.service.impl.*;

import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// main --> service --> repo --> model
public class Main {
    public static void main(String[] args) {
      Path dataDir = Path.of("data");

      //setup all repositories
      ProductRepository productRepository = new ProductCsvRepositoryImpl(dataDir.resolve("products.csv"));
      CustomerRepository customerRepository = new CustomerCsvRepoImpl(dataDir.resolve("customers.csv"));
      CartRepository cartRepository = new CartCsvRepositoryImpl(dataDir.resolve("carts.csv"));
      CartItemRepository cartItemRepository = new CartItemCsvRepositoryImpl(dataDir.resolve("cart_items.csv"));
      OrderItemRepository orderItemRepository = new OrderItemCsvRepositoryImpl(dataDir.resolve("order_items.csv"));
      OrderRepository orderRepository = new OrderCsvRepositoryImpl(dataDir.resolve("order.csv"));

      //setup all services
      CatalogService  catalogService = new CatalogServiceImpl(productRepository);
      InventoryService  inventoryService = new InventoryServiceImpl();
      CartService cartService = new CartServiceImpl();
      CustomerService customerService = new CustomerServiceImpl();
      OrderService orderService = new OrderServiceImpl();


        //Since main will interact with services only, so pass all services
      AppRunner app = new AppRunner(catalogService,
              customerService,cartService,
              orderService,inventoryService);

      app.run();
    }
}