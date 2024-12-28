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
        session.setAttribute("customerId",customerId);
        try{ 
            ResponseEntity<List> response =restTemplate.getForEntity(billBaseUrl+"/customer"+"/"+customerId+"/unpaid", List.class);

            List<Bill> billList = response.getBody();
            model.addAttribute("unpaidBills",billList);
            session.setAttribute("unpaidBills", billList);
            session.setAttribute("message", null );

        }
        catch(Exception e){
            model.addAttribute("message"," customer has no Bending bill ");
            session.setAttribute("message", " customer has no Bending bill " );  
        }

        return "unpaidbillView";    
    }

    @GetMapping("/allBills")
    public String allBills(Model model,HttpSession session) {
        String customerId=(String) session.getAttribute("customerId");
        ResponseEntity<List> response =restTemplate.getForEntity(billBaseUrl+"/customer"+"/"+customerId, List.class);
        List allbillList = response.getBody();
        model.addAttribute("allBills",allbillList);

        //Getting message 
        String message = (String) session.getAttribute("message");
        if(message == null){
        //Getting Bills
            List<Bill> billList =(List<Bill>) session.getAttribute("unpaidBills");
            model.addAttribute("unpaidBills",billList);
        } 
        else model.addAttribute("message",message);
        
        return "unpaidbillView";
    }

    @GetMapping("/paybill")
    public String payBill(@RequestParam String billId, Model model, HttpSession session) {
        // Call the backend API to process the payment
        ResponseEntity<String> response = restTemplate.getForEntity(billBaseUrl + "/customer/" + billId + "/pay", String.class);
        
        // Optional: Check the response and add attributes to the model
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("paymessage", "Payment successful for Bill ID: " + billId);
        } else {
            model.addAttribute("paymessage", "Payment failed for Bill ID: " + billId);
        }
    
        // Retrieve the updated list of unpaid bills
        String customerId = (String) session.getAttribute("customerId");
        try{ 
            ResponseEntity<List> responsebill =restTemplate.getForEntity(billBaseUrl+"/customer"+"/"+customerId+"/unpaid", List.class);

            List<Bill> billList = responsebill.getBody();
            model.addAttribute("unpaidBills",billList);
            session.setAttribute("unpaidBills", billList);

        }
        catch(Exception e){
            model.addAttribute("message"," customer has no Bending bill ");
            session.setAttribute("unpaidBills",null);
            session.setAttribute("message", " customer has no Bending bill " );  
        }
    
        // Return the same view or redirect as needed
        return "unpaidbillView";
    }
 
}