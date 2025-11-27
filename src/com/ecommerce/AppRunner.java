package com.ecommerce;

import com.ecommerce.exceptions.EcommerceException;
import com.ecommerce.handler.ProductHandler;
import com.ecommerce.models.CartItemModel;
import com.ecommerce.models.CartModel;
import com.ecommerce.models.CustomerModel;
import com.ecommerce.models.OrderModel;
import com.ecommerce.service.CartService;
import com.ecommerce.service.CatalogService;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.OrderService;

import java.util.List;
import java.util.Scanner;

public class AppRunner {

    private final CatalogService catalogService;
    private final CustomerService customerService;
    private final CartService cartService;
    private final OrderService orderService;
    private final ProductHandler productHandler;

    public AppRunner(CatalogService catalogService,
                     CustomerService customerService,
                     CartService cartService,
                     OrderService orderService, ProductHandler productHandler) {
        this.catalogService = catalogService;
        this.customerService = customerService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.productHandler = productHandler;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Ecommerce CLI");
        printHelp();

        boolean running = true;
        while (running) {
            System.out.print("\n> ");
            String command = scanner.nextLine().trim();

            try {
                // Normalize the command to lowercase to simulate 'equalsIgnoreCase'
                String normalizedCommand = command.trim().toLowerCase();

                switch (normalizedCommand) {
                    case "help" -> printHelp();
                    case "add-product" -> productHandler.handleAddProduct(scanner);
                    case "list-products" -> productHandler.handleListProducts();
                    case "find-productbyid" -> productHandler.handleFindProductById(scanner);
                    case "find-productbysku" -> productHandler.handleFindProductBySku(scanner);
                    case "update-productbysku" -> productHandler.handleUpdateProductBySku(scanner);
                    case "delete-productbyid" -> productHandler.handleProductDeletionById(scanner);
                    case "delete-productbysku" -> productHandler.handleProductDeletionBySku(scanner);
                    case "check-stock" -> productHandler.handleAvailableStock(scanner);
                    case "register-customer" -> handleRegisterCustomer(scanner);
                    case "add-to-cart" -> handleAddToCart(scanner);
                    case "view-cart" -> handleViewCart(scanner);
                    case "checkout" -> handleCheckout(scanner);
                    case "cancel-order" -> handleCancelOrder(scanner);
                    case "exit", "quit" -> running = false;
                    default -> {
                        // Handle the 'startsWith' logic here because switch requires exact matches
                        if (normalizedCommand.startsWith("orders")) {
                            handleOrdersForCustomer(scanner, command);
                        } else {
                            System.out.println("Unknown command. Type 'help' for list.");
                        }
                    }
                }
            } catch (EcommerceException e) {
                System.out.println("Business error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }

        System.out.println("Bye!");
        scanner.close();
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  add-product         - add a new product");
        System.out.println("  list-products       - list all products");
        System.out.println("  find-productById    - find product by Id");
        System.out.println("  find-productBySku   - find product by Sku");
        System.out.println("  update-productBySku - update product by Sku");
        System.out.println("  delete-productById  - delete product by Id");
        System.out.println("  delete-productBySku - delete product by Sku");
        System.out.println("  check-stock         - check stock");
        System.out.println("  register-customer   - register a new customer");
        System.out.println("  add-to-cart         - add item to customer cart");
        System.out.println("  view-cart           - view customer cart");
        System.out.println("  checkout            - checkout cart and create order");
        System.out.println("  cancel-order        - cancel an order");
        System.out.println("  orders              - list orders for a customer");
        System.out.println("  help                - show this help");
        System.out.println("  exit / quit         - exit application");
    }

    // ----------------- COMMAND HANDLERS -----------------


    // register-customer — prompts for customer details
    private void handleRegisterCustomer(Scanner scanner) throws Exception {
        System.out.print("Customer ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Address: ");
        String address = scanner.nextLine().trim();

        CustomerModel c = new CustomerModel();
        c.setId(id);
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);

     //   customerService.registerCustomer(c);
        System.out.println("Customer registered.");
    }

    // add-to-cart — customerId, sku, quantity
    private void handleAddToCart(Scanner scanner) throws EcommerceException {
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine().trim();

        System.out.print("Product SKU: ");
        String sku = scanner.nextLine().trim();

        System.out.print("Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        cartService.addToCart(customerId, sku, qty);
        System.out.println("Item added to cart.");
    }

    // view-cart — show cart and subtotal
    private void handleViewCart(Scanner scanner) {
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine().trim();

        CartModel cart = cartService.getCart(customerId);
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("Cart for customer " + customerId + ":");
        System.out.println("SKU    | Qty | Unit Price | Line Total");
        System.out.println("--------------------------------------");

        List<CartItemModel> items = cart.getItems();
        int i;
        for (i = 0; i < items.size(); i++) {
            CartItemModel item = items.get(i);
            double lineTotal = item.getUnitPrice() * item.getQuantity();
            System.out.printf("%-6s | %3d | %10.2f | %10.2f%n",
                    item.getSku(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    lineTotal);
        }

        System.out.printf("Subtotal: %.2f%n", cart.getSubtotal());
    }

    // checkout — simulate payment and create order
    private void handleCheckout(Scanner scanner) throws EcommerceException {
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine().trim();

        OrderModel order = orderService.checkout(customerId);
        System.out.println("Order created with ID: " + order.getId());
        System.out.println("Status   : " + order.getOrderStatus().getDisplayName());
        System.out.printf("Subtotal : %.2f%n", order.getSubtotal());
        System.out.printf("Taxes    : %.2f%n", order.getTaxes());
        System.out.printf("Shipping : %.2f%n", order.getShipping());
        System.out.printf("Total    : %.2f%n", order.getTotal());
    }

    // cancel-order — cancel and release inventory
    private void handleCancelOrder(Scanner scanner) throws EcommerceException {
        System.out.print("Order ID: ");
        String orderId = scanner.nextLine().trim();

        orderService.cancelOrder(orderId);
        System.out.println("Order cancelled (if it was eligible).");
    }

    // orders customerId — list orders for customer
    private void handleOrdersForCustomer(Scanner scanner, String command) throws EcommerceException {
        // accept either "orders" then prompt, or "orders C001"
        String customerId = null;
        String[] parts = command.split("\\s+");
        if (parts.length >= 2) {
            customerId = parts[1];
        } else {
            System.out.print("Customer ID: ");
            customerId = scanner.nextLine().trim();
        }

        List<OrderModel> orders = orderService.getOrdersForCustomer(customerId);
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders for customer " + customerId);
            return;
        }

        System.out.println("Orders for customer " + customerId + ":");
        int i;
        for (i = 0; i < orders.size(); i++) {
            OrderModel o = orders.get(i);
            System.out.printf("%s | %s | total=%.2f%n",
                    o.getId(),
                    o.getOrderStatus().getDisplayName(),
                    o.getTotal());
        }
    }
}
