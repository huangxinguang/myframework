package com.starlight.service;

import com.starlight.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/6/29 上午10:39.
 */
public interface CustomerService {
    /**
     * 获取客户列表
     * @param keywords
     * @return
     */
    List<Customer> getCustomerList(String keywords);

    /**
     * 获取客户
     * @param id
     * @return
     */
    Customer getCustomer(Long id);

    /**
     * 创建客户
     * @param filedMap
     * @return
     */
    boolean createCustomer(Map<String,Object> filedMap);

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    boolean updateCustomer(Long id,Map<String,Object> fieldMap);

    /**
     * 删除客户
     * @param id
     * @return
     */
    boolean deleteCustomer(Long id);
}
