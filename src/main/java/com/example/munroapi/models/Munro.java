package com.example.munroapi.models;

import java.util.ArrayList;

public class Munro {
    private String name;
    private Float height;
    private String gridRef;

    private String category; // post1997


    public Munro(String name, float height, String gridRef, String category) {
        this.name = name;
        this.height = height;
        this.gridRef = gridRef;
        this.category = category;
    }

    public Munro(ArrayList<String> allArgs) {
        this.name = allArgs.get(0);

        // if the height is empty, be sure to just set it to null
        try {
            this.height = Float.parseFloat(allArgs.get(1));
        } catch (Exception ex){
            if (ex.getMessage().equals("empty String")) {
                this.height = null;
            } else {
                ex.printStackTrace();
                throw ex;
            }
        }

        this.gridRef = allArgs.get(2);

        // If the category is empty, be sure to set it to ""
        try {
            this.category = allArgs.get(3);
        } catch (IndexOutOfBoundsException ex){
            this.category = "";
        }
        
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
