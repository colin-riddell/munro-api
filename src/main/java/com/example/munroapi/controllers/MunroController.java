package com.example.munroapi.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.munroapi.services.DataLoader;
import com.example.munroapi.models.Munro;
import com.example.munroapi.payloads.ResponseMessage;
import com.example.munroapi.repositories.MunroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class MunroController {

    private List<Munro> munros;

    @Autowired
    DataLoader dataLoaderService;

    @Autowired
    MunroRepository munroRepository;

    @PostMapping(path = "/munros/upload")
    public ResponseEntity<ResponseMessage> postCSV(@RequestParam("file") MultipartFile file){
        BufferedReader br;
        List<String> listOfLines = null;
        try {
            InputStream  is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is,"Cp1252"));
            listOfLines = DataLoader.parseCSVFile(br);
        } catch (IOException ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new ResponseMessage("Failed to load CSV data", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        }
        LinkedList<Integer> columnIndexes = new LinkedList<>(Arrays.asList(5, 9, 13, 27));
        this.munros = DataLoader.createMunrosFromData(listOfLines, columnIndexes);

        return new ResponseEntity<>(new ResponseMessage("File Recieved", HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping(path="/munros")
    public ResponseEntity getAllMunros(
        @RequestParam(name = "category", required = false) String category,
        @RequestParam(name = "maxResults", required = false) Integer maxResults,
        @RequestParam(name = "sortBy", required = false) String sortBy, //height in meters or by name
        // @RequestParam(name = "asc", required = false) Boolean asc,      // sort by height/name ascending
        // Ascending order  is default so will only enable descending
        @RequestParam(name = "desc", required = false, defaultValue = "false") Boolean desc,       // sory by height/name decending
        @RequestParam(name = "maxHeight", required = false) Float maxHeight, // filter max height - heights above?
        @RequestParam(name = "minHeight", required = false) Float minHeight  // filter min height - heights below?

    ){
        if (munros == null){
            return new ResponseEntity<ResponseMessage>(
                new ResponseMessage("No data found. Please post a csv to /munros/upload as form-data under the key 'file'.",
                                    HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
        }
        // Create a copy of the munros so filters can be subsiqently applied to it for this response
        // without modifying the full list of munros
        List<Munro> allMunros = this.munros.stream().map(s ->s).collect(Collectors.toList());

        if (category != null){
            //find all munros by category
            allMunros = munroRepository.findByCategory(allMunros, category);
        }

        if (sortBy != null){
            allMunros = munroRepository.sortBy(allMunros, sortBy, desc);
        }



        return new ResponseEntity<List<Munro>>(allMunros, HttpStatus.OK);
    }
    


}

