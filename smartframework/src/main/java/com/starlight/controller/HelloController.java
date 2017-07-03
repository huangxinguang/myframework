package com.starlight.controller;

import com.starlight.annotation.Action;
import com.starlight.annotation.Controller;
import com.starlight.annotation.Inject;
import com.starlight.bean.Param;
import com.starlight.bean.View;
import com.starlight.model.Customer;
import com.starlight.service.CustomerService;
import com.starlight.service.impl.CustomerServiceImpl;

import javax.servlet.http.HttpServlet;
import java.util.List;

/**
 * Created by huangxinguang on 2017/6/30 下午5:46.
 */
@Controller
public class HelloController {

    @Inject
    private CustomerServiceImpl customerService;

    @Action("get:/customer")
    public View customer(Param param) {
        List<Customer> customerList = customerService.getCustomerList("");
        return new View("customer.jsp").addModel("customerList",customerList);
    }
}
