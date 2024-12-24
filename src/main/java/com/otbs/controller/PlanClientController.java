package com.otbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.otbs.model.Plan;

@Controller
@RequestMapping("/plan")
public class PlanClientController {
	
	 @Autowired
	    private RestTemplate restTemplate;

	    @Value("${backend.api.plan.based.url}")
	    private String planBasedUrl;	
	
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/manage")
	public String planManagement() {
		return "managePlan";
	}
	
	
	@GetMapping("/viewAllPlans")
    public String listPlans(Model model) {
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(planBasedUrl + "/getAll", List.class);
            List plans = response.getBody();
            model.addAttribute("listOfPlans", plans);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch plans: " + e.getMessage());
        }
        return "viewAllPlans";
    }
	
	
	
//	@GetMapping("/viewAllPlans")
//	public String listPlans(Model model) {
//	    try {
//	        ResponseEntity<List> response = restTemplate.getForEntity(planBasedUrl + "/getAll", List.class);
//	        List plans = response.getBody();
////	        System.out.println("Fetched Plans: " + plans); 	// Debug log
//	        model.addAttribute("listOfPlans", plans);
//	    } catch (Exception e) {
//	        model.addAttribute("error", "Failed to fetch plans: " + e.getMessage());
//	        e.printStackTrace(); 	// Debug log
//	    }
//	    return "viewAllPlans";
//	}

	
	
	@GetMapping("/searchId")  		// instaded of this i am using the managePlan page.
	public String getId() {
		return "searchId";
	}
	
	
	@GetMapping("/searchById")
    public String searchPlanById(@RequestParam("planId") int planId, Model model) {
        try {
            ResponseEntity<Plan> response = restTemplate.getForEntity(planBasedUrl + "/getId/" + planId, Plan.class);
            model.addAttribute("plan", response.getBody());
        } catch (Exception e) {
            model.addAttribute("error", "Plan not found: " + e.getMessage());
        }
        return "searchResultPlan";
    }
	
	
//	@GetMapping("/searchById")
//	public String searchPlanById(@RequestParam("planId") int planId, Model model) {
//	    try {
//	        String url = planBasedUrl + "/getId/" + planId;
//	        Plan plan = restTemplate.getForObject(url, Plan.class);  	// Fetching a single plan
//	        model.addAttribute("plan", plan);
//	    } catch (Exception e) {
//	        model.addAttribute("error", "Plan not found: " + e.getMessage());
//	    }
//	    return "searchResultPlan";       //  this Thymeleaf template will terurn
//	}
	
	
	@GetMapping("/searchByName")
	public String searchPlanByName(@RequestParam("planName") int planName, Model model) {
	    try {
	        String url = planBasedUrl + "/getName/" + planName;
	        Plan plan = restTemplate.getForObject(url, Plan.class);  	
	        model.addAttribute("plan", plan);
	    } catch (Exception e) {
	        model.addAttribute("error", "Plan not found: " + e.getMessage());
	    }
	    return "searchResultPlan";     
	}
	
	
	
//	@PostMapping("/addPlan")
//	public String addNewPlan(@ModelAttribute Plan plan, Model model) {
//	    try {
//	        String url = planBasedUrl + "/add";
//	        restTemplate.postForObject(url, plan, Plan.class);
//	        model.addAttribute("message", "Plan added successfully!");
//	    } catch (Exception e) {
//	        model.addAttribute("error", "Failed to add plan: " + e.getMessage());
//	    }
//	    return "addPlan";
//	}
	
	
	
	@PostMapping("/addPlan")
    public String addNewPlan(@ModelAttribute Plan plan, Model model) {
        try {
            ResponseEntity<Plan> response = restTemplate.postForEntity(planBasedUrl + "/add", plan, Plan.class);
            model.addAttribute("message", "Plan added successfully! Plan ID: " + response.getBody().getPlanId());
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add plan: " + e.getMessage());
        }
        return "addPlan";
    }
	
	
	@PostMapping("/updatePlan")
    public String updatePlan(@RequestParam int planId, @ModelAttribute Plan plan, Model model) {
        try {
            String url = planBasedUrl + "/updateById/" + planId;
            ResponseEntity<Plan> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(plan), Plan.class);
            model.addAttribute("message", "Plan updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update plan: " + e.getMessage());
        }
        return "updatePlan";
    }
	
	
	
	@PostMapping("/deletePlan")
    public String deletePlan(@RequestParam int planId, Model model) {
        try {
            String url = planBasedUrl + "/deleteById/" + planId;
            restTemplate.delete(url);
            model.addAttribute("message", "Plan deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete plan: " + e.getMessage());
        }
        return "deletePlan";
    }
	
	
}
