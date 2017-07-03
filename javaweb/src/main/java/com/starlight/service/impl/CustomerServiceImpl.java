package com.starlight.service.impl;

import com.starlight.helper.DatabaseHelper;
import com.starlight.model.Customer;
import com.starlight.service.CustomerService;

import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/6/29 上午10:50.
 */
public class CustomerServiceImpl implements CustomerService {
    @Override
    public List<Customer> getCustomerList(String keywords) {
        String sql = "SELECT * FROM CUSTOMER ";
        return DatabaseHelper.queryEntityList(Customer.class,sql);
    }

    @Override
    public Customer getCustomer(Long id) {
        String sql = "SELECT * FROM CUSTOMER WHERE id = ?";
        return DatabaseHelper.queryEntity(Customer.class,sql,id);
    }

    @Override
    public boolean createCustomer(Map<String, Object> filedMap) {
        return DatabaseHelper.insertEntity(Customer.class,filedMap);
    }

    @Override
    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    @Override
    public boolean deleteCustomer(Long id) {
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }
}
