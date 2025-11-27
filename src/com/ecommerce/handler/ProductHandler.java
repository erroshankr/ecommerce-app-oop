package com.ecommerce.handler;

import com.ecommerce.models.ProductModel;
import com.ecommerce.service.CatalogService;

import java.util.List;
import java.util.Scanner;

public class ProductHandler {

    private final CatalogService catalogService;
    public ProductHandler(CatalogService catalogService) {
         this.catalogService = catalogService;
    }

    // add-product — prompts for SKU, name, category, price, quantity
    public void handleAddProduct(Scanner scanner) throws Exception {
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
    public void handleListProducts() throws Exception {
        List<ProductModel> products = catalogService.findAll();

        if (products.isEmpty()) {
            System.out.println("No products.");
            return;
        }

        System.out.println("ID    | SKU    | Name                 | Category     | Price   | Qty");
        System.out.println("---------------------------------------------------------------");

        int i;
        for (i = 0; i < products.size(); i++) {
            ProductModel p = products.get(i);
            System.out.printf("%-6s |%-6s | %-20s | %-11s | %7.2f | %4d%n",
                    p.getId(),
                    p.getSku(),
                    p.getName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getAvailableQuantity());
        }
    }

    public void handleFindProductById(Scanner sc) throws Exception {
        String id = sc.nextLine().trim();
        ProductModel p = catalogService.findById(id);

        if (p == null) {
            System.out.println("No products found with id: " + id);
            return;
        }

        System.out.println("ID    | SKU    | Name                 | Category     | Price   | Qty");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-6s |%-6s | %-20s | %-11s | %7.2f | %4d%n",
                p.getId(),
                p.getSku(),
                p.getName(),
                p.getCategory(),
                p.getPrice(),
                p.getAvailableQuantity());
    }

    public void handleFindProductBySku(Scanner sc) throws Exception {
        String sku = sc.nextLine().trim();
        ProductModel p = catalogService.findBySku(sku);

        if (p == null) {
            System.out.println("No products found with id: " + sku);
            return;
        }

        System.out.println("ID    | SKU    | Name                 | Category     | Price   | Qty");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-6s |%-6s | %-20s | %-11s | %7.2f | %4d%n",
                p.getId(),
                p.getSku(),
                p.getName(),
                p.getCategory(),
                p.getPrice(),
                p.getAvailableQuantity());

    }

    public void handleUpdateProductBySku(Scanner sc) throws Exception {

        System.out.print("Enter sku to update: ");
        String sku = sc.nextLine().trim();

        ProductModel p = catalogService.findBySku(sku);
        if(p == null){
            System.out.println("No products found with sku: " + sku);
            return;
        }

        System.out.print("\n ");
        System.out.print("Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Category: ");
        String category = sc.nextLine().trim();

        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine().trim());

        System.out.print("Available Quantity: ");
        int qty = Integer.parseInt(sc.nextLine().trim());


        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setAvailableQuantity(qty);

        catalogService.updateProduct(p);
        System.out.println("Product with sku: " + sku + " updated successfully");
    }

    public void handleProductDeletionBySku(Scanner sc) throws Exception {
        String sku = sc.nextLine().trim();
        ProductModel p = catalogService.findBySku(sku);
        if (p == null) {
            System.out.println("No products found with sku: " + sku);
            return;
        }
        catalogService.removeProductBySku(sku);
        System.out.println("Product with SKU: " + sku + " has been deleted.");
    }

    public void handleProductDeletionById(Scanner sc) throws Exception {
        String id = sc.nextLine().trim();
        ProductModel p = catalogService.findById(id);
        if (p == null) {
            System.out.println("No products found with id: " + id);
            return;
        }

        catalogService.removeProductById(id);
        System.out.println("Product with ID: " + id + " has been deleted.");
    }

    public void handleAvailableStock(Scanner sc) throws Exception {
        System.out.println("Enter SKU");
        String sku = sc.nextLine().trim();
        System.out.println("Enter Quantity");
        int quantity = Integer.parseInt(sc.nextLine().trim());

        boolean res = catalogService.isStockPresent(sku, quantity);
        System.out.println("Stock present: " + res);
    }

}
