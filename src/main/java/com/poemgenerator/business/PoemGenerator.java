/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator.business;

import com.poemgenerator.exceptions.InvalidRuleDefinition;
import com.poemgenerator.model.Rule;
import com.poemgenerator.util.FileUtil;
import com.poemgenerator.util.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author dennis
 */
public class PoemGenerator {
    
    private static final String KEYWORDS_LINE_BREAK = "$LINEBREAK";
    
    /**
     * Process an input file from path.
     * @param path FilePath.
     * @return Map with all rules read.
     */
    public Map<String, Rule> processFile(String path) {
        Map<String, Rule> rules = new HashMap();
        List<String> lines;
        
        lines = FileUtil.readLines(path);
        
        
        lines.stream().forEach((line) -> {
            System.out.println(line);
            try {
                Rule rule = extractRule(line);
                rules.put(rule.getName(), rule);
            } catch (InvalidRuleDefinition e){
                System.out.println("Invalid rule: " + e.getMessage());
            }
        });
        
        return rules;
    }

    /**
     * Generate aleatory poem.
     * @param allRules All rules received.
     * @return List with poem lines.
     */
    public List<String> generatePoem(Map<String, Rule> allRules) {
        List<String> lines = new ArrayList();
        
        Rule poem = allRules.get("<POEM>");
        if (poem==null){
            throw new IllegalArgumentException("Poem rule do not exists");
        } else {
            lines.add(evaluateRuleReference(allRules, poem));
        }
        
        return lines;        
    }

    /**
     * Evaluate rule references.
     * @param allRules All rules received.
     * @param currentRule Current rule for evaluate.
     * @return Generated text after rule has been evaluated.
     */
    private String evaluateRuleReference(Map<String, Rule> allRules, Rule currentRule) {
        int wordIndex = 0;
        String evaluatedText = "";
        //System.out.println("Cur. rule: " + currentRule.getName());
        
        for(String[] elements: currentRule.getElements()){
            wordIndex = ThreadLocalRandom.current().nextInt(0, elements.length);
            String word = elements[wordIndex];
            
            if( StringUtil.isKeyword(word) ){
                if (KEYWORDS_LINE_BREAK.equals(word)){
                    evaluatedText += "\n";
                }                
            } else if( StringUtil.isReference(word) ){
                Rule nextRule = allRules.get(word);
                evaluatedText += evaluateRuleReference(allRules, nextRule);
            } else {
                evaluatedText += (word + " ");
            }
        }
        
        return evaluatedText;
    }

    /**
     * Print poem lines on screen.
     * @param poemLines Lines from poem.
     */
    public void printPoemLines(List<String> poemLines) {
        System.out.println("\nPrinting generated poem ... ");
        System.out.println("===============================\n");
        poemLines.stream().forEach((line) -> {
            System.out.print(line);
        });
        System.out.println("===============================");
    }

    /**
     * Extract rule from a line.
     * @param line Line text.
     * @return Generated rule.
     */
    private Rule extractRule(String line) {
        String ruleName = "";
        List<String[]> elements = new ArrayList<>();
        if (line== null || line.isEmpty()){
            throw new InvalidRuleDefinition("Line is null or empty");
        } else {
            String ruleParts[] = line.trim().split(" ");
            if (ruleParts!=null){
                ruleName = ruleParts[0].trim().replace(":", "");
                if (ruleParts.length>=2){
                    for(int i=1; i <ruleParts.length; i++){
                        String[] possibleElements = ruleParts[i].trim().split("\\|");
                        elements.add(possibleElements);                            
                    }
                } else {
                    throw new InvalidRuleDefinition(String.format("[%s], has only (%d) parts  ", ruleName, ruleParts.length));
                }
            } else {
                throw new InvalidRuleDefinition("Reading: Empty line parts");
            }
        }
        
        return new Rule(String.format("<%s>", ruleName), elements);
    }

    /**
     * Print all rules.
     * @param allRules Map for rules.
     */
    public void printLoadedRules(Map<String, Rule> allRules) {
        System.out.println("\nPrinting loaded Rules ... ");
        System.out.println("===============================\n");
        allRules.values().stream().forEach((rule) -> {
            System.out.println("\n");
            System.out.println(rule.getName());
            for(String[] elements: rule.getElements()){
                System.out.println(Arrays.toString(elements));
            }
        });
        System.out.println("===============================");
    }

    
    
}
