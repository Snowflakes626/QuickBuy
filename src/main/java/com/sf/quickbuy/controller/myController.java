package com.sf.quickbuy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sf.quickbuy.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
public class myController {
    @Autowired
    BuyService buyService;

    @RequestMapping("/quickBuy/{item}/{person}")
    public String quickBuy(@PathVariable String item, @PathVariable String person) throws JsonProcessingException {
        if(buyService.canVisit(item, 5, 20)){
            String result = buyService.buy(item, person);
            if (!result.equals("0")) {
                return person + " success";
            } else {
                return person + " fail";
            }
        }
        else{
            return person + " fail";
        }
    }

    //jmeter进行压力测试
    @RequestMapping("/quickBuy/{item}")
    public String quickBuy1(@PathVariable String item, HttpServletRequest request) throws JsonProcessingException {
        String person = request.getParameter("person");
        if(buyService.canVisit(item, 5, 20)){
            String result = buyService.buy(item, person);
            if (!result.equals("0")) {
                return person + " success";
            } else {
                return person + " fail";
            }
        }
        else{
            return person + " fail";
        }
    }
}
