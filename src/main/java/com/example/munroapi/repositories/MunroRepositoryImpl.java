package com.example.munroapi.repositories;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.munroapi.models.Munro;

import org.springframework.stereotype.Repository;

/**
 * Though not backed by a DB or using JPA, this class implements methods through the repository pattern
 * that allow the user of this to run queries against the munros dataset.
 */

@Repository
public class MunroRepositoryImpl implements MunroRepository {


    /**
     * Given a list of munros and a catagory
     * Return all the munros who are in that category
     */
    public List<Munro> findByCategory(List<Munro> munros, String category){
        List<Munro> retData = munros.stream().filter(mun -> mun.getCategory().equals(category)).collect(Collectors.toList());
        return retData;
    }

    /**
     * @param List of munros
     * @param String to sort by name | height
     * @param Boolean to sort descending
     * 
     * Given a list of munros, this returns a sorted list of munros by the sortBy
     * If desc is true, the list returned will be sorted descending by either name or height
     * If more than one munro have the same name, the munros with the same name will be returned sorted by height
     * If more than one munro has the same height, these will be sorted by the name
     */
    public List<Munro> sortBy(List<Munro> munros, String sortBy, Boolean desc){
        List<Munro> retData = null;
        if (sortBy.equals("name")){
            retData = munros.stream().sorted(
                Comparator.comparing(Munro::getName)
                    .thenComparing(Munro::getHeight) // when names are same sort by height
                    ).collect(Collectors.toList());
            if (desc != null && desc == true){
                retData = munros.stream().sorted(
                    Comparator.comparing(Munro::getName)
                        .thenComparing(Munro::getHeight)
                        .reversed()).collect(Collectors.toList());
            }
        }
        if (sortBy.equals("height")){
            retData = munros.stream().sorted(
                Comparator.comparingDouble(Munro::getHeight)
                    .thenComparing(Munro::getName)  // when heights are the same sort by name
                ).collect(Collectors.toList());
            if (desc != null && desc == true){
                retData = munros.stream().sorted(
                    Comparator.comparingDouble(Munro::getHeight)
                        .thenComparing(Munro::getName)
                        .reversed()).collect(Collectors.toList());
            }
        }
        return retData; 
    }

    /**
     * @param List of munros
     * @param number of results to limit to
     * Given a list of munros, return a smaller list same size as the limit parameter
     */
    public List<Munro> limitTo(List<Munro> munros, int limit){
        List<Munro> retData = null;
        retData = munros.stream().limit(limit).collect(Collectors.toList());
        return retData;
    }

    /**
     * @param List of munros
     * @param float minHeight - minimum height to return from the list
     * Return a new list of munros that are all higher in meters than the minHeight provided
     */
    public List<Munro> findByHeightMin(List<Munro> munros, float minHeight){
        List<Munro> retData = null;

        retData = munros.stream().filter(mun -> mun.getHeight() >= minHeight).collect(Collectors.toList());
        return retData;
    }

    /**
     * @param List of munros
     * @param float maxHeight - maximum height to return from the list
     * Return a new list of munros that are all smaller in meters than the maxHeight provided
     */
    public List<Munro> findByHeightMax(List<Munro> munros, float maxHeight){
        List<Munro> retData = null;

        retData = munros.stream().filter(mun -> mun.getHeight() <= maxHeight).collect(Collectors.toList());
        return retData;
    }

    
}
