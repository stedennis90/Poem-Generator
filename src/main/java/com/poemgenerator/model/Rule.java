/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator.model;

/**
 *
 * @author dennis
 */
public class Rule {
    
    private String name;
    private String[] words;
    private String[] references;

    public Rule(String name, String[] words, String[] references) {
        this.name = name;
        this.words = words;
        this.references = references;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    public String[] getReferences() {
        return references;
    }

    public void setReferences(String[] references) {
        this.references = references;
    }
    
    
}
