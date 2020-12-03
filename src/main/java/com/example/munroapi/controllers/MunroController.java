package com.example.munroapi.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.example.munroapi.services.DataLoader;
import com.example.munroapi.models.Munro;
import com.example.munroapi.payloads.ResponseMessage;
import com.example.munroapi.repositories.MunroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        }
        LinkedList<Integer> columnIndexes = new LinkedList<>(Arrays.asList(5, 9, 13, 27));
        this.munros = DataLoader.createMunrosFromData(listOfLines, columnIndexes);

        return new ResponseEntity<>(new ResponseMessage("File Recieved", HttpStatus.OK.value()), HttpStatus.OK);
    }


    


}

