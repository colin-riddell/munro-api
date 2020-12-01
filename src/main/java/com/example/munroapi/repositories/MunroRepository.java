package com.example.munroapi.repositories;

import com.example.munroapi.models.Munro;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunroRepository extends ReactiveMongoRepository<Munro, String> {
    
}
