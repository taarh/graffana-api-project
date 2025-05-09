package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private String product;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
} 