package com.otbs.model;

import java.util.Date;

import jakarta.persistence.Entity;


@Entity
public class Complaint {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int complaintId;

    // @ManyToOne
    // @JoinColumn(name = "connectionId")
    // @JsonIgnoreProperties("complaintsRaised")
    private Connection connection;

    // @ManyToOne
    // @JoinColumn(name = "executiveId") 
    // @JsonIgnoreProperties("complaintsAssigned")
    // private HelpDeskExecutive assignedTo;

    private String complaintType;
    private String description;
    private String status;
    private String priority;
    private Date createdDate;
    private Date resolvedDate;
}