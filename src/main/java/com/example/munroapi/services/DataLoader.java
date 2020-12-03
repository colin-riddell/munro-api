package com.example.munroapi.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.munroapi.models.Munro;

@Service
public class DataLoader {

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

    public static List<String> parseCSVFile(BufferedReader reader){
        // Read the CSV file into a BufferedReader
        if (reader == null){
            // BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream("munrotab_v6.2.csv"), "Cp1252"));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public static List<Munro> createMunrosFromData(List<String> listOfLines, LinkedList<Integer> columnIndexes){
        List<Munro> munros = new ArrayList<>();

        List<Integer> columnsCopy = columnIndexes.stream().map(s ->s).collect(Collectors.toList());

        for (String line : listOfLines) {
            ArrayList<String> csvElements = splitStringOnCharSurroundChar(line, ',', '"');
            System.out.println(csvElements.get(0));
            ArrayList<String> lineArgs = new ArrayList<>();
            for (int i = 0; i < csvElements.size(); i++) {
                if (columnsCopy.contains(i)){
                    columnsCopy.remove(Integer.valueOf(i));
                    lineArgs.add(csvElements.get(i));
                }
            }
            if (lineArgs.size() >= 3 && !lineArgs.get(0).equals("")){
                List<Munro> muns = Stream.of(lineArgs).map(Munro::new).collect(Collectors.toList());
                munros.addAll(muns);
                columnsCopy = columnIndexes.stream().map(s ->s).collect(Collectors.toList());
            }
            
        }
        return munros;
    }
}