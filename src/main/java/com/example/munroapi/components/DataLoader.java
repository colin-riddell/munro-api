package com.example.munroapi.components;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.munroapi.models.Munro;

@Component
public class DataLoader implements ApplicationRunner {

    public static ArrayList<String> splitStringOnCharSurroundChar(String inputString, char split, char surround)
    {
        ArrayList<String> csvValues = new ArrayList<String>();
        boolean notInSurround = true;
        int start = 0;

        // remove any trailing commas from the inputString
        int len = inputString.length()-1;
        if (inputString.charAt(len) == ','){
            inputString = inputString.substring(0, len);
        }

        for(int i=0; i< inputString.length()-1; i++) {
            // if not already in a surrounding char then grab the substring
            if(inputString.charAt(i) == split && notInSurround) {
                csvValues.add(inputString.substring(start, i));
                start = i + 1;                
            }   
            else if(inputString.charAt(i) == surround)
                notInSurround =! notInSurround;
        }
        csvValues.add(inputString.substring(start));
        return csvValues;
    }   

    public static List<String> parseCSVFile(){
        // Read the CSV file into a BufferedReader
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream("munrotab_v6.2.csv"), "Cp1252"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.readLine(); // be sure to consume the title line
        } catch (Exception ex){
            ex.printStackTrace(); //TODO: get rid when refacording into own method
        }

        // Stream the data from the reader into a List<Strings>
        // each element in listOfLines is a line from the CSV file
        Stream<String> lines = reader.lines();
        List<String> listOfLines = lines.map(s->s).collect(Collectors.toList());
        return listOfLines;        
    }

    public static List<Munro> createMunrosFromData(List<String> listOfLines, Set columnIndexes){
        ArrayList<String> lineArgs = new ArrayList<>();
        List<Munro> munros = new ArrayList<>();

        for (String line : listOfLines) {
            System.out.println(line);
            ArrayList<String> csvElements = splitStringOnCharSurroundChar(line, ',', '"');
            for (int i = 0; i < csvElements.size(); i++) {
                if (columnIndexes.contains(i)){ // TODO: Handle exceptions for indexes that are OOB when data is missing
                    lineArgs.add(csvElements.get(i));
                    if (lineArgs.size() == columnIndexes.size()){
                        List<Munro> muns = Stream.of(lineArgs).map(Munro::new).collect(Collectors.toList());
                        munros.addAll(muns);
                    }
                }
            }
        }

        return munros;
    }

    @Override
    public void run(ApplicationArguments args)  {

        List<String> listOfLines = parseCSVFile();

        Set columnIndexes = Set.of(5, 9, 13, 27);
        List<Munro> munros = createMunrosFromData(listOfLines, columnIndexes);


    }
}