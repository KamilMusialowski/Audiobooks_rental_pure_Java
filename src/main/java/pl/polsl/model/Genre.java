/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pl.polsl.model;

/**
 *
 * Enum class representing genre of audiobook.
 * 
 * @author Kamil Musialowski
 * @version 1.5
 */
public enum Genre {
    /**
     * Represents crime fantasy genre.
     */
    CRIME("CRIME"),
    /**
     * Represents novel genre.
     */
    NOVEL("NOVEL"),
    /**
     * Represents fantasy genre.
     */
    FANTASY("FANTASY"),
    /**
     * Represents unspecified genre.
     */
    UNSPECIFIED("UNSPECIFIED");
    
    /**
     * Field representing label/description of enum values.
     */
    private final String label;
    /**
     * Constructor of Genre enum type object.
     * @param label Label of created object.
     */
    private Genre(String label) {
        this.label = label;
    }
    /**
     * Returns label of Genre enum type value.
     * @return Genre values label.
     */
    public String getLabel() {
        return this.label;
    }
    
    
}
