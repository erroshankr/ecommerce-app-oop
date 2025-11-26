package com.ecommerce.repo.impl.csv;

import com.ecommerce.exceptions.ProductAlreadyPresentException;
import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.models.ProductModel;
import com.ecommerce.repo.ProductRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductCsvRepositoryImpl implements ProductRepository {

    private final Path csvpath;
    private final String HEADER = "id,sku,name,category,price,availableQuantity";

    public ProductCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void addProduct(ProductModel p) throws IOException,ProductAlreadyPresentException {
          List<ProductModel> list = findAll();

          for (ProductModel x :list){
              if(x.getSku().equalsIgnoreCase(p.getSku())){
                  throw new ProductAlreadyPresentException("Product with sku " + p.getSku() + " already exists");
              }
          }

          list.add(p);
          ensureHeaderInFile();
          writeAll(list);
    }

    @Override
    public ProductModel getProductBySku(String sku) throws IOException{
        List<ProductModel> list = findAll();
        for (ProductModel x :list){
            if(x.getSku().equalsIgnoreCase(sku)){
                return x;
            }
        }
        return null;
    }

    @Override
    public ProductModel getProductById(String id) throws IOException{
        List<ProductModel> list = findAll();
        for (ProductModel x :list){
            if(x.getId().equalsIgnoreCase(id)){
                return x;
            }
        }
        return null;
    }

    @Override
    public List<ProductModel> findAll() throws IOException{
       List<ProductModel> result = new ArrayList<>();

       BufferedReader br = Files.newBufferedReader(csvpath);
       String line = br.readLine(); // skips header

       while ((line = br.readLine() )!= null) {
           if(!line.trim().isEmpty()){
               ProductModel p = parseIntoProduct(line);
               result.add(p);
           }
       }

       return result;
    }


    @Override
    public void removeProductBySku(String sku) throws IOException,ProductNotFoundException{
        List<ProductModel> list = findAll();
        boolean isRemoved = false;
        for (ProductModel x :list){
            if(x.getSku().equalsIgnoreCase(sku)){
                isRemoved = true;
                list.remove(x);
            }
        }
        if(!isRemoved){
            throw new ProductNotFoundException("Product with sku " + sku + " not found for removal");
        }
        writeAll(list);
    }

    @Override
    public void removeProductById(String id) throws IOException,ProductNotFoundException{
        List<ProductModel> list = findAll();
        boolean isRemoved = false;
        for (ProductModel x :list){
            if(x.getId().equalsIgnoreCase(id)){
                isRemoved = true;
                list.remove(x);
            }
        }
        if(!isRemoved){
            throw new ProductNotFoundException("Product with id " + id + " not found for removal");
        }
        writeAll(list);
    }

    @Override
    public void updateProduct(ProductModel p) throws IOException,ProductNotFoundException{
        List<ProductModel> list = findAll();
        boolean isFound = false;
        for (ProductModel x :list){
            if(x.getSku().equalsIgnoreCase(p.getSku())){
               isFound = true;
            }
        }

        if(!isFound){
            throw new ProductNotFoundException("Product with sku " + p.getSku() + " not found for update");
        }

        writeAll(list);
    }


    private void writeAll(List<ProductModel> list) throws IOException {

        ensureHeaderInFile();
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        bw.write(HEADER);
        bw.newLine();

        for (ProductModel p :list){
            String line = p.getId() + ","    +
                    p.getSku() + ","   +
                    p.getName() + ","  +
                    p.getCategory() + "," +
                    p.getPrice() + "," +
                    p.getAvailableQuantity();

            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    private ProductModel parseIntoProduct(String line) {
        // 2,P002,Cotton T-Shirt,Apparel,8.50,150
        String[] t = line.split(",");

        ProductModel p = new ProductModel();
        p.setId(t[0]);
        p.setSku(t[1]);
        p.setName(t[2]);
        p.setCategory(t[3]);
        p.setPrice(Double.parseDouble(t[4]));
        p.setAvailableQuantity(Integer.parseInt(t[5]));

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
