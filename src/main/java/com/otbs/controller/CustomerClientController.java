package com.otbs.controller;

import com.otbs.model.Customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class CustomerClientController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.api.customer.base.url}")
    private String customerBaseUrl;


    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(Customer customer, Model model) {
        restTemplate.postForObject(customerBaseUrl + "/register", customer, Customer.class);
        model.addAttribute("message", "Registration successful!");
        return "register";
    }
    
//    @GetMapping("/viewallcustomers")
//    public String viewAllCustomers(Model model) {
//        Customer[] customersArray = restTemplate.getForObject(customerBaseUrl + "/viewallcustomers", Customer[].class);
//        List<Customer> customers = Arrays.asList(customersArray);
//        model.addAttribute("customers", customers);
//        return "viewallcustomers";
//    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        String loginUrl = customerBaseUrl + "/login";

        // Create a request body with username and password
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                loginUrl,
                requestBody,
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/dashboard";
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "customerdashboard";
    }
}
