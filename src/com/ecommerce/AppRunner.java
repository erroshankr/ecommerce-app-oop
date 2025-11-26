package com.ecommerce;

import com.ecommerce.exceptions.EcommerceException;
import com.ecommerce.models.*;
import com.ecommerce.service.*;

import java.util.List;
import java.util.Scanner;

public class AppRunner {

    private final CatalogService catalogService;
    private final CustomerService customerService;
    private final CartService cartService;
    private final OrderService orderService;
    private final InventoryService inventoryService;

    public AppRunner(CatalogService catalogService,
               CustomerService customerService,
               CartService cartService,
               OrderService orderService, InventoryService inventoryService) {
        this.catalogService = catalogService;
        this.customerService = customerService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.inventoryService = inventoryService;
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
                if (command.equalsIgnoreCase("help")) {
                    printHelp();
                } else if (command.equalsIgnoreCase("add-product")) {
                    handleAddProduct(scanner);
                } else if (command.equalsIgnoreCase("list-products")) {
                    handleListProducts();
                } else if (command.equalsIgnoreCase("register-customer")) {
                    handleRegisterCustomer(scanner);
                } else if (command.equalsIgnoreCase("add-to-cart")) {
                    handleAddToCart(scanner);
                } else if (command.equalsIgnoreCase("view-cart")) {
                    handleViewCart(scanner);
                } else if (command.equalsIgnoreCase("checkout")) {
                    handleCheckout(scanner);
                } else if (command.equalsIgnoreCase("cancel-order")) {
                    handleCancelOrder(scanner);
                } else if (command.startsWith("orders")) {
                    handleOrdersForCustomer(scanner, command);
                } else if (command.equalsIgnoreCase("exit") ||
                        command.equalsIgnoreCase("quit")) {
                    running = false;
                } else {
                    System.out.println("Unknown command. Type 'help' for list.");
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
        System.out.println("  add-product       - add a new product");
        System.out.println("  list-products     - list all products");
        System.out.println("  register-customer - register a new customer");
        System.out.println("  add-to-cart       - add item to customer cart");
        System.out.println("  view-cart         - view customer cart");
        System.out.println("  checkout          - checkout cart and create order");
        System.out.println("  cancel-order      - cancel an order");
        System.out.println("  orders            - list orders for a customer");
        System.out.println("  help              - show this help");
        System.out.println("  exit / quit       - exit application");
    }

    // ----------------- COMMAND HANDLERS -----------------

    // add-product — prompts for SKU, name, category, price, quantity
    private void handleAddProduct(Scanner scanner) throws Exception {
        System.out.print("SKU: ");
        String sku = scanner.nextLine().trim();

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Category: ");
        String category = scanner.nextLine().trim();

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Available Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        ProductModel p = new ProductModel();
        p.setSku(sku);
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setAvailableQuantity(qty);

        catalogService.addProduct(p);

        System.out.println("Product added.");
    }

    // list-products — show all products
    private void handleListProducts() throws Exception {
        List<ProductModel> products = catalogService.findAll();

        if (products.isEmpty()) {
            System.out.println("No products.");
            return;
        }

        System.out.println("SKU    | Name                 | Category     | Price   | Qty");
        System.out.println("---------------------------------------------------------------");

        int i;
        for (i = 0; i < products.size(); i++) {
            ProductModel p = products.get(i);
            System.out.printf("%-6s | %-20s | %-11s | %7.2f | %4d%n",
                    p.getSku(),
                    p.getName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getAvailableQuantity());
        }
    }

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
