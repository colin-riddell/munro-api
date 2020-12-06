package com.example.munroapi.services;

import java.util.List;

import com.example.munroapi.models.Munro;
import com.example.munroapi.repositories.MunroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunroService {
    @Autowired
    private MunroRepository munroRepository;

    private List<Munro> munros;

    public boolean isEmpty(){
        if (this.munros == null){
            return true;
        }
        return false;
    }

    public List<Munro> getMunros(){
        return this.munros;
    }

    public void setMunros(List<Munro> munros){
        this.munros = munros;
    }
    
    public List<Munro> findByCategory(List<Munro> munros, String category){
        return munroRepository.findByCategory(munros, category);
    }

    public List<Munro> sortBy(List<Munro> munros, String sortBy, Boolean descBoolean){
        return munroRepository.sortBy(munros, sortBy, descBoolean);
    }

    public List<Munro> limitTo(List<Munro> munros, int limit){
        return munroRepository.limitTo(munros, limit);
    }

    public List<Munro> findByHeightMin(List<Munro> munros, float minHeight){
        return munroRepository.findByHeightMin(munros, minHeight);
    }

    public List<Munro> findByHeightMax(List<Munro> munros, float maxHeight){
        return munroRepository.findByHeightMax(munros, maxHeight);
    }
    
}
