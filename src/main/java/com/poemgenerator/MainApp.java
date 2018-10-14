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

    private static void evaluateArguments(String[] args) {
        if( args!=null && args.length>0){
            workFile = args[0];
            System.out.println("Input argument workFile: " + workFile);
            if (args.length>1 && args[1].equalsIgnoreCase("true")){
                debugMode = true;
                System.out.println("Input argument debugMode: " + debugMode);
            }
        } else {
            workFile = DEFAULT_FILE;
        }
        
    }
    
    private static final String DEFAULT_FILE = "classpath:PruebaPoemGen";
    private static String workFile;
    private static boolean debugMode;
    
    public static void main(String[] args) {
        
        evaluateArguments(args);
        
        Map<String, Rule> allRules;
        List<String> poemLines;
        
        PoemGenerator poemGenerator = new PoemGenerator();
        
        allRules = poemGenerator.processFile(workFile);
        
        if (debugMode)
            poemGenerator.printLoadedRules(allRules);
        
        poemLines = poemGenerator.generatePoem(allRules);
        
        poemGenerator.printPoemLines(poemLines);
    }

    
}
