/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator.util;

/**
 *
 * @author dennis
 */
public class StringUtil {
    
    public final static boolean isReference(String item){
        return item.matches("^<\\w+>");
    }
    
    public final static boolean isKeyword(String item){
        return item.startsWith("$");
    }
    
    public static void main(String[] args) {
        System.out.println("result reference == " + isReference("<2TOP>"));
        System.out.println("result keyword   == " + isKeyword("$BREAKLINE"));
    }
}
