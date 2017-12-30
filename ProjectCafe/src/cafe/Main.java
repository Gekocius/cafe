/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafe;

import cafe.logic.InformationSystem;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Utilities;

/**
 *
 * @author David Fuchs
 */
public class Main extends Application {
    private final InformationSystem system = new InformationSystem();
    private final Scene loginScreen;
    private final Scene homeScreen;
    private final Scene registrationScreen;
    
    private final Map<String,TextField> homeScreenTextFields = new HashMap<>();
    private final Map<String,TextField> registrationScreenTextFields = new HashMap<>();
    private final Map<String,TextField> loginScreenTextFields = new HashMap<>();
    
    private Stage stage;
    
    public Main(){
        createTextFields();
        homeScreen = createHomeScreen();
        loginScreen = createLoginScreen();
        registrationScreen = createRegistrationScreen();
    }
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setScene(homeScreen);
        primaryStage.setTitle("Cafe");
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
        registrationScreenTextFields.put("email", new TextField());
        registrationScreenTextFields.put("name", new TextField());
        registrationScreenTextFields.put("surname", new TextField());
        registrationScreenTextFields.put("password", new TextField());
        loginScreenTextFields.put("email", new TextField());
        loginScreenTextFields.put("password", new TextField());
    }
    
    private Scene createHomeScreen(){
        BorderPane root = new BorderPane();
        VBox home = new VBox();
        HBox top = new HBox();
        Button  register = new Button("Register"),
                login = new Button("Log in"),
                search = new Button("Request search results");
        register.setOnAction((ActionEvent) -> {
            stage.setScene(registrationScreen);
        });
        login.setOnAction((ActionEvent) -> {
            stage.setScene(loginScreen);
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
        return new Scene(root,640,480);
    }
    
    private Scene createLoginScreen(){
        BorderPane root = new BorderPane();
        VBox center = new VBox();
        Button  login = new Button("Log in");
        login.setOnAction((ActionEvent) -> {
            
        });
        FlowPane email = new FlowPane();
        email.getChildren().addAll(new Label("E-mail address"),loginScreenTextFields.get("email"));
        FlowPane password = new FlowPane();
        password.getChildren().addAll(new Label("Password"),loginScreenTextFields.get("password"));
        center.getChildren().addAll(email,password);
        root.setCenter(center);
        root.setBottom(login);
        return new Scene(root,640,240);
    }
    
    private Scene createRegistrationScreen(){
        BorderPane root = new BorderPane();
        VBox center = new VBox();
        Button  register = new Button("Register");
        register.setOnAction((ActionEvent) -> {
            if(!validEmail(registrationScreenTextFields.get("email").getText().toLowerCase(Locale.ROOT)))
                Utilities.messageBox("That is not a valid email address.", "Invalid email address",
                                     "Invalid email address", Alert.AlertType.ERROR);
            else if(system.createUser(registrationScreenTextFields.get("email").getText().toLowerCase(Locale.ROOT),
                            registrationScreenTextFields.get("name").getText(),
                            registrationScreenTextFields.get("surname").getText(),
                            registrationScreenTextFields.get("password").getText()))
                stage.setScene(homeScreen);
            else
                Utilities.messageBox("Account with your email address already exists.", "Account creation error",
                                     "Account creation error", Alert.AlertType.ERROR);
        });
        FlowPane email = new FlowPane();
        email.getChildren().addAll(new Label("E-mail address"),registrationScreenTextFields.get("email"));
        FlowPane name = new FlowPane();
        name.getChildren().addAll(new Label("Name"),registrationScreenTextFields.get("name"));
        FlowPane surname = new FlowPane();
        surname.getChildren().addAll(new Label("Surname"),registrationScreenTextFields.get("surname"));
        FlowPane password = new FlowPane();
        password.getChildren().addAll(new Label("Password"),registrationScreenTextFields.get("password"));
        center.getChildren().addAll(email,name,surname,password);
        root.setCenter(center);
        root.setBottom(register);
        return new Scene(root,640,240);
    }
    
    private boolean validEmail(String email){
        return email.contains("@");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
