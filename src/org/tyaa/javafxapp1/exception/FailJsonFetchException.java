/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.javafxapp1.exception;

/**
 *
 * @author yurii
 */
public class FailJsonFetchException extends Exception{

    @Override
    public String getMessage() {
        
        return "JSON fetching failed";
    }
}
