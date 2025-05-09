package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Contract> contracts;
} 