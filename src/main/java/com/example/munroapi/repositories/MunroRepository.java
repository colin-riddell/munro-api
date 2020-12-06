package com.example.munroapi.repositories;

import java.util.List;

import com.example.munroapi.models.Munro;
import org.springframework.stereotype.Repository;

@Repository
public interface MunroRepository {
    List<Munro> findByCategory(List<Munro> munros, String category);

    List<Munro> sortBy(List<Munro> munros, String sortBy, Boolean descBoolean);

    List<Munro> limitTo(List<Munro> munros, int limit);

    List<Munro> findByHeightMin(List<Munro> munros, float minHeight);

    List<Munro> findByHeightMax(List<Munro> munros, float maxHeight);
    
}
