/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.util.Objects;

/**
 * Module package class representing single audiobook.
 *
 * @author kmusi
 * @version 1.5
 */
public class Audiobook {

    /**
     * Srting representing author of audiobook.
     */
    private String author;
    /**
     * String representing title of audiobook.
     */
    private String title;
    /**
     * Genre enum object representing genre of audiobook.
     */
    private Genre genre;
    /**
     * Double representing price of audiobook.
     */
    private double price;

    /**
     * Overrided equals method for Audiobook class.
     *
     * @param obj Object to which Audiobook class object is compared.
     * @return Boolean type value - true or false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Audiobook other = (Audiobook) obj;
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return this.genre == other.genre;
    }

    /**
     * Constructor of Audiobook class object.
     *
     * @param author String parameter containing author of audiobook.
     * @param title String parameter containing title of audiobook.
     * @param genre Genre enum parameter containing genre of audiobook.
     * @param price Double parameter containig price of audiobook.
     */
    public Audiobook(String author, String title, Genre genre, double price) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.price = price;
    }

    /**
     * Returns informations about audiobook with String object.
     *
     * @return String containing iformations about audiobook: author, title,
     * genre and price.
     */
    @Override
    public String toString() {
        return author + " " + title + " ; " + genre.getLabel() + " ; " + price;
    }

    /**
     * Getter returning genre of audiobook.
     *
     * @return Genre enum object representing genre of audiobook.
     */
    public Genre getGenre() {
        return this.genre;
    }

    /**
     * Setting genre of audiobook.
     *
     * @param genre Genre enum type object contaning genre of audiobook.
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Getter returning author of audiobook.
     *
     * @return String containig author of audiobook.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setting author of audiobook.
     *
     * @param author String containing author to be setted for audiobook.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Getter returning title of audiobook.
     *
     * @return String containig title fo audiobook.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setting title of audiobook.
     *
     * @param title String containing title to be setted for audiobook.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter returning price of audiobook.
     *
     * @return Double containig price fo audiobook.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setting price of audiobook.
     *
     * @param price Double containing price to be setted for audiobook.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
