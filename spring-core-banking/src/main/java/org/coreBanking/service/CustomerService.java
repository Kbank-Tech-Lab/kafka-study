package org.coreBanking.service;

import java.util.UUID;
import org.coreBanking.model.Customer;

public interface CustomerService {

    Customer createCustomer(String name);
    Customer getCustomer(UUID id);
}
