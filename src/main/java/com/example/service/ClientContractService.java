package com.example.service;

import com.example.entity.Client;
import com.example.entity.Contract;
import com.example.repository.ClientRepository;
import com.example.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.ArrayList;

@Service
public class ClientContractService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ContractRepository contractRepository;
    
    private final MeterRegistry meterRegistry;
    
    public ClientContractService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    
    @Transactional
    @Timed(value = "client.save.time", description = "Time taken to save client with contracts")
    public Client saveClientWithContracts(Client client) {
        if (client.getId() == null) {
            // New client
            if (client.getContracts() == null) {
                client.setContracts(new ArrayList<>());
            }
            
            Client savedClient = clientRepository.save(client);
            
            // Handle contracts
            if (client.getContracts() != null) {
                for (Contract contract : client.getContracts()) {
                    contract.setClient(savedClient);
                    contractRepository.save(contract);
                }
            }
            
            // Add counter for successful saves
            meterRegistry.counter("client.save.success").increment();
            
            return savedClient;
        } else {
            // Existing client
            Client existingClient = clientRepository.findById(client.getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
            
            // Update client details
            existingClient.setFirstName(client.getFirstName());
            existingClient.setLastName(client.getLastName());
            existingClient.setAge(client.getAge());
            existingClient.setAddress(client.getAddress());
            
            // Handle new contracts
            if (client.getContracts() != null) {
                for (Contract contract : client.getContracts()) {
                    if (contract.getId() == null) {
                        contract.setClient(existingClient);
                        contractRepository.save(contract);
                    }
                }
            }
            
            return clientRepository.save(existingClient);
        }
    }
} 