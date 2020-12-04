package com.example.munroapi.repositories;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.munroapi.models.Munro;

import org.springframework.stereotype.Repository;

@Repository
public class MunroRepositoryImpl implements MunroRepository {

    // Filtering of search by hill category (i.e. Munro, Munro Top or either). If this information is
    // not provided by the user it should default to either. This should use the “post 1997”
    // column and if it is blank the hill should be always excluded from results.
    // ● The ability to sort the results by height in meters and alphabetically by name. For both
    // options it should be possibly to specify if this should be done in ascending or descending
    // order.
    // ● The ability to limit the total number of results returned, e.g. only show the top 10
    // ● The ability to specify a minimum height in meters
    // ● The ability to specify a maximum height in meters
    // ● Queries may include any combination of the above features and none are mandatory.
    // ● Suitable error responses for invalid queries (e.g. when the max height is less than the
    // minimum height)


    public List<Munro> findByCategory(List<Munro> munros, String category){
        List<Munro> retData = munros.stream().filter(mun -> mun.getCategory().equals(category)).collect(Collectors.toList());
        return retData;
    }

    public List<Munro> sortBy(List<Munro> munros, String sortBy, Boolean desc){
        List<Munro> retData = null;
        if (sortBy.equals("name")){
            retData = munros.stream().sorted(Comparator.comparing(Munro::getName)).collect(Collectors.toList());
            if (desc != null && desc == true){
                retData = munros.stream().sorted(Comparator.comparing(Munro::getName).reversed()).collect(Collectors.toList());
            }
        }
        if (sortBy.equals("height")){
            retData = munros.stream().sorted(Comparator.comparingDouble(Munro::getHeight)).collect(Collectors.toList());
            if (desc != null && desc == true){
                retData = munros.stream().sorted(Comparator.comparingDouble(Munro::getHeight).reversed()).collect(Collectors.toList());
            }
        }
        return retData; 
    }

    // List<Munro> findByName(){

    // }

    // List<Munro> findByHeightMin(){

    // }

    // List<Munro> findByHeightMax(){

    // }
    
}
