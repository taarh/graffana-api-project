package com.example.controller;

import com.example.entity.Client;
import com.example.service.ClientContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClientContractController {
    
    @Autowired
    private ClientContractService clientContractService;
    
    @PostMapping("/clients")
    public ResponseEntity<Client> createClientWithContracts(@RequestBody Client client) {
        Client savedClient = clientContractService.saveClientWithContracts(client);
        return ResponseEntity.ok(savedClient);
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("TEST");
    }
} 