package com.ecommerce.repo.impl.csv;

import com.ecommerce.exceptions.CartAlreadyPresentException;
import com.ecommerce.exceptions.CartNotFoundException;
import com.ecommerce.models.CartModel;
import com.ecommerce.repo.CartRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CartCsvRepositoryImpl implements CartRepository {

    private final Path csvpath;
    private final String HEADER = "cartId,customerId,subtotal,tax";

    public CartCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void save(CartModel cart) throws IOException, CartAlreadyPresentException {
        List<CartModel> list = findAll();

        for (CartModel x :list){
            if(x.getId().equalsIgnoreCase(cart.getId())){
                throw new CartAlreadyPresentException("Cart with id " + cart.getId() + " already exists");
            }
            if(x.getCustomerID().equalsIgnoreCase(cart.getCustomerId())){
                throw new CartAlreadyPresentException("Cart for customer " + cart.getCustomerID() + " already exists");
            }
        }

        list.add(cart);
        ensureHeaderInFile();
        writeAll(list);
    }

    @Override
    public CartModel findByCustomerId(String customerId) throws IOException {
        List<CartModel> list = findAll();
        for (CartModel x :list){
            if(x.getCustomerID().equalsIgnoreCase(customerId)){
                return x;
            }
        }
        return null;
    }

    @Override
    public void deleteByCustomerId(String customerId) throws IOException,CartNotFoundException {
        List<CartModel> list = findAll();
        boolean isRemoved = false;
        for (CartModel x :list){
            if(x.getCustomerID().equalsIgnoreCase(customerId)){
                isRemoved = true;
                list.remove(x);
            }
        }
        if(!isRemoved){
            throw new CartNotFoundException("Cart for customer " + customerId + " not found for removal");
        }
        writeAll(list);
    }

    @Override
    public List<CartModel> findAll() throws IOException {
        List<CartModel> result = new ArrayList<>();

        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine(); // skips header

        while ((line = br.readLine() )!= null) {
            if(!line.trim().isEmpty()){
                CartModel p = parseIntoCart(line);
                result.add(p);
            }
        }

        return result;
    }

    private void writeAll(List<CartModel> list) throws IOException {

        ensureHeaderInFile();
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        bw.write(HEADER);
        bw.newLine();

        for (CartModel p :list){
            String line = p.getId() + ","    +
                    p.getCustomerID() + ","   +
                    p.getSubtotal() + ","  +
                    p.getTax() ;

            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    private CartModel parseIntoCart(String line) {
        // "cartId,customerId,subtotal,tax"
        String[] t = line.split(",");

        CartModel p = new CartModel();
        p.setId(t[0]);
        p.setCustomerId(t[1]);
        p.setSubtotal(Double.parseDouble(t[2]));
        p.setTax(Double.parseDouble(t[3]));

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
