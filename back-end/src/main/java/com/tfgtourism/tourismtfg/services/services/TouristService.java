package com.tfgtourism.tourismtfg.services.services;

import java.util.List;
import java.util.Optional;

// import java.util.Optional;

import com.tfgtourism.tourismtfg.domains.Tourist;

public interface TouristService {
    List<Tourist> findAll();
    
    Tourist save(Tourist resource);

    void delete(Tourist resource);
    
    Optional<Tourist> findById(String id);    
}