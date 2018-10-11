/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator.business;

import com.poemgenerator.model.Rule;
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

    private static final String BREAK_LINE = "\n";
    
    private static final String[] KEYWORDS = {"$END", "$LINEBREAK"};
    private static final String[] RULES_WITHOUT_VALUES = {"LINE", "POEM"};
    
    /**
     * Process an input file from path.
     * @param path FilePath.
     * @return Map with all rules read.
     */
    public Map<String, Rule> processFile(String path) {
        Map<String, Rule> rules = new HashMap();
        
        rules.put("POEM",new Rule("POEM", new String[]{}, new String[]{"LINE","LINE","LINE","LINE","LINE"}));
        rules.put("LINE",new Rule("LINE", new String[]{}, new String[]{"NOUN","PREPOSITION","PRONOUN"}));
        
        rules.put("ADJECTIVE",new Rule("ADJECTIVE", 
                                "black|white|dark|light|bright|murky|muddy|clear".split("\\|"),
                                "NOUN|ADJECTIVE|$END".split("\\|") ));
        
        rules.put("NOUN",new Rule("NOUN", 
                                "heart|sun|moon|thunder|fire|time|wind|sea|river|flavor|wave|willow|rain|tree|flower|field|meadow|pasture|harvest|water|father|mother|brother|sister".split("\\|"),
                                "VERB|PREPOSITION|$END".split("\\|") ));
        
        rules.put("PRONOUN",new Rule("PRONOUN", 
                                "my|your|his|her".split("\\|"),
                                "NOUN|ADJECTIVE".split("\\|") ));
        
        rules.put("VERB",new Rule("VERB", 
                                "runs|walks|stands|climbs|crawls|flows|flies|transcends|ascends|descends|sinks".split("\\|"),
                                "PREPOSITION|PRONOUN|$END".split("\\|") ));
        
        rules.put("PREPOSITION",new Rule("PREPOSITION", 
                                "above|across|against|along|among|around|before|behind|beneath|beside|between|beyond|during|inside|onto|outside|under|underneath|upon|with|without|through".split("\\|"),
                                "NOUN|PRONOUN|ADJECTIVE".split("\\|") ));
        return rules;
    }

    /**
     * Generate aleatory poem.
     * @param allRules All rules received.
     * @return List with poem lines.
     */
    public List<String> generatePoem(Map<String, Rule> allRules) {
        List<String> lines = new ArrayList();
        
        Rule poem = allRules.get("POEM");
        if (poem==null){
            throw new IllegalArgumentException("Poem rule do not exists");
        } else {
            for (String reference: poem.getReferences()){
                Rule line = allRules.get(reference);
                String resultLine = "";
                if (line!=null){
                    resultLine = evaluateRuleReference(allRules, line) + BREAK_LINE;
                    lines.add(resultLine);
                }
            }
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
        
        String[] words = currentRule.getWords();
        if (Arrays.binarySearch(RULES_WITHOUT_VALUES, currentRule.getName())<0){
            if (words==null || words.length<1){
                throw new IllegalArgumentException("Can't found words");
            } else {            
                wordIndex = ThreadLocalRandom.current().nextInt(0, words.length);
                evaluatedText += (words[wordIndex] + " ");
            }
        }
        
        String[] references = currentRule.getReferences();
        if (references==null || references.length<1){
            throw new IllegalArgumentException("Can't found references");
        } else {            
            int referenceIndex = ThreadLocalRandom.current().nextInt(0, references.length);
            //System.out.println("refIndex: " + referenceIndex + " from : " + Arrays.toString(references));
            String currentReference = references[referenceIndex];
            if (Arrays.binarySearch(KEYWORDS, currentReference)<0){
                Rule nextRule = allRules.get(currentReference);
                evaluatedText += evaluateRuleReference(allRules, nextRule);
            }            
        }        
        //System.out.println("evaluated: " + resultValue);
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
            System.out.println(line);
        });
        System.out.println("===============================");
    }

    
    
}
