package com.totoApp.rest;

import com.totoApp.model.Customer;
import com.totoApp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Customer> duplicateAcquisition(@RequestBody Customer  customer) {

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
}
