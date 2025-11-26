package com.ecommerce.repo.impl.csv;

import com.ecommerce.exceptions.CustomerAlreadyExistException;
import com.ecommerce.exceptions.CustomerNotFoundException;
import com.ecommerce.models.CustomerModel;
import com.ecommerce.repo.CustomerRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomerCsvRepoImpl implements CustomerRepository {

    private final Path csvpath;
    private final String HEADER = "id,name,phone,email,address";

    public CustomerCsvRepoImpl(Path csvpath) {
        this.csvpath = csvpath;
    }


    @Override
    public void addCustomer(CustomerModel c) throws IOException, CustomerAlreadyExistException {
        List<CustomerModel> list = findAll();

        for (CustomerModel x :list){
            if(x.getEmail().equalsIgnoreCase(c.getEmail())){
                throw new CustomerAlreadyExistException("Customer with email " + c.getEmail() + " already exists");
            }
        }

        list.add(c);
        ensureHeaderInFile();
        writeAll(list);
    }

    @Override
    public CustomerModel getCustomerById(String id) throws IOException{
        List<CustomerModel> list = findAll();
        for (CustomerModel x :list){
            if(x.getId().equalsIgnoreCase(id)){
                return x;
            }
        }
        return null;
    }

    @Override
    public CustomerModel getCustomerByEmail(String email) throws IOException{
        List<CustomerModel> list = findAll();
        for (CustomerModel x :list){
            if(x.getEmail().equals(email)){
                return x;
            }
        }
        return null;
    }

    @Override
    public void removeCustomerById(String id) throws IOException,CustomerNotFoundException {
        List<CustomerModel> list = findAll();
        boolean isRemoved = false;
        for (CustomerModel x :list){
            if(x.getId().equalsIgnoreCase(id)){
                isRemoved = true;
                list.remove(x);
            }
        }
        if(!isRemoved){
            throw new CustomerNotFoundException("Customer with id " + id + " not found for removal");
        }
        writeAll(list);
    }

    @Override
    public void updateCustomer(CustomerModel c) throws IOException,CustomerNotFoundException{
        List<CustomerModel> list = findAll();
        boolean isFound = false;
        for (CustomerModel x :list){
            if(x.getCustomerID().equalsIgnoreCase(c.getCustomerID())){
                isFound = true;
            }
        }

        if(!isFound){
            throw new CustomerNotFoundException("Customer with id " + c.getCustomerID() + " not found for update");
        }

        writeAll(list);
    }

    @Override
    public List<CustomerModel> findAll() throws IOException{
        List<CustomerModel> result = new ArrayList<>();

        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine(); // skips header

        while ((line = br.readLine() )!= null) {
            if(!line.trim().isEmpty()){
                CustomerModel p = parseIntoCustomer(line);
                result.add(p);
            }
        }

        return result;
    }

    private void writeAll(List<CustomerModel> list) throws IOException {

        ensureHeaderInFile();
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        bw.write(HEADER);
        bw.newLine();

        for (CustomerModel p :list){
            String line = p.getId() + ","    +
                    p.getName() + ","   +
                    p.getPhone() + ","  +
                    p.getEmail() + "," +
                    p.getAddress() ;

            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    private CustomerModel parseIntoCustomer(String line) {

        String[] t = line.split(",");

        CustomerModel p = new CustomerModel();
        p.setId(t[0]);
        p.setName(t[1]);
        p.setPhone(t[2]);
        p.setEmail(t[3]);
        p.setAddress(t[4]);

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
