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
import com.example.munroapi.services.MunroService;
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


/**
 * MunroController is responsible for providing HTTP endpoints into the API:
 * * /munros/upload - For POSTint CSV file data to populate the list of munros
 * * /munros        - For allowing users to GET data about the munros. Query string params can be passed to
 *                    this endpoint to customise and filter the results. The following queries are available:
 *         * category=MUN|TOP    - pass either MUN or TOP as values to category to filter by that category
 *         * sortBy=height|name  - pass either height or name to sortBy to sort by height in meters or name
 *         * desc=true|false     - pass either true|false to desc to sortBy descending or ascending (descending is default)
 *         * minHeight=_number_  - pass a decimal number to minHeight to get munros OVER that height
 *         * maxHeight=_number_  - pass a decimal number to maxHeight to get munros UNDER that height
 */
@RestController
public class MunroController {


    @Autowired
    DataLoader dataLoaderService;

    @Autowired
    MunroService munroService;

    /**
     *
     * @param file - POST a CSV file
     * @return ResponseEntity with either success or failure if the CSV is valid and has been loaded or invalid and
     * not loaded
     */
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
        this.munroService.setMunros(DataLoader.createMunrosFromData(listOfLines, columnIndexes));

        return new ResponseEntity<>(new ResponseMessage("File Recieved", HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    /*
     * * /munros        - For allowing users to GET data about the munros. Query string params can be passed to
     *                    this endpoint to customise and filter the results. The following queries are available:
     *         * category=MUN|TOP    - pass either MUN or TOP as values to category to filter by that category
     *         * sortBy=height|name  - pass either height or name to sortBy to sort by height in meters or name
     *         * desc=true|false     - pass either true|false to desc to sortBy descending or ascending (descending is default)
     *         * minHeight=_number_  - pass a decimal number to minHeight to get munros OVER that height
     *         * maxHeight=_number_  - pass a decimal number to maxHeight to get munros UNDER that height
     */
    @GetMapping(path="/munros")
    public ResponseEntity getAllMunros(
        @RequestParam(name = "category", required = false) String category,
        @RequestParam(name = "maxResults", required = false) Integer maxResults,
        @RequestParam(name = "sortBy", required = false) String sortBy, //height in meters or by name
        // Ascending order  is default so will only enable descending
        @RequestParam(name = "desc", required = false, defaultValue = "false") Boolean desc,       // sory by height/name decending
        @RequestParam(name = "maxHeight", required = false) Float maxHeight, // filter max height - heights above?
        @RequestParam(name = "minHeight", required = false) Float minHeight  // filter min height - heights below?

    ){
        if (munroService.isEmpty()){
            return new ResponseEntity<ResponseMessage>(
                new ResponseMessage("No data found. Please post a csv to /munros/upload as form-data under the key 'file'.",
                                    HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
        }

        List<Munro> munros = munroService.getMunros();
        // Create a copy of the munros so filters can be subsiqently applied to it for this response
        // without modifying the full list of munros
        List<Munro> allMunros = munros.stream().map(s ->s).collect(Collectors.toList());

        if (category != null){
            //find all munros by category
            allMunros = munroService.findByCategory(allMunros, category);
        }

        if (sortBy != null){
            allMunros = munroService.sortBy(allMunros, sortBy, desc);
        }

        if (maxResults != null){
            allMunros = munroService.limitTo(allMunros, maxResults);
        }

        if (minHeight !=null && maxHeight != null){
            if (maxHeight < minHeight){
                return new ResponseEntity<ResponseMessage>(
                new ResponseMessage("maxHeight can't be less than minHeight.",
                                    HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
            }
        }

        if (minHeight != null){
            allMunros = munroService.findByHeightMin(allMunros, minHeight);
        }

        if (maxHeight != null){
            allMunros = munroService.findByHeightMax(allMunros, maxHeight);
        }
        return new ResponseEntity<List<Munro>>(allMunros, HttpStatus.OK);
    }
}

