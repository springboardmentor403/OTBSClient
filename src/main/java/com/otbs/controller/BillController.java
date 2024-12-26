package com.otbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.otbs.model.Bill;

import jakarta.servlet.http.HttpSession;


@Controller
public class BillController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.api.bill.base.url}")
    private String billBaseUrl;

    @GetMapping("/unpaidBills")
    public String unpaidBills(@RequestParam String customerId,Model model,HttpSession session) {
        ResponseEntity<List> response =restTemplate.getForEntity(billBaseUrl+"/customer"+"/"+customerId+"/unpaid", List.class);
        List<Bill> billList = response.getBody();
        model.addAttribute("unpaidBills",billList);
        session.setAttribute("customerId",customerId);
        session.setAttribute("unpaidBills", billList);
        return "unpaidbillView";
    }

    @GetMapping("/allBills")
    public String allBills(Model model,HttpSession session) {
        String customerId=(String) session.getAttribute("customerId");
        ResponseEntity<List> response =restTemplate.getForEntity(billBaseUrl+"/customer"+"/"+customerId, List.class);
        List allbillList = response.getBody();
        List<Bill> billList =(List<Bill>) session.getAttribute("unpaidBills");
        model.addAttribute("unpaidBills",billList);
        model.addAttribute("allBills",allbillList);
        return "unpaidbillView";
    }
    
    
}
