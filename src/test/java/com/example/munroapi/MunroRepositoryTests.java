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

    private List<Munro> munros, munrosWithSameHeight;

    @Autowired
    MunroRepository munroRepository;

    @BeforeEach
    public void before(){
        
        Munro mun1 = new Munro("Big Hill", 1000.2f,"NN12345", "MUN");
        Munro mun2 = new Munro("Oor Hill", 500.2f,"NN12346", "TOP");
        Munro mun3 = new Munro("Your Hill", 1300.f,"NN12347", "MUN");
        Munro mun4 = new Munro("Everybuddys Hill", 500.4f,"NN12348", "TOP");
        Munro mun5 = new Munro("Little Hill", 980.3f,"NN12349", "MUN");


        Munro mun6 = new Munro("Little Hill", 980.3f,"NN12349", "MUN");
        Munro mun7 = new Munro("Other Hill", 980.3f,"NN12348", "MUN");
        Munro mun8 = new Munro("Tiny Hill", 980.3f,"NN12347", "MUN");
        Munro mun9 = new Munro("Massive Hill", 980.3f,"NN12346", "MUN");

        munros = List.of(mun1, mun2, mun3, mun4, mun5);
        munrosWithSameHeight = List.of(mun6, mun7, mun8, mun9);
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

    @Test
    public void  canSortByHeightThenName_AllSameHeight(){
        //Given we have munros in a list
        //And they are not sorted by height
        //And they all have the same height
        assertEquals("Little Hill", this.munrosWithSameHeight.get(0).getName());
        assertEquals("Other Hill", this.munrosWithSameHeight.get(1).getName());
        assertEquals("Tiny Hill", this.munrosWithSameHeight.get(2).getName());
        assertEquals("Massive Hill", this.munrosWithSameHeight.get(3).getName());

        // When we sort them by height - without specifying ascending or descending 
        List<Munro> found = munroRepository.sortBy(this.munrosWithSameHeight, "height", null);

        // Then they should be sorted by height
        // And they should be sorted in alphabetical order by name when the heights are the same
        assertEquals(4, found.size());
        assertEquals("Little Hill", found.get(0).getName());
        assertEquals("Massive Hill", found.get(1).getName());
        assertEquals("Other Hill", found.get(2).getName());
        assertEquals("Tiny Hill", found.get(3).getName());
    } 

    @Test
    public void  canSortByHeightThenName_FourSameHeight(){
        //Given we have munros in a list
        //And they are not sorted by height
        //And a few of the have the same height
        Munro mun10 = new Munro("Scotland Hill", 800f,"NN12349", "MUN");
        Munro mun11 = new Munro("England Hill", 1200.3f,"NN12348", "MUN");
        Munro mun12 = new Munro("Ireland Hill", 980.3f,"NN12347", "MUN");
        Munro mun13 = new Munro("Jersey Hill", 980.3f,"NN12346", "MUN");
        Munro mun14 = new Munro("London Hill", 980.3f,"NN12346", "MUN");
        Munro mun15 = new Munro("Zulu Hill", 980.3f,"NN12346", "MUN");

        List<Munro> lotsOfMunrosSomeSameHeight = List.of(mun10, mun11, mun12, mun13, mun14, mun15);

        // When we sort them by height - without specifying ascending or descending 
        List<Munro> found = munroRepository.sortBy(lotsOfMunrosSomeSameHeight, "height", null);

        // Then they should be sorted by height ascending
        assertEquals(6, found.size());
        assertEquals("Scotland Hill", found.get(0).getName());
        // And the heights are the same they should be sorted in alphabetical order by name

        assertEquals("Ireland Hill", found.get(1).getName());
        assertEquals("Jersey Hill", found.get(2).getName());
        assertEquals("London Hill", found.get(3).getName());
        assertEquals("Zulu Hill", found.get(4).getName());
        assertEquals("England Hill", found.get(5).getName());
    } 
}
