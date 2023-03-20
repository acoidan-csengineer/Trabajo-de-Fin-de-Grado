package com.tfgtourism.tourismtfg.services.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Pair;

// import java.util.Optional;

import com.tfgtourism.tourismtfg.domains.PointOfInterest;

public interface PointOfInterestService {
    List<PointOfInterest> findAll();
    
    PointOfInterest save(PointOfInterest resource);

    void delete(PointOfInterest resource);

    void deleteAll();
    
    Optional<PointOfInterest> findById(String id);    

    ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> getTimeBetweenPairsPOIS();

    Double getDistance(ArrayList<Double> pointOne, ArrayList<Double> pointTwo);
}