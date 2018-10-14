/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator.exceptions;

/**
 *
 * @author dennis
 */
public class InvalidRuleDefinition extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>InvalidRuleDefinition</code> without
     * detail message.
     */
    public InvalidRuleDefinition() {
    }

    /**
     * Constructs an instance of <code>InvalidRuleDefinition</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidRuleDefinition(String msg) {
        super(msg);
    }
}
