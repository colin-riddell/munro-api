package com.example.munroapi.repositories;

import java.util.List;

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


    // List<Munro> findByCategory(String category){

    // }

    // List<Munro> findByName(){

    // }

    // List<Munro> findByHeightMin(){

    // }

    // List<Munro> findByHeightMax(){

    // }
    
}
