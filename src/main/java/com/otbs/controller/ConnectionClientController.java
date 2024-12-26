package com.otbs.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.otbs.model.Connection;
import com.otbs.model.ConnectionLog;
import com.otbs.model.Customer;
import com.otbs.model.Outlet;


@Controller
public class ConnectionClientController {
	
	
//	@Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }


    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.api.connection.base.url}")
    private String connectionBaseUrl;

	
	@GetMapping("/connection-management")
	public String connectionmanagementPage() {
		return "connection-management";
	}
//	 @GetMapping("/connection/activate")
//	    public String getActivateConnectionPage() {
//	        return "activate";  // Thymeleaf template for activating a connection
//	    }
	 
//	 @PostMapping("/activation-success")
//	    public String activationsuccessPage() {
//	    	return "activation-success";
//	    }
	 
//	 @PostMapping("/activate-connection")
//	    public String activateConnection(@ModelAttribute Connection connection, Model model) {
//	        // Process the form data and activate the connection
//	        // Add any necessary attributes to the model
//	        model.addAttribute("connectionId", connection.getConnectionId());
//	        model.addAttribute("processingFee", connection.getProcessingFee());
//	        return "activation-success"; // returns the Thymeleaf template for success page
//	    }

//	 @PostMapping("/activate")
//	 public String activateConnection(@ModelAttribute Connection connection, Model model) {
//		 ResponseEntity<Connection> response=getRestTemplate().postForEntity("http://localhost:8091/activate", connection, Connection.class);
//		 Connection connectionObj=response.getBody();
//		 model.addAttribute(getActivateConnectionPage(), connectionObj);
//		 return "activation-success";
//	 }
	 
	 
	 
//	 @PostMapping("/activate")
//	 public String activateConnection(
//	         @RequestParam Integer customerId,
//	         @RequestParam String connectionType,
//	         @RequestParam String networkType,
//	         @RequestParam LocalDate activationDate,
//	         Model model) {
//	        String activateUrl = connectionBaseUrl + "/activate";
//
//	     // Create a request body with connection details
//	     Map<String, Object> requestBody = new HashMap<>();
//	     requestBody.put("customerId", customerId);
//	     requestBody.put("connectionType", connectionType);
//	     requestBody.put("networkType", networkType);
//	     requestBody.put("activationDate", activationDate);
//
//	     HttpHeaders headers = new HttpHeaders();
//	     headers.setContentType(MediaType.APPLICATION_JSON);
//
//	     HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//	     try {
//	         ResponseEntity<Connection> response = restTemplate.postForEntity(
//	             activateUrl,
//	             requestEntity,
//	             Connection.class
//	         );
//
//	         if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//	             model.addAttribute("connection", response.getBody());
//	             return "activation-success";
//	         } else {
//	             model.addAttribute("error", "Failed to activate connection");
//	             return "activate";
//	         }
//	     } catch (Exception e) {
//	         e.printStackTrace(); // Log the exception for debugging
//	         model.addAttribute("error", "An unexpected error occurred");
//	         return "activate";
//	     }
//	 }
	
	
	  @GetMapping("/add-connection")
	    public String showactivatePage(Model model) {
	        model.addAttribute("connection", new Connection());
	        return "add-connection";
	    }
//	  @PostMapping("/add-connection")
//	  public String registerCustomer(@ModelAttribute Connection connection, Model model) {
//	      // Set default values
//	      connection.setStatus("Active"); // Set status to active
//	      connection.setActivationdate(LocalDate.now()); // Set the current date for activation
//
//	      try {
//	          // Make a POST request to the backend
//	          restTemplate.postForObject(connectionBaseUrl + "/activate", connection, Connection.class);
//
//	          model.addAttribute("message", "Connection added successfully!");
//	          return "activation-success";
//	      } catch (HttpServerErrorException e) {
//	          // Handle server-side validation or other errors
//	          model.addAttribute("error", "Failed to activate connection: " + e.getResponseBodyAsString());
//	          return "add-connection"; // Redirect back to the form with an error message
//	      } catch (Exception e) {
//	          // Catch any other unexpected errors
//	          model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
//	          return "add-connection";
//	      }
//	  }

	  
//	  @PostMapping("/add-connection")
//	    public String registerCustomer(Connection connection, Model model) {
//	        restTemplate.postForObject(connectionBaseUrl + "/activate", connection, Connection.class);
//	        model.addAttribute("message", "Connection added successful!");
//	        return "activation-success";
//	    }
	 
	  @PostMapping("/add-connection")
	  public String registerCustomer(Connection connection, Model model) {
	      Connection response = restTemplate.postForObject(connectionBaseUrl + "/activate", connection, Connection.class);

	      if (response != null) {
	          model.addAttribute("message", "Connection added successfully!");
	          model.addAttribute("connection", response);
	          return "activation-success";
	      } else {
	          model.addAttribute("error", "Failed to activate connection");
	          return "add-connection";
	      }
	  }


	    
	    // Render the Upgrade Connection Page
	    @GetMapping("/upgrade-connection")
	    public String getUpgradeConnectionPage() {
	        return "upgrade-connection";  // Thymeleaf template for upgrading a connection
	    }
	    
	    @PostMapping("/upgrade-success")
	    public String upgradesuccessPage() {
	    	return "upgrade-success";
	    }
