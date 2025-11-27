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

        System.out.print("ID: ");
        String id = scanner.nextLine().trim();

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
        p.setId(id);
        p.setSku(sku);
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setAvailableQuantity(qty);

        boolean res = catalogService.addProduct(p);

        if(res) {
            System.out.println("Product added.");
        }
        else {
            System.out.println("Product not added.");
        }
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
        System.out.print("Enter product ID: ");
        String id = sc.nextLine().trim();
        ProductModel p = catalogService.findById(id);

        if (p == null) {
            System.out.println("No products found with id: " + id);
            return;
        }

        System.out.println();
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
        System.out.print("Enter product SKU: ");
        String sku = sc.nextLine().trim();
        ProductModel p = catalogService.findBySku(sku);

        if (p == null) {
            System.out.println("No products found with sky: " + sku);
            return;
        }

        System.out.println();
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

        System.out.println();
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

        boolean res = catalogService.updateProduct(p);
        if(res) {
            System.out.println("Product with sku: " + sku + " updated successfully");
        }
        else {
            System.out.println("Product not updated.");
        }
    }

    public void handleProductDeletionBySku(Scanner sc) throws Exception {
        System.out.print("Enter sku to delete: ");
        String sku = sc.nextLine().trim();

        ProductModel p = catalogService.findBySku(sku);
        if (p == null) {
            System.out.println("No products found with sku: " + sku);
            return;
        }
        boolean res = catalogService.removeProductBySku(sku);
        if(res) {
            System.out.println("Product with SKU: " + sku + " has been deleted.");
        }else{
            System.out.println("Product not deleted.");
        }
    }

    public void handleProductDeletionById(Scanner sc) throws Exception {
        System.out.print("Enter ID to remove: ");
        String id = sc.nextLine().trim();

        ProductModel p = catalogService.findById(id);
        if (p == null) {
            System.out.println("No products found with id: " + id);
            return;
        }

        boolean res = catalogService.removeProductById(id);
        if(res) {
            System.out.println("Product with ID: " + id + " has been deleted.");
        }else{
            System.out.println("Product not deleted.");
        }
    }

    public void handleAvailableStock(Scanner sc) throws Exception {
        System.out.print("Enter SKU: ");
        String sku = sc.nextLine().trim();

        System.out.print("Enter Quantity: ");
        int quantity = Integer.parseInt(sc.nextLine().trim());

        boolean res = catalogService.isStockPresent(sku, quantity);
        if(res) {
            System.out.println("Stock is available of sku: " + sku + " for quantity: " + quantity);
        }else{
            System.out.println("Stock is not available of sku: " + sku + " for quantity: " + quantity);
        }
    }

}
