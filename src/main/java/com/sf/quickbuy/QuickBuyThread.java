package com.sf.quickbuy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class QuickBuyThread extends Thread{
    public void run(){
        //通过java代码模拟发出get post请求
        RestTemplate restTemplate = new RestTemplate();
        String user = Thread.currentThread().getName();
        ResponseEntity<String> entity = restTemplate.
                getForEntity("http://localhost:8080/quickBuy/computer/"+user , String.class);
        System.out.println(entity.getBody());
    }
}
