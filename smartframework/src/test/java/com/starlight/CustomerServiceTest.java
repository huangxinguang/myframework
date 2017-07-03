package com.starlight;

import com.starlight.helper.DatabaseHelper;
import com.starlight.model.Customer;
import com.starlight.service.CustomerService;
import com.starlight.service.impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by huangxinguang on 2017/6/29 上午10:48.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    /**
     * 初始化变量
     */
    public CustomerServiceTest() {
        customerService = new CustomerServiceImpl();
    }

    /**
     * 初始化数据库
     */
    @Before
    public void init() {
        DatabaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    @Test
    public void testGetCustomerList() {
        List<Customer> customerList = customerService.getCustomerList("");
        Assert.assertEquals(2,customerList.size());
    }

    @Test
    public void testGetCustomer() {
        Long id = 1L;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void testCreateCustomer() {
        Map<String,Object> fieldMap = new HashMap<String,Object>();
        fieldMap.put("name","customer100");
        fieldMap.put("contact","jone");
        fieldMap.put("telephone","143231223434");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);

    }

    @Test
    public void testUpdaateCustomer() {
        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name","cusd223");
        boolean result = customerService.updateCustomer(1L,fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteCustomer() {
        boolean result = customerService.deleteCustomer(3L);
        Assert.assertTrue(result);
    }

}
