/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafe;

import cafe.logic.Cafe;
import cafe.logic.InformationSystem;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
    private final Scene homeScreenUser;
    private final Scene homeScreenAdmin;
    private final Scene registrationScreen;
    private final Scene searchScreen;
    private final Scene cafeDetail;
    private final Scene cafeDetailUser;
    private final Scene postComment;
    private final Scene addCafe;
    
    
    private final Map<String,TextField> homeScreenTextFields = new HashMap<>();
    private final Map<String,TextField> homeScreenUserTextFields = new HashMap<>();
    private final Map<String,TextField> homeScreenAdminTextFields = new HashMap<>();
    private final Map<String,TextField> registrationScreenTextFields = new HashMap<>();
    private final Map<String,TextField> loginScreenTextFields = new HashMap<>();
    private final Map<String,TextArea> cafeDetailTextFields = new HashMap<>();
    private final Map<String,TextArea> cafeDetailUserTextFields = new HashMap<>();
    private final Map<String,TextArea> postCommentTextFields = new HashMap<>();
    private final Map<String,TextField> addCafeTextFields = new HashMap<>();
    
    private final ListView<String> searchResults = new ListView<>();
    
    private Collection<Cafe> searchResultsComplete = null;
    
    
    private Stage stage;
    
    public Main(){
        createTextFields();
        homeScreen = createHomeScreen();
        homeScreenUser = createHomeScreenUser();
        homeScreenAdmin = createHomeScreenAdmin();
        loginScreen = createLoginScreen();
        registrationScreen = createRegistrationScreen();
        searchScreen =createSearchScreen();
        cafeDetail = createCafeDetail();
        cafeDetailUser = createCafeDetailUser();
        postComment = createPostComment();
        addCafe = createAddCafe();
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
        homeScreenUserTextFields.put("cafe name", new TextField());
        homeScreenUserTextFields.put("country", new TextField());
        homeScreenUserTextFields.put("city", new TextField());
        homeScreenUserTextFields.put("street", new TextField());
        homeScreenUserTextFields.put("coffee", new TextField());
        homeScreenUserTextFields.put("special offer", new TextField());
        homeScreenUserTextFields.put("minimal rating", new TextField());
        homeScreenAdminTextFields.put("cafe name", new TextField());
        homeScreenAdminTextFields.put("country", new TextField());
        homeScreenAdminTextFields.put("city", new TextField());
        homeScreenAdminTextFields.put("street", new TextField());
        homeScreenAdminTextFields.put("coffee", new TextField());
        homeScreenAdminTextFields.put("special offer", new TextField());
        homeScreenAdminTextFields.put("minimal rating", new TextField());
        registrationScreenTextFields.put("email", new TextField());
        registrationScreenTextFields.put("name", new TextField());
        registrationScreenTextFields.put("surname", new TextField());
        registrationScreenTextFields.put("password", new TextField());
        loginScreenTextFields.put("email", new TextField());
        loginScreenTextFields.put("password", new TextField());
        cafeDetailTextFields.put("rating", new TextArea());
        cafeDetailTextFields.put("OFOK", new TextArea());
        cafeDetailTextFields.put("special offers", new TextArea());
        cafeDetailTextFields.put("posts", new TextArea());
        cafeDetailUserTextFields.put("rating", new TextArea());
        cafeDetailUserTextFields.put("OFOK", new TextArea());
        cafeDetailUserTextFields.put("special offers", new TextArea());
        cafeDetailUserTextFields.put("posts", new TextArea());
        postCommentTextFields.put("comment", new TextArea());
        addCafeTextFields.put("name", new TextField());
        addCafeTextFields.put("country", new TextField());
        addCafeTextFields.put("city", new TextField());
        addCafeTextFields.put("street", new TextField());
        addCafeTextFields.put("offered kinds", new TextField());
        addCafeTextFields.put("add new kind", new TextField());
        addCafeTextFields.put("special offers", new TextField());
        addCafeTextFields.put("add new special", new TextField());
        
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
            search(homeScreenTextFields);
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

    private void search(Map<String,TextField> textFields) {
        searchResults.getItems().removeIf(filter -> {return true;});
        searchResultsComplete
                = system.search(textFields.get("cafe name").getText(),
                        textFields.get("country").getText(),
                        textFields.get("city").getText(),
                        textFields.get("street").getText(),
                        true,
                        textFields.get("coffee").getText(),
                        textFields.get("special offer").getText(),
                        getMinimalRating());
        searchResultsComplete.stream().map(cafe -> cafe.getName()).forEach(cafe_name -> {
            searchResults.getItems().add(cafe_name);
        });
        stage.setScene(searchScreen);
    }
    
    private Scene createSearchScreen(){
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);
        searchResults.setPrefHeight(600);
        FlowPane searchResultPane=new FlowPane();
        searchResultPane.setAlignment(Pos.CENTER);
        searchResultPane.getChildren().add(new Label("Found cafes"));
        root.getChildren().addAll(searchResultPane,  searchResults);
        return new Scene(root,640,480);
    }
    
    private Scene createHomeScreenUser(){
        BorderPane root = new BorderPane();
        VBox home = new VBox();
        HBox top = new HBox();
        Button  editProfile = new Button("Edit profile"),
                logout = new Button("Log out"),
                search = new Button("Request search results");
        editProfile.setOnAction((ActionEvent) -> {
            
        });
        logout.setOnAction((ActionEvent) -> {
            system.logout();
            stage.setScene(homeScreen);
            homeScreenUserTextFields.values().forEach(textField -> {textField.clear();});
        });
        search.setOnAction((ActionEvent) -> {
            search(homeScreenUserTextFields);
        });
        top.getChildren().addAll(homeScreenUserTextFields.get("cafe name"),editProfile,logout);
        FlowPane country = new FlowPane();
        country.getChildren().addAll(new Label("Country"),homeScreenUserTextFields.get("country"));
        FlowPane city = new FlowPane();
        city.getChildren().addAll(new Label("City"),homeScreenUserTextFields.get("city"));
        FlowPane street = new FlowPane();
        street.getChildren().addAll(new Label("Street"),homeScreenUserTextFields.get("street"));
        FlowPane coffee = new FlowPane();
        coffee.getChildren().addAll(new Label("Coffee"),homeScreenUserTextFields.get("coffee"));
        FlowPane specialOffer = new FlowPane();
        specialOffer.getChildren().addAll(new Label("Special offer"),homeScreenUserTextFields.get("special offer"));
        FlowPane rating = new FlowPane();
        rating.getChildren().addAll(new Label("Minimal rating"),homeScreenUserTextFields.get("minimal rating"));
        home.getChildren().addAll(new Label("Search by address"),country,city,street,
                                  new Label("Search by products"),coffee,specialOffer,
                                  new Label("Search by rating"),rating);
        root.setCenter(home);
        root.setTop(top);
        root.setBottom(search);
        return new Scene(root,640,480);
    }
    
    private Scene createHomeScreenAdmin(){
        BorderPane root = new BorderPane();
        VBox home = new VBox();
        HBox top = new HBox();
        Button  editUser = new Button("Edit user"),
                logout = new Button("Log out"),
                search = new Button("Request search results");
        editUser.setOnAction((ActionEvent) -> {
            
        });
        logout.setOnAction((ActionEvent) -> {
            system.logout();
            stage.setScene(homeScreen);
            homeScreenAdminTextFields.values().forEach(textField -> {textField.clear();});
        });
        search.setOnAction((ActionEvent) -> {
            search(homeScreenAdminTextFields);
        });
        top.getChildren().addAll(homeScreenAdminTextFields.get("cafe name"),editUser,logout);
        FlowPane country = new FlowPane();
        country.getChildren().addAll(new Label("Country"),homeScreenAdminTextFields.get("country"));
        FlowPane city = new FlowPane();
        city.getChildren().addAll(new Label("City"),homeScreenAdminTextFields.get("city"));
        FlowPane street = new FlowPane();
        street.getChildren().addAll(new Label("Street"),homeScreenAdminTextFields.get("street"));
        FlowPane coffee = new FlowPane();
        coffee.getChildren().addAll(new Label("Coffee"),homeScreenAdminTextFields.get("coffee"));
        FlowPane specialOffer = new FlowPane();
        specialOffer.getChildren().addAll(new Label("Special offer"),homeScreenAdminTextFields.get("special offer"));
        FlowPane rating = new FlowPane();
        rating.getChildren().addAll(new Label("Minimal rating"),homeScreenAdminTextFields.get("minimal rating"));
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
            if(system.login(loginScreenTextFields.get("email").getText().toLowerCase(Locale.ROOT),
                         loginScreenTextFields.get("password").getText())){
                if(system.loggedInAsAdmin())
                    stage.setScene(homeScreenAdmin);
                else
                    stage.setScene(homeScreenUser);
                loginScreenTextFields.values().forEach(textField -> {textField.clear();});
            }
            else
                Utilities.messageBox("Login wasn't successful.", "Failed login", "Failed login", Alert.AlertType.ERROR);
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
        VBox left = new VBox();
        VBox right = new VBox();
        GridPane leftGrid=new GridPane();
        GridPane rightGrid=new GridPane();
        Button  register = new Button("Register");
        register.setPrefSize(600, 10);
        register.setOnAction((ActionEvent) -> {
            if(!validEmail(registrationScreenTextFields.get("email").getText().toLowerCase(Locale.ROOT)))
                Utilities.messageBox("That is not a valid email address.", "Invalid email address",
                                     "Invalid email address", Alert.AlertType.ERROR);
            else if(system.createUser(registrationScreenTextFields.get("email").getText().toLowerCase(Locale.ROOT),
                            registrationScreenTextFields.get("name").getText(),
                            registrationScreenTextFields.get("surname").getText(),
                            registrationScreenTextFields.get("password").getText())){
                stage.setScene(homeScreen);
                registrationScreenTextFields.values().forEach(textField -> {
                    textField.clear();
                });
            }
            else
                Utilities.messageBox("Account with your email address already exists.", "Account creation error",
                                     "Account creation error", Alert.AlertType.ERROR);
        });
        leftGrid.add(new Label("E-mail address"),0,0,1,1);
        leftGrid.add(new Label("Name"),0,1,1,1);
        leftGrid.add(new Label("Surname"),0,2,1,1);
        leftGrid.add(new Label("Password"),0,3,1,1);
        leftGrid.setVgap(15);
        registrationScreenTextFields.get("email").setPrefWidth(520);
        rightGrid.add(registrationScreenTextFields.get("email"),1,0,1,1);
        rightGrid.add(registrationScreenTextFields.get("name"),1,1,1,1);
        rightGrid.add(registrationScreenTextFields.get("surname"),1,2,1,1);
        rightGrid.add(registrationScreenTextFields.get("password"),1,3,1,1);
        rightGrid.add(register,1,7,1,1);
        rightGrid.setHgap(10);                    
        rightGrid.setVgap(5);
        left.getChildren().add(leftGrid);
        right.getChildren().add(rightGrid);
        root.setLeft(left);
        root.setCenter(right);
        root.setRight(new Label("     "));
        root.setTop(new Label("\n"));
        return new Scene(root,640,240);
    }
    
    private Scene createCafeDetail(){
        VBox root = new VBox();
        Label cafeDetailLabel = new Label("Cafe detail");
        
        HBox ratingHBox = new HBox(); 
        
        HBox kindsOfCafeHBox = new HBox(); 
        VBox offeredKindsVBox = new VBox();
        VBox specialOffersVBox = new VBox();
        
        root.setSpacing(5);
        
        
        offeredKindsVBox.getChildren().addAll(new Label("Offered kinds of coffee"), cafeDetailTextFields.get("OFOK"));
        specialOffersVBox.getChildren().addAll(new Label("Special offers"), cafeDetailTextFields.get("special offers"));
        kindsOfCafeHBox.getChildren().addAll(offeredKindsVBox, specialOffersVBox);
        
        HBox postsHBox = new HBox();
        postsHBox.getChildren().addAll(new Label("Posts"));
        postsHBox.setAlignment(Pos.CENTER_LEFT);
        
        
        ratingHBox.getChildren().addAll(new Label("Rating:"),cafeDetailTextFields.get("rating"));
        ratingHBox.setMaxHeight(10);
        root.getChildren().addAll(cafeDetailLabel,ratingHBox, kindsOfCafeHBox, postsHBox, cafeDetailTextFields.get("posts"));
        return new Scene(root,640,480);
    }
    
    private Scene createCafeDetailUser(){
        VBox root = new VBox();
        Label cafeDetailLabel = new Label("Cafe detail");
        
        HBox ratingHBox = new HBox(); 
        
        HBox kindsOfCafeHBox = new HBox(); 
        VBox offeredKindsVBox = new VBox();
        VBox specialOffersVBox = new VBox();
        
        Button addRatingButton = new Button("Add rating");
        Button addPostButton = new Button("Add post");
     
        
        addPostButton.setOnAction((ActionEvent) -> {
             stage.setScene(postComment);
        });
        
        addRatingButton.setOnAction((ActionEvent) -> {
        
        
        });
        
      
        root.setSpacing(5);
        
        
        offeredKindsVBox.getChildren().addAll(new Label("Offered kinds of coffee"), cafeDetailUserTextFields.get("OFOK"));
        specialOffersVBox.getChildren().addAll(new Label("Special offers"), cafeDetailUserTextFields.get("special offers"));
        kindsOfCafeHBox.getChildren().addAll(offeredKindsVBox, specialOffersVBox);
        
        HBox postsHBox = new HBox();
        postsHBox.getChildren().addAll(new Label("Posts"), addPostButton);
        postsHBox.setAlignment(Pos.CENTER_LEFT);
        postsHBox.setSpacing(300);
        
        
        ratingHBox.getChildren().addAll(new Label("Rating:"),cafeDetailUserTextFields.get("rating"), addRatingButton);
        ratingHBox.setMaxHeight(10);
        ratingHBox.setSpacing(3);
        root.getChildren().addAll(cafeDetailLabel,ratingHBox, kindsOfCafeHBox, postsHBox, cafeDetailUserTextFields.get("posts"));
        return new Scene(root,640,480);
    }
    
    private Scene createPostComment(){
        VBox root = new VBox();
        Button postCommentButton = new Button("Post");
        postCommentButton.setPrefWidth(250);
        
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.getChildren().addAll(new Label("Post a comment"), postCommentTextFields.get("comment"), postCommentButton);
        return new Scene(root, 640,480);
    }
    
    private Scene createAddCafe(){
        VBox root = new VBox();
        
        HBox nameHBox = new HBox();
        nameHBox.getChildren().addAll(new Label ("Name"), addCafeTextFields.get("name"));
        nameHBox.setSpacing(110);
        
        HBox locationHBox = new HBox();
        VBox labelsLocationVBox = new VBox();
        labelsLocationVBox.getChildren().addAll(new Label("Country"),new Label("City"), new Label("Street"));
        labelsLocationVBox.setSpacing(10);
        
        VBox textFieldsVBox = new VBox();
        textFieldsVBox.getChildren().addAll(addCafeTextFields.get("country"),addCafeTextFields.get("city"), 
                                        addCafeTextFields.get("street"));
        
        locationHBox.getChildren().addAll(labelsLocationVBox, textFieldsVBox);
        locationHBox.setSpacing(100);
        
        HBox offersHBox = new HBox();
        VBox addKindsVBox = new VBox();
        VBox addSpecialOfVBox = new VBox();
        Button addNewKindButton = new Button("Add new kind of coffee");
        Button addNewSpecialButton = new Button("Add new special offer");
          
        addNewKindButton.setOnAction((ActionEvent) -> {
        
        });
        addNewSpecialButton.setOnAction((ActionEvent) -> {
        
        });
            
        addKindsVBox.getChildren().addAll(new Label("Offered kinds of coffee"), addCafeTextFields.get("offered kinds"),
                new Label("Kind of coffee"), addCafeTextFields.get("add new kind"), addNewKindButton);
        
        addSpecialOfVBox.getChildren().addAll(new Label("Special offers"), addCafeTextFields.get("special offers"),
                new Label("Special offer"), addCafeTextFields.get("add new special"), addNewSpecialButton);
       
        offersHBox.getChildren().addAll(addKindsVBox, addSpecialOfVBox);
        offersHBox.setSpacing(150);
        
        Button registerButton = new Button("Register cafe");
        registerButton.setPrefWidth(620);
        registerButton.setPrefHeight(50);
        
        registerButton.setOnAction((ActionEvent) -> {
        
        });
        root.getChildren().addAll(new Label("Add cafe"), nameHBox, new Label("Location"), locationHBox, offersHBox, 
                registerButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(5);
        return new Scene(root, 640,480);
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
    
    private double getMinimalRating(){
        String rating = homeScreenTextFields.get("minimal rating").getText();
        return !rating.isEmpty() ? Double.parseDouble(rating) : 0;
    }
}
