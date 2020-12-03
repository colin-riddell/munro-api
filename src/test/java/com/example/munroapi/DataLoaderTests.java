package com.example.munroapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.example.munroapi.models.Munro;
import com.example.munroapi.services.DataLoader;

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

    @Test
    public void itShoudCreateMunrosFromData_allFields(){
        // Given we have an array of lines with one string element
        // And the line has all the required fields
        String line = "1,1,\"http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y\",http://www.geograph.org.uk/gridref/NN7732430857,http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1,Ben Chonzie,1,01A,1.1,931,3054,51 52,OL47W 368W 379W,NN773308,NN7732430857,277324,730857,MUN,MUN,MUN,MUN,MUN,MUN,MUN,MUN,MUN,MUN,MUN,";
        List<String> lines = List.of(line);

        //When we create munros from that array of strings
        LinkedList<Integer> columnIndexes = new LinkedList<>(Arrays.asList(5, 9, 13, 27));
        List<Munro> munros = DataLoader.createMunrosFromData(lines, columnIndexes);
        // Then we will get back an array of munro objects
        // And it should have 1 element in it
        assertEquals(1, munros.size());
        // And the element should have:
        // category: MUN
        // gridRef: NN773308
        // height: 931.00f
        // name: Ben Chonzie
        assertEquals(munros.get(0).getCategory(), "MUN");
        assertEquals(munros.get(0).getGridRef(), "NN773308");
        assertEquals(munros.get(0).getHeight(), 931.00f);
        assertEquals(munros.get(0).getName(), "Ben Chonzie");
    }

    @Test
    public void itShoudCreateMunrosFromData_post1997_missing(){
        // Given we have an array of lines with one string element
        // And the line is missing the  post1997 data

        String line = "8,39,\"http://www.streetmap.co.uk/newmap.srf?x=244847&y=722468&z=3&sv=244847,722468&st=4&tl=~&bi=~&lu=N&ar=y\",http://www.geograph.org.uk/gridref/NN4484722468,http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=39,Stob Binnein - Creag a' Bhragit,1,01C,1.3,923,3028,51,OL46N 365N,NN448224,NN4484722468,244847,722468,TOP,,,,,,,,,,,";
        List<String> lines = List.of(line);

        // When we create munros from that array of strings
        //When we create munros from that array of strings
        // List<Integer> columnIndexes = new ArrayList<Integer>({5, 9, 13, 27);
        LinkedList<Integer> columnIndexes = new LinkedList<>(Arrays.asList(5, 9, 13, 27));

        List<Munro> munros = DataLoader.createMunrosFromData(lines, columnIndexes);

        // Then we will get back an array of munros
        // And it should have 1 element in it - a Munro object
        assertEquals(1, munros.size());

        //And the munro object should have:
        // category: "" -- as it's not there
        // gridRef: NN448224
        // height: 923.0f
        // name: "Stob Binnein - Creag a' Bhragit"
        assertEquals("", munros.get(0).getCategory());
        assertEquals("NN448224", munros.get(0).getGridRef());
        assertEquals(923.0f, munros.get(0).getHeight());
        assertEquals("Stob Binnein - Creag a' Bhragit", munros.get(0).getName());



    }

}
