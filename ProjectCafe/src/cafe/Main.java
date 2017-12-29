/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafe;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author David Fuchs
 */
public class Main extends Application {
    private final Map<String,TextField> homeScreenTextFields = new HashMap<>();
    
    @Override
    public void start(Stage primaryStage) {
        createTextFields();
        Scene scene = new Scene(createHomeScreen(), 640, 480);
        primaryStage.setTitle("Cafe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createTextFields() {
        homeScreenTextFields.put("cafe name", new TextField());
        homeScreenTextFields.put("country", new TextField());
        homeScreenTextFields.put("city", new TextField());
        homeScreenTextFields.put("street", new TextField());
        homeScreenTextFields.put("coffee", new TextField());
        homeScreenTextFields.put("special offer", new TextField());
        homeScreenTextFields.put("minimal rating", new TextField());
    }
    
    private BorderPane createHomeScreen(){
        BorderPane root = new BorderPane();
        VBox home = new VBox();
        HBox top = new HBox();
        Button  register = new Button("Register"),
                login = new Button("Log in"),
                search = new Button("Request search results");
        register.setOnAction((ActionEvent) -> {
            
        });
        login.setOnAction((ActionEvent) -> {
            
        });
        search.setOnAction((ActionEvent) -> {
            
        });
        top.getChildren().addAll(homeScreenTextFields.get("cafe name"),register,login);
        FlowPane country = new FlowPane();
        country.getChildren().addAll(new Label("Country"),homeScreenTextFields.get("country"));
        FlowPane city = new FlowPane();
        city.getChildren().addAll(new Label("City"),homeScreenTextFields.get("city"));
        FlowPane street = new FlowPane();
        street.getChildren().addAll(new Label("Street"),homeScreenTextFields.get("street"));
        FlowPane coffee = new FlowPane();
        coffee.getChildren().addAll(new Label("Coffee"),homeScreenTextFields.get("coffee"));
        FlowPane specialOffer = new FlowPane();
        specialOffer.getChildren().addAll(new Label("Special offer"),homeScreenTextFields.get("special offer"));
        FlowPane rating = new FlowPane();
        rating.getChildren().addAll(new Label("Minimal rating"),homeScreenTextFields.get("minimal rating"));
        home.getChildren().addAll(new Label("Search by address"),country,city,street,
                                  new Label("Search by products"),coffee,specialOffer,
                                  new Label("Search by rating"),rating);
        root.setCenter(home);
        root.setTop(top);
        root.setBottom(search);
        return root;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
