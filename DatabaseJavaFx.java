/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasejavafx;

import inputOutput.PostgreSQLConnect;
import inputOutput.ConnectionData;
import inputOutput.XmlParser;
import dataModel.FilmDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.application.Application.launch;
import javafx.scene.Group;

/**
 *
 * @author dvice
 */
public class DatabaseJavaFx extends Application {
    //Logger for exception catches
    private static final Logger logger = Logger.getLogger(DatabaseJavaFx.class.getName());
    
    //The ObservableList that holds all of the films
    private ObservableList<FilmDAO> data = FXCollections.observableArrayList();
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        //Set up the Table
        TableView tableView = new TableView();
        tableView.setEditable(false);
        
        //Set up the Table's label
        Label label = new Label("Films");
        
        //Set up the column for the film titles.
        TableColumn title = new TableColumn("Title");
        final int TITLE_MIN_WIDTH = 200;
        title.setMinWidth(TITLE_MIN_WIDTH);
        title.setCellValueFactory(new PropertyValueFactory<FilmDAO, String>("filmTitle"));
        
        //Set up the column for the film descriptions
        TableColumn description = new TableColumn("Description");
        final int DESC_MIN_WIDTH = 600;
        description.setMinWidth(DESC_MIN_WIDTH);
        description.setCellValueFactory(new PropertyValueFactory<FilmDAO, String>("filmDescription"));
        
        //Set up the column for the film rental price
        TableColumn rentalRate = new TableColumn("Rental Rate");
        final int RENTRATE_MIN_WIDTH = 100;
        rentalRate.setMinWidth(RENTRATE_MIN_WIDTH);
        rentalRate.setCellValueFactory(new PropertyValueFactory<FilmDAO, String>("filmRentPrice"));
        
        //Set up the column for the film rating
        TableColumn rating = new TableColumn("Rating");
        final int RATING_MIN_WIDTH = 100;
        rating.setMinWidth(RATING_MIN_WIDTH);
        rating.setCellValueFactory(new PropertyValueFactory<FilmDAO, String>("filmRating"));
        
        //Add the columns to the table
        tableView.getColumns().addAll(title, description, rentalRate, rating);
        
        //Create the button that fetches the files from the database
        Button fetchFilmsButton = new Button("Fetch Films From Database");
        fetchFilmsButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                fetchData(tableView);
            }
        });
        
        //Set up the scene
        Scene scene = new Scene(new Group());
        
        //Set up the UI, its style and add the label and table to it
        VBox vbox = new VBox();
        vbox.setPrefHeight(500);
        vbox.setStyle("-fx-background-color: #00bfff; -fx-padding: 50;");
        vbox.getChildren().addAll(label, tableView);
        
        //Add the vbox and the button to the scene
        ((Group) scene.getRoot()).getChildren().addAll(vbox, fetchFilmsButton);
        
        //Set up stage title, add the scene, and then display
        primaryStage.setTitle("Films For Rent");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     *
     * @param paraTV
     */
    public void fetchData(TableView paraTV)
    {
        //A try to establish the connection
        try(Connection connection = getConnection())
        {
            paraTV.setItems(fetchFilms(connection));
        }
        catch(SQLException | ClassNotFoundException excep)
        {
            logger.log(Level.SEVERE, null, excep);
        }
    }
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException
    {
        //Set up the XML parser and set the destination of the XML file.
        XmlParser xml = new XmlParser("inputOutput/properties.xml");
        
        //Get the connection data from the parsed XML
        ConnectionData xmlConnData = xml.getConnectionData();
        
        //Set up the PostgreSQL connection with the data from the XML
        PostgreSQLConnect psqlConnect = new PostgreSQLConnect(xmlConnData);
        
        //Establish the database connection
        Connection databaseConn = psqlConnect.getConnection();
        
        return databaseConn;
    }
    
    /**
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public ObservableList<FilmDAO> fetchFilms(Connection connection) throws SQLException
    {
        //Set up the ObservableList for the films
        ObservableList<FilmDAO> films = FXCollections.observableArrayList();
        
        //set up the database commands
        String select = "SELECT title, rental_rate, rating, description " + "FROM film " + "ORDER BY title;";
        
        //set up the statement
        Statement statement = connection.createStatement();
        
        //execute the query
        ResultSet resultSet = statement.executeQuery(select);
        
        //cycle through each entry, getting the necessary information and
        //then add the film to the list
        while(resultSet.next())
        {
            FilmDAO film = new FilmDAO();
            film.setFilmTitle(resultSet.getString("title"));
            film.setFilmRating(resultSet.getString("rating"));
            film.setFilmDescription(resultSet.getString("description"));
            film.setFilmRentPrice(resultSet.getDouble("rental_rate"));
            
            films.add(film);
        }
        
        return films;
    }
}
