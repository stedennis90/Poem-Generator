/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator.model;

import java.util.List;

/**
 *
 * @author dennis
 */
public class Rule {
    
    private String name;
    private List<String[]> elements;

    public Rule(String name, List<String[]> elements) {
        this.name = name;
        this.elements = elements;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String[]> getElements() {
        return elements;
    }

    public void setElements(List<String[]> elements) {
        this.elements = elements;
    }
    
    
    
}
