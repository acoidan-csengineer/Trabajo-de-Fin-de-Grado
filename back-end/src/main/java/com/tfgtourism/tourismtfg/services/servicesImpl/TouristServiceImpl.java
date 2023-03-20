package com.tfgtourism.tourismtfg.services.servicesImpl;

import java.util.List;
import java.util.Optional;

import com.tfgtourism.tourismtfg.domains.Tourist;
import com.tfgtourism.tourismtfg.repositories.TouristRepository;
import com.tfgtourism.tourismtfg.services.services.TouristService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TouristServiceImpl implements TouristService {
    @Autowired
    private TouristRepository repository;

    @Override
    public List<Tourist> findAll() {
        return this.repository.findAll();
    }
    
    @Override
    public void delete(Tourist resource) {
        this.repository.delete(resource);
    }

    @Override
    public Tourist save(Tourist resource) {
        return this.repository.save(resource);
    }

    @Override
    public Optional<Tourist> findById(String id) {
        return this.repository.findById(id);
    }
}