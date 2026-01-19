package com.java.capstone1.repository;

import com.java.capstone1.model.Deal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DealRepository extends MongoRepository<Deal, String> {
}
