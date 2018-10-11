/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator;

import com.poemgenerator.business.PoemGenerator;
import com.poemgenerator.model.Rule;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dennis
 */
public class MainApp {
    
    public static void main(String[] args) {
        String path = "/myFile.txt";
        Map<String, Rule> allRules;
        List<String> poemLines;
        
        PoemGenerator poemGenerator = new PoemGenerator();
        
        allRules = poemGenerator.processFile(path);
        poemLines = poemGenerator.generatePoem(allRules);
        poemGenerator.printPoemLines(poemLines);
    }

    
}
