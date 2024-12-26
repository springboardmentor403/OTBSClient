package com.otbs.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.otbs.model.Outlet;
@Controller // This annotation tells Spring that this class is a web controller.
public class OutletClientController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.api.outlet.base.url}")    
    private String outletBaseUrl;
    
    @GetMapping("/add-outlet")
    public String showOutletPage(Model model) { // Added `Model` to the method signature.
        model.addAttribute("outlet", new Outlet()); // Preparing a new Outlet object for the view.
        return "add-outlet"; // View name
    }

    @PostMapping("/add-outlet")
    public String addOutlet(Outlet outlet, Model model) {
        restTemplate.postForObject(outletBaseUrl + "/add", outlet, Outlet.class);
        model.addAttribute("message", "Outlet added successfully!"); // Feedback for the user
        return "addOutletSuccess"; // View name
    }
        
	    

 	   
 	   @GetMapping("/view-all-outlets")
 	    public String viewAllOutlets(Model model) {
 	        // Add logic to fetch outlets if needed
 		// Make the GET request using RestTemplate to fetch the outlets
 	        Outlet[] outletsArray = restTemplate.getForObject(outletBaseUrl + "/all", Outlet[].class);
 	        
 	        // Convert the array to a List for easier handling in the view
 	        List<Outlet> outlets = Arrays.asList(outletsArray);

 	        // Add the outlets to the model to be accessible in the view
 	        model.addAttribute("outlets", outlets);
 	        return "viewAllOutlets";
 	    }

// 	    @GetMapping("/nearest")
// 	    public String findNearestOutlet(Model model) {
// 	    	 Outlet[] outletsArray = restTemplate.getForObject(outletBaseUrl + "/nearest{location}", Outlet[].class);
//  	        
//  	        // Convert the array to a List for easier handling in the view
//  	        List<Outlet> outlets = Arrays.asList(outletsArray);
//
//  	        // Add the outlets to the model to be accessible in the view
//  	        model.addAttribute("outlets", outlets);
// 	    	
// 	        return "findNearestOutlet";
// 	    }
 	   
 	  @GetMapping("/nearest")
 	 public String findNearestOutlet() {
 		 return "findNearestOutlet";
  	    }  
 	  
   
  
}

