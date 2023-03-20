package com.tfgtourism.tourismtfg.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tfgtourism.tourismtfg.domains.PointOfInterest;

@Repository
public interface PointOfInterestRepository extends MongoRepository<PointOfInterest, String> {
}
