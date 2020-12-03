package com.example.munroapi.repositories;

import java.util.List;

import com.example.munroapi.models.Munro;

import org.springframework.stereotype.Repository;

@Repository
public interface MunroRepository {
    List<Munro> findByCategory(List<Munro> munros, String category, Boolean desc);

    List<Munro> sortBy(List<Munro> munros, String sortBy);
    
}