//	    @PostMapping("/upgrade-connection")
//	    public String upgradeConnection(@ModelAttribute Connection connection, Model model) {
//	        // Process the upgrade, e.g., change connection type or network type
//	        // Add attributes to display success message
//	        model.addAttribute("message", "Your connection upgraded successfully!");
//	        model.addAttribute("connectionId", connection.getConnectionId());
//	        model.addAttribute("newConnectionType", connection.getConnectionType());
//	        model.addAttribute("newNetworkType", connection.getNetworkType());
//	        return "upgrade-success"; // Render the success page after upgrading
//	    }
	    
//		  @PostMapping("/upgrade-connection")
//		  public String upgradeCustomer(Connection connection, Model model) {
//		      Connection response = restTemplate.postForObject(connectionBaseUrl + "/upgrade{connectionId}", connection, Connection.class);
//
//		      if (response != null) {
//		          model.addAttribute("message", "Connection upgraded successfully!");
//		          model.addAttribute("connection", response);
//		          return "upgrade-success";
//		      } else {
//		          model.addAttribute("error", "Failed to upgrade connection");
//		          return "upgrade-connection";
//		      }
//		  }
	    

	    // Render the Delete Connection Page
	    @GetMapping("/delete-connection")
	    public String getDeleteConnectionPage() {
	        return "delete-connection";  // Thymeleaf template for deleting a connection
	    }

//	    @PostMapping("/delete-success")
//	    public String deletesuccessPage() {
//	    	return "delete-success";
//	    }
	    
	    @PostMapping("/delete-connection")
	    public String deleteConnection(@RequestParam("connectionId") int connectionId, Model model) {
	        // Logic to delete the connection based on connectionId
	        // For now, assume the deletion was successful
	        model.addAttribute("message", "Connection deleted successfully!");
	        model.addAttribute("connectionId", connectionId);
	        return "delete-success"; // Render the success page after deletion
	    }
	   

//	    // Render the View All Connections Page
//	    @GetMapping("/view-all-connections")
//	    public String getAllConnections(Model model) {
//
//	        // Fetch all connections using service
//	      //  List<Connection> connections = connectionService.getAllConnections();
//	        model.addAttribute("connections", connections);
//
//	        return "view-all-connections";  // Thymeleaf template for displaying all connections
//	    }


    
	    @GetMapping("/view-all-connections")
	    public String getViewallConnectionsPage(Model model) {
	    	Connection[] connectionsArray = restTemplate.getForObject(connectionBaseUrl + "/all", Connection[].class);
 	        
 	        // Convert the array to a List for easier handling in the view
 	        List<Connection> connections = Arrays.asList(connectionsArray);

 	        // Add the outlets to the model to be accessible in the view
 	        model.addAttribute("connections", connections);	
	    	
	        return "view-all-connections";  // Thymeleaf template for viewing all connections
	    }
	    



//	    @GetMapping("/viewconnectionbyId")
//	    public String viewConnection(@RequestParam Integer connectionId, Model model) {
//	      
//	        // Call the backend API to fetch the connection details
//	    	Connection[] connectionsArray = restTemplate.getForObject(connectionBaseUrl + "/{connectionId}", Connection[].class);
//	    	
// 	        List<Connection> connections = Arrays.asList(connectionsArray);
//
//	        // Add the connection object to the model
//	        model.addAttribute("connections", connections);
//
//	        // Return the view page
//	        return "view-connection";
//	    }
	    // Render the View Connection by ID Page
	    @GetMapping("/viewconnectionbyId")
	    public String getViewConnectionPage() {
	        return "viewconnectionbyId";  // Thymeleaf template for viewing connection by ID
	    }



	    
}

