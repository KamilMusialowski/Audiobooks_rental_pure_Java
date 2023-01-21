/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

/**
 * Exception class of "model" package.
 * @author Kamil Musialowski
 * @version 1.1
 */
public class OwnException extends Exception {
    /**
     * Contructor of OwnException class without any params.
     */
    public OwnException(){
    }
    /**
     * Constructor of OwnException class with String param.
     * @param message Communication thrown with exception.
     */
    public OwnException(String message) {
        super(message);
    }
}
