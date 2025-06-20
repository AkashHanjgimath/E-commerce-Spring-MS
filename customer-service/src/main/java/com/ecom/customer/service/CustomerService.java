package com.ecom.customer.service;

import com.ecom.customer.dto.CustomerRequest;
import com.ecom.customer.dto.CustomerResponse;
import com.ecom.customer.exception.CustomerNotFoundException;
import com.ecom.customer.model.Customer;
import com.ecom.customer.repository.CustomerRepository;
import com.ecom.customer.util.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(mapper.toCustomer(request));
        return customer.getId();

    }


    public void update(CustomerRequest request) {
        var customer = this.customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
                ));
        mergeCustomer(customer, request);
        customerRepository.save(customer);

    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {

        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
       return customerRepository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .toList();
    }

    public CustomerResponse findById(String id)
    {
        return customerRepository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(()->new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
    }

    public boolean existById(String id)
    {
        return customerRepository.findById(id).isPresent();
    }

    public void deleteCustomer(String id)
    {
        customerRepository.deleteById(id);
    }
}
