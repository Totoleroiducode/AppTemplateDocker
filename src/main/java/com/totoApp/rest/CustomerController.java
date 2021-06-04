package com.totoApp.rest;

import com.totoApp.exceptions.ResourceNotFoundException;
import com.totoApp.model.Customer;
import com.totoApp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {

        try {
            Customer nCustomer = new Customer();
            nCustomer.setFirstName(customer.getFirstName());
            nCustomer.setLastName(customer.getLastName());

            customerRepository.save(nCustomer);

            return ResponseEntity.status(HttpStatus.CREATED).body(nCustomer);

        } catch (Exception e) {
            LOGGER.error("Something went wrong during the Customer's saving", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @GetMapping("/retrieve/")
    public Customer findCustomerById(@RequestParam(name = "id") long id) {
        Customer customerFound = null;


        Optional<Customer> optCustomer = customerRepository.findById(id);

        if (optCustomer.isPresent()) {
            customerFound = optCustomer.get();
        }
        return customerFound;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = customerRepository.findAll();

            return ResponseEntity.status(HttpStatus.CREATED).body(customerList);

        } catch (Exception e) {
            LOGGER.error("Something went wrong during the Customers retrieving", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found " + id));
        customerRepository.delete(customer);
        return ResponseEntity.ok().build();

    }


}
