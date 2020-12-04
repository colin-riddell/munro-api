package com.example.munroapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.munroapi.models.Munro;
import com.example.munroapi.repositories.MunroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MunroRepositoryTests {

    private List<Munro> munros;

    @Autowired
    MunroRepository munroRepository;

    @BeforeEach
    public void before(){
        
        Munro mun1 = new Munro("Big Hill", 1000.2f,"NN12345", "MUN");
        Munro mun2 = new Munro("Oor Hill", 500.2f,"NN12346", "TOP");
        Munro mun3 = new Munro("Your Hill", 1300.f,"NN12347", "MUN");
        Munro mun4 = new Munro("Everybuddys Hill", 500.4f,"NN12348", "TOP");
        Munro mun5 = new Munro("Little Hill", 980.3f,"NN12349", "MUN");

        munros = List.of(mun1, mun2, mun3, mun4, mun5);
    }
    
    @Test
    public void  canFindByCategory(){
        // Given we have 2 hills in the TOP category
        // When we find all by category TOP
        List<Munro> found = munroRepository.findByCategory(this.munros, "TOP");

        // Then 2 hills should be returned
        assertEquals(2, found.size());
        //And first one should be Oor Hill
        assertEquals("Oor Hill", found.get(0).getName());
        //And second one should be everybuddys hill
        assertEquals("Everybuddys Hill", found.get(1).getName());
        
    }

    @Test
    public void canSortByHeight(){
        //Given we have munros in a list
        //And they are not sorted by height
        assertEquals("Big Hill", this.munros.get(0).getName());
        assertEquals("Oor Hill", this.munros.get(1).getName());
        assertEquals("Your Hill", this.munros.get(2).getName());
        assertEquals("Everybuddys Hill", this.munros.get(3).getName());
        assertEquals("Little Hill", this.munros.get(4).getName());

        // When we sort them by height - without specifying ascending or descending 
        List<Munro> found = munroRepository.sortBy(this.munros, "height", null);

        // Then they should be sorted by height in ascending:
        assertEquals(5, found.size());
        assertEquals("Oor Hill", found.get(0).getName());
        assertEquals("Everybuddys Hill", found.get(1).getName());
        assertEquals("Little Hill", found.get(2).getName());
        assertEquals("Big Hill", found.get(3).getName());
        assertEquals("Your Hill", found.get(4).getName());
    }


    @Test
    public void canSortByHeight_descending(){
        //Given we have munros in a list
        //And they are not sorted by height
        assertEquals("Big Hill", this.munros.get(0).getName());
        assertEquals("Oor Hill", this.munros.get(1).getName());
        assertEquals("Your Hill", this.munros.get(2).getName());
        assertEquals("Everybuddys Hill", this.munros.get(3).getName());
        assertEquals("Little Hill", this.munros.get(4).getName());

        // When we sort them by height descending
        List<Munro> found = munroRepository.sortBy(this.munros, "height", true);

        // Then they should be sorted by height in ascending:
        assertEquals(5, found.size());
        assertEquals("Your Hill", found.get(0).getName());
        assertEquals("Big Hill", found.get(1).getName());
        assertEquals("Little Hill", found.get(2).getName());
        assertEquals("Everybuddys Hill", found.get(3).getName());
        assertEquals("Oor Hill", found.get(4).getName());

    }

    @Test
    public void canSortByName(){
        //Given we have munros in a list
        //And they are not sorted by name
        assertEquals("Big Hill", this.munros.get(0).getName());
        assertEquals("Oor Hill", this.munros.get(1).getName());
        assertEquals("Your Hill", this.munros.get(2).getName());
        assertEquals("Everybuddys Hill", this.munros.get(3).getName());
        assertEquals("Little Hill", this.munros.get(4).getName());

        // When we sort them by name - without specifying ascending or descending 
        List<Munro> found = munroRepository.sortBy(this.munros, "name", null);

        // Then they should be sorted by height in ascending:
        assertEquals(5, found.size());
        assertEquals("Big Hill", found.get(0).getName());
        assertEquals("Everybuddys Hill", found.get(1).getName());
        assertEquals("Little Hill", found.get(2).getName());
        assertEquals("Oor Hill", found.get(3).getName());
        assertEquals("Your Hill", found.get(4).getName());
    }


    @Test
    public void canSortByName_descending(){
        //Given we have munros in a list
        //And they are not sorted by name
        assertEquals("Big Hill", this.munros.get(0).getName());
        assertEquals("Oor Hill", this.munros.get(1).getName());
        assertEquals("Your Hill", this.munros.get(2).getName());
        assertEquals("Everybuddys Hill", this.munros.get(3).getName());
        assertEquals("Little Hill", this.munros.get(4).getName());

        // When we sort them by name descending alphabetical (z-a) 
        List<Munro> found = munroRepository.sortBy(this.munros, "name", true);

        // Then they should be sorted by height in descending:
        assertEquals(5, found.size());
        assertEquals("Your Hill", found.get(0).getName());
        assertEquals("Oor Hill", found.get(1).getName());
        assertEquals("Little Hill", found.get(2).getName());
        assertEquals("Everybuddys Hill", found.get(3).getName());
        assertEquals("Big Hill", found.get(4).getName());

    }

    @Test
    public void  canLimitResults(){
        // Given we have 5 hills in the list
        // When we limit them to 2
        List<Munro> found = munroRepository.limitTo(this.munros, 2);

        // Then 2 hills should be returned
        assertEquals(2, found.size());
        assertEquals("Big Hill", found.get(0).getName());
        assertEquals("Oor Hill", found.get(1).getName());
        
    }

    @Test
    public void  canFindByHeightMin(){
        // Given we have 5 hills in the list
        //And they range from 500 -> 1300 meters in height
        // When we query by minHeight 1000
        List<Munro> found = munroRepository.findByHeightMin(this.munros, 1000f);

        // Then 2 hills should be returned - both should be OVER 1000 meters height
        assertEquals(2, found.size());
        assertEquals("Big Hill", found.get(0).getName());
        assertEquals("Your Hill", found.get(1).getName());
    }

    @Test
    public void  canFindByHeightMax(){
        // Given we have 5 hills in the list
        //And they range from 500 -> 1300 meters in height
        // When we query by maxHeight 1000
        List<Munro> found = munroRepository.findByHeightMax(this.munros, 1000f);

        // Then 3 hills should be returned - all should be UNDER 1000 meters height
        assertEquals(3, found.size());
        assertEquals("Oor Hill", found.get(0).getName());
        assertEquals("Everybuddys Hill", found.get(1).getName());
        assertEquals("Little Hill", found.get(2).getName());
        
    }    
}
