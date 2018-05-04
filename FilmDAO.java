/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModel;

/**
 *
 * @author dvice
 */
public class FilmDAO {
    private String filmTitle;
    private String filmRating;
    private String filmDescription;
    private double filmRentPrice;

    /**
     * @return the filmTitle
     */
    public String getFilmTitle() {
        return filmTitle;
    }

    /**
     * @param filmTitle the filmTitle to set
     */
    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    /**
     * @return the filmRating
     */
    public String getFilmRating() {
        return filmRating;
    }

    /**
     * @param filmRating the filmRating to set
     */
    public void setFilmRating(String filmRating) {
        this.filmRating = filmRating;
    }

    /**
     * @return the filmDescription
     */
    public String getFilmDescription() {
        return filmDescription;
    }

    /**
     * @param filmDescription the filmDescription to set
     */
    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    /**
     * @return the filmRentPrice
     */
    public double getFilmRentPrice() {
        return filmRentPrice;
    }

    /**
     * @param filmRentPrice the filmRentPrice to set
     */
    public void setFilmRentPrice(double filmRentPrice) {
        this.filmRentPrice = filmRentPrice;
    }
    
    /**
     *
     */
    public FilmDAO()
    {
        
    }
    
    /**
     *
     * @param fTitle
     * @param fRating
     * @param fDescription
     * @param fRentPrice
     */
    public FilmDAO(String fTitle, String fRating, String fDescription, double fRentPrice)
    {
        filmTitle = fTitle;
        filmRating = fRating;
        filmDescription = fDescription;
        filmRentPrice = fRentPrice;
    }
}
