package com.ecommerce.repo.impl.csv;

import com.ecommerce.exceptions.CartItemAlreadyPresentException;
import com.ecommerce.exceptions.CartItemNotFoundException;
import com.ecommerce.exceptions.CartNotFoundException;
import com.ecommerce.exceptions.ProductNotFoundInCartException;
import com.ecommerce.models.CartItemModel;
import com.ecommerce.repo.CartItemRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CartItemCsvRepositoryImpl implements CartItemRepository {

    private final Path csvpath;
    private final String HEADER = "cartItemId,cartId,sku,quantity,unitPrice";

    public CartItemCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void save(CartItemModel p) throws IOException, CartItemAlreadyPresentException {
        List<CartItemModel> list = findAll();

        for (CartItemModel x :list){
            if(x.getSku().equalsIgnoreCase(p.getSku())){
                throw new CartItemAlreadyPresentException("CartItem with sku " + p.getSku() + " already exists");
            }
        }

        list.add(p);
        ensureHeaderInFile();
        writeAll(list);
    }

    @Override
    public CartItemModel findById(String id) throws IOException {
        List<CartItemModel> list = findAll();
        for (CartItemModel x :list){
            if(x.getId().equalsIgnoreCase(id)){
                return x;
            }
        }
        return null;
    }

    @Override
    public List<CartItemModel> findAllByCartId(String cartId) throws IOException, CartNotFoundException {
        List<CartItemModel> list = findAll();
        List<CartItemModel> res = new ArrayList<>();
        for (CartItemModel x :list){
            if(x.getCartId().equalsIgnoreCase(cartId)){
                res.add(x);
            }
        }
        return res;
    }

    @Override
    public CartItemModel findByCartIdAndSku(String cartId, String sku) throws IOException, CartNotFoundException, ProductNotFoundInCartException {

        List<CartItemModel> list = findAll();
        boolean cartExists = false;

        for (int i = 0; i < list.size(); i++) {
            CartItemModel item = list.get(i);

            // First check if this item belongs to the cart
            if (item.getCartId().equalsIgnoreCase(cartId)) {
                cartExists = true;

                // Now check if SKU matches
                if (item.getSku().equalsIgnoreCase(sku)) {
                    return item; // perfect match
                }
            }
        }

        // After full scan...
        if (!cartExists) {
            throw new CartNotFoundException("Cart with id " + cartId + " not found");
        }

        // Cart exists, but SKU does not
        throw new ProductNotFoundInCartException(
                "Product with sku " + sku + " not found in Cart " + cartId
        );
    }

    @Override
    public void updateQuantityBySku(String sku, int quantity) throws IOException, ProductNotFoundInCartException {
        List<CartItemModel> list = findAll();
        boolean isFound = false;
        for (int i = 0; i < list.size(); i++) {
            CartItemModel x = list.get(i);
            if(x.getSku().equalsIgnoreCase(sku)){
                isFound = true;
                x.setQuantity(quantity);
                list.set(i, x);
            }
        }

        if(!isFound){
            throw new ProductNotFoundInCartException("Product with sku " + sku + " not found in cart for update");
        }

        writeAll(list);
    }

    @Override
    public void deleteBySku(String sku) throws IOException, ProductNotFoundInCartException {
        List<CartItemModel> list = findAll();
        boolean isRemoved = false;
        for (CartItemModel x :list){
            if(x.getSku().equalsIgnoreCase(sku)){
                isRemoved = true;
                list.remove(x);
            }
        }
        if(!isRemoved){
            throw new ProductNotFoundInCartException("Product with sku " + sku + " not found in cart for removal");
        }
        writeAll(list);
    }

    @Override
    public void deleteById(String id) throws IOException, CartItemNotFoundException {
        List<CartItemModel> list = findAll();
        boolean isRemoved = false;
        for (CartItemModel x :list){
            if(x.getId().equalsIgnoreCase(id)){
                isRemoved = true;
                list.remove(x);
            }
        }
        if(!isRemoved){
            throw new CartItemNotFoundException("CartItem with id " + id + " not found in cart for removal");
        }
        writeAll(list);
    }

    @Override
    public List<CartItemModel> findAll() throws IOException {
        List<CartItemModel> result = new ArrayList<>();

        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine(); // skips header

        while ((line = br.readLine() )!= null) {
            if(!line.trim().isEmpty()){
                CartItemModel p = parseIntoCartItem(line);
                result.add(p);
            }
        }

        return result;
    }

    private void writeAll(List<CartItemModel> list) throws IOException {

        ensureHeaderInFile();
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        bw.write(HEADER);
        bw.newLine();
       // "cartItemId,cartId,sku,quantity,unitPrice"
        for (CartItemModel p :list){
            String line = p.getId() + ","    +
                    p.getCartId() + ","   +
                    p.getSku() + ","  +
                    p.getQuantity() + "," +
                    p.getUnitPrice() ;

            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    private CartItemModel parseIntoCartItem(String line) {

        String[] t = line.split(",");
        //"cartItemId,cartId,sku,quantity,unitPrice"
        CartItemModel p = new CartItemModel();
        p.setId(t[0]);
        p.setCartId(t[1]);
        p.setSku(t[2]);
        p.setQuantity(Integer.parseInt(t[3]));
        p.setUnitPrice(Double.parseDouble(t[4]));

        return p;
    }

    private void ensureHeaderInFile() throws IOException {
        if(Files.notExists(csvpath)){
            Files.createDirectories(csvpath.getParent());
            BufferedWriter bw = Files.newBufferedWriter(csvpath);
            bw.write(HEADER);
            bw.newLine();
            bw.close();
        }
    }
}
