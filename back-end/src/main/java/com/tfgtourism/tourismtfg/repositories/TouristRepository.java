package com.tfgtourism.tourismtfg.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tfgtourism.tourismtfg.domains.Tourist;

@Repository
public interface TouristRepository extends MongoRepository<Tourist, String> {
}
