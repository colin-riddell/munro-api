package com.example.munroapi.models;

import java.util.ArrayList;

public class Munro {
    //Running No,
    // DoBIH Number,
    // Streetmap,
    // Geograph,
    // Hill-bagging,
    // Name,
    // SMC Section,
    // RHB Section,
    // _Section,
    // Height (m),
    // Height (ft)
    // ,Map 1:50,
    // Map 1:25,
    // Grid Ref,
    // GridRefXY,
    // xcoord,
    // ycoord,
    // 1891,
    // 1921,
    // 1933,
    // 1953,
    // 1969,
    // 1974,
    // 1981,
    // 1984,
    // 1990,
    // 1997,
    // Post 1997,
    // Comments

//    private int runningNumber;
//    private int DoBIHNumber;
//    private String streetmap;
//    private String geography;
//    private String hillBagging;
    private String name;
//    private int SMCSection;
//    private String RHBSection;
//    private String section;
    private Float height;
//    private int heightFeet;
//    private String map50;
//    private String map25;
    private String gridRef;
//    private String gridRefXY;
//    private int xCoord;
//    private int yCoord;
//    private String d1891;
//    private String d1921;
//    private String d1933;
//    private String d1953;
//    private String d1969;
//    private String d1974;
//    private String d1981;
//    private String d1984;
//    private String d1990;
//    private String d1997;
    private String category; // post1997
//    private String comments;


    public Munro(String name, float height, String gridRef, String category) {
        this.name = name;
        this.height = height;
        this.gridRef = gridRef;
        this.category = category;
    }

    public Munro(ArrayList<String> allArgs) {
        this.name = allArgs.get(0);
        try {
            this.height = Float.parseFloat(allArgs.get(1));
        } catch (NumberFormatException ex){
            if (ex.getMessage().equals("empty String")) {
                this.height = null;
            } else {
                ex.printStackTrace();
                throw ex;
            }
        }
        this.gridRef = allArgs.get(2);
        this.category = allArgs.get(3);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getGridRef() {
        return gridRef;
    }

    public void setGridRef(String gridRef) {
        this.gridRef = gridRef;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
