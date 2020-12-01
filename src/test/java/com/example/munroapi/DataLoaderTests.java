package com.example.munroapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.munroapi.components.DataLoader;

import org.junit.jupiter.api.Test;

public class DataLoaderTests {

    @Test
    public void itShouldSplitLine_basic(){
        //Given we have a CSV Line denoting 4 columns
        String csvLine = "hello,there,some,data";

        //When we split it
        List<String> out = DataLoader.splitStringOnCharSurroundChar(csvLine, ',', '"');

        //Then the list should have 4 elements
        assertEquals(4, out.size());
        //And the lists 0th element should be "hello"
        assertEquals("hello", out.get(0));
        //And the lists 1st element should be "there"
        assertEquals("there", out.get(1));
        //And the lists 2nd element should be "some"
        assertEquals("some", out.get(2));
        //And the lists 3rd element should be "data"
        assertEquals("data", out.get(3));
    }

    @Test
    public void itShouldSplitLine_surround(){
        //Given we have a CSV line w/ 4 columns
        String csvLine = "hello,\"there\",some,data";
        // And one of the columns data is in quotation marks " "
        //When we split it
        List<String> out = DataLoader.splitStringOnCharSurroundChar(csvLine, ',', '"');

        //Then the list should have 4 elements
        assertEquals(4, out.size());
        //And the lists 0th element should be "hello"
        assertEquals("hello", out.get(0));
        //And the lists 1st element should be "there"
        assertEquals("\"there\"", out.get(1));
        //And the lists 2nd element should be "some"
        assertEquals("some", out.get(2));
        //And the lists 3rd element should be "data"
        assertEquals("data", out.get(3));
    }

    @Test
    public void itShouldSplitLine_surround_comma(){
        //Given we have a CSV line w/ 4 columns
        // And one of the columns data is in quotation marks " "
        // And that column has a comma in it
        String csvLine = "hello,\"there,there\",some,data";

        //When we split it
        List<String> out = DataLoader.splitStringOnCharSurroundChar(csvLine, ',', '"');

        //Then the list should have 4 elements
        assertEquals(4, out.size());
        //And the lists 0th element should be "hello"
        assertEquals("hello", out.get(0));
        //And the lists 1st element should be "there"
        assertEquals("\"there,there\"", out.get(1));
        //And the lists 2nd element should be "some"
        assertEquals("some", out.get(2));
        //And the lists 3rd element should be "data"
        assertEquals("data", out.get(3));
    }


    @Test
    public void itShouldSplitLine_5_columns_trailing(){
        //Given we have a CSV line w/ 5 columns
        //And the last column is empty - making it a "trailing" comma
        String csvLine = "hello,there,some,data,";

        //When we split it
        List<String> out = DataLoader.splitStringOnCharSurroundChar(csvLine, ',', '"');

        //Then the list should have 4 elements (last column is empty)
        assertEquals(4, out.size());
        //And the lists 0th element should be "hello"
        assertEquals("hello", out.get(0));
        //And the lists 1st element should be "there"
        assertEquals("there", out.get(1));
        //And the lists 2nd element should be "some"
        assertEquals("some", out.get(2));
        //And the lists 3rd element should be "data"
        assertEquals("data", out.get(3));
    }

    
    @Test
    public void itShouldSplitLine_empty_columns(){
        //Given we have a CSV line w/ 4 columns
        //And the the columns are empty
        String csvLine = ",,,,";

        //When we split it
        List<String> out = DataLoader.splitStringOnCharSurroundChar(csvLine, ',', '"');

        //Then the list should have 0 elements (last column is empty)
        assertEquals(3, out.size()); //TODO check this is what we want!

        //And they should be empty strings - TODO

    }

}
