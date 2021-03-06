package cafe;

import cafe.logic.Cafe;
import cafe.logic.Coffee;
import cafe.logic.InformationSystem;
import cafe.logic.SpecialOffer;
import cafe.logic.User;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Utilities;



/**
 * Class for UI and user input checks.
 * @author David Fuchs
 */
public class Main extends Application {
    private static final int BASE_YEAR = 1900;
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
    private final Scene changeCafeDetail;
    private final Scene findUser;
    private final Scene editUser;
    private final Scene editUserAdmin;
    
    private final Map<String,TextField> homeScreenTextFields = new HashMap<>();
    private final Map<String,TextField> homeScreenUserTextFields = new HashMap<>();
    private final Map<String,TextField> homeScreenAdminTextFields = new HashMap<>();
    private final Map<String,TextField> registrationScreenTextFields = new HashMap<>();
    private final Map<String,TextField> loginScreenTextFields = new HashMap<>();
    private final Map<String,TextField> cafeDetailTextFields = new HashMap<>();
    private final Map<String,TextField> cafeDetailUserTextFields = new HashMap<>();
    private final Map<String,TextArea> postCommentTextFields = new HashMap<>();
    private final Map<String,TextField> addCafeTextFields = new HashMap<>();
    private final Map<String,ListView<String>> cafeDetailListViews = new HashMap<>();
    private final Map<String,ListView<String>> cafeDetailUserListViews = new HashMap<>();
    private final Map<String,ListView<String>> changeCafeDetailListViews = new HashMap<>();
    private final Map<String,TextField> changeCafeDetailTextFields = new HashMap<>();
    private final Map<String,TextField> findUserTextFields = new HashMap<>();
    private final Map<String,TextField> editUserTextFields = new HashMap<>();
    private final Map<String,TextField> editUserAdminTextFields = new HashMap<>();
    
    private final ListView<String> searchResults = new ListView<>();
    private final TextArea posts = new TextArea();
    private final TextArea postsUser = new TextArea();
    private final CheckBox isActiveCheckBox = new CheckBox("Is this cafe active?");
    
    private Collection<Cafe> searchResultsComplete = null;
    private Cafe selectedCafe = null;
    private User selectedUser = null;
    
    private Stage stage;

    
    /**
     * Initializes GUI elements.
     */
    public Main(){
        posts.setEditable(false);
        postsUser.setEditable(false);
        createTextFields();
        createListViews();
        homeScreen = createHomeScreen();
        homeScreenUser = createHomeScreenUser();
        homeScreenAdmin = createHomeScreenAdmin();
        loginScreen = createLoginScreen();
        loginScreen.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
        registrationScreen = createRegistrationScreen();
        registrationScreen.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
        searchScreen =createSearchScreen();
        cafeDetail = createCafeDetail();
        cafeDetail.getRoot().setOnKeyPressed(value -> {
            returnToHomeScreen(value);
            posts.clear();
        });
        cafeDetailUser = createCafeDetailUser();
        cafeDetailUser.getRoot().setOnKeyPressed(value -> {
            returnToHomeScreen(value);
            postsUser.clear();
        });
        postComment = createPostComment();
        postComment.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
        addCafe = createAddCafe();
        addCafe.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
        changeCafeDetail = createChangeCafeDetail();
        changeCafeDetail.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
        findUser = createFindUser();
        findUser.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
        editUser = createEditUser();
        editUser.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
        editUserAdmin = createEditUserAdmin();
        editUserAdmin.getRoot().setOnKeyPressed(value -> returnToHomeScreen(value));
    }

    /**
     * Handles user's requests to return to home screen.
     * @param value 
     */
    private void returnToHomeScreen(KeyEvent value) {
        if(value.getCode().equals(KeyCode.ESCAPE))
            switchToHomeScreen();
    }
    
    /**
     * Initializes primary stage.
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setScene(homeScreen);
        stage.setTitle("Cafe");
        stage.show();
    }
    
    /**
     * Creates list views for displaying various lists.
     */
    private void createListViews(){
        cafeDetailListViews.put("OFOK",new ListView<>());
        cafeDetailListViews.put("special offers",new ListView<>());
        cafeDetailUserListViews.put("OFOK",new ListView<>());
        cafeDetailUserListViews.put("special offers",new ListView<>());
        changeCafeDetailListViews.put("OFOK", new ListView<>());
        changeCafeDetailListViews.put("special offers", new ListView<>());
    }

    /**
     * Creates text fields needed for user input.
     */
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
        cafeDetailTextFields.put("rating", new TextField());
        cafeDetailUserTextFields.put("rating", new TextField());
        postCommentTextFields.put("comment", new TextArea());
        addCafeTextFields.put("name", new TextField());
        addCafeTextFields.put("country", new TextField());
        addCafeTextFields.put("city", new TextField());
        addCafeTextFields.put("street", new TextField());
        addCafeTextFields.put("offered kinds", new TextField());
        addCafeTextFields.put("add new kind", new TextField());
        addCafeTextFields.put("special offers", new TextField());
        addCafeTextFields.put("add new special", new TextField());
        changeCafeDetailTextFields.put("name", new TextField());
        changeCafeDetailTextFields.put("country", new TextField());
        changeCafeDetailTextFields.put("city", new TextField());
        changeCafeDetailTextFields.put("street", new TextField());
        changeCafeDetailTextFields.put("add new kind", new TextField());
        changeCafeDetailTextFields.put("add new special", new TextField());
        findUserTextFields.put("e-mail", new TextField());
        editUserTextFields.put("e-mail", new TextField());
        editUserTextFields.put("name", new TextField());
        editUserTextFields.put("surname", new TextField());
        editUserTextFields.put("password", new TextField());
        editUserAdminTextFields.put("e-mail", new TextField());
        editUserAdminTextFields.put("name", new TextField());
        editUserAdminTextFields.put("surname", new TextField());

    }
    
    /**
     * Creates home screen and sets button actions.
     * @return 
     */
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

    /**
     * Starts database search and displays the results on the search screen.
     * @param textFields 
     */
    private void search(Map<String,TextField> textFields) {
        selectedCafe = null;
        searchResults.getItems().removeIf(filter -> {return true;});
        searchResultsComplete
                = system.search(textFields.get("cafe name").getText(),
                        textFields.get("country").getText(),
                        textFields.get("city").getText(),
                        textFields.get("street").getText(),
                        true,
                        textFields.get("coffee").getText(),
                        textFields.get("special offer").getText(),
                        getDouble(homeScreenTextFields.get("minimal rating").getText()));
        searchResultsComplete.stream().map(cafe -> cafe.getName()).forEach(cafe_name -> {
            searchResults.getItems().add(cafe_name);
        });
        stage.setScene(searchScreen);
    }
    
    /**
     * Creates search screen and sets actions.
     * @return 
     */
    private Scene createSearchScreen(){
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);
        searchResults.setPrefHeight(600);
        searchResults.setOnMouseClicked((MouseEvent) -> {
            if(system.loggedInAsAdmin())
                viewCafeDetail(changeCafeDetail, changeCafeDetailTextFields, changeCafeDetailListViews);
            else if(system.loggedIn())
                viewCafeDetail(cafeDetailUser,cafeDetailUserTextFields,cafeDetailUserListViews);
            else
                viewCafeDetail(cafeDetail,cafeDetailTextFields,cafeDetailListViews);
        });
        FlowPane searchResultPane=new FlowPane();
        searchResultPane.setAlignment(Pos.CENTER);
        searchResultPane.getChildren().add(new Label("Found cafes"));
        root.getChildren().addAll(searchResultPane,  searchResults);
        return new Scene(root,640,480);
    }

    /**
     * Creates cafe detail screen and sets button actions.
     * @param variant
     * @param textFields
     * @param listViews 
     */
    private void viewCafeDetail(Scene variant,Map<String,TextField> textFields,Map<String,ListView<String>> listViews) {
        try{
            selectedCafe = searchResultsComplete.stream().filter(cafe -> {
                return cafe.getName().equals(searchResults.getSelectionModel().getSelectedItem());
            }).findFirst().get();
        }
        catch(Exception ex){
            Utilities.messageBox("No such cafe found", "Not found", "Cafe not found", Alert.AlertType.INFORMATION);
            switchToHomeScreen();
        }
        if(selectedCafe == null)
            return;
        double rating = selectedCafe.getRating();
        if(system.loggedInAsAdmin()){
            textFields.get("name").setText(selectedCafe.getName());
            textFields.get("country").setText(selectedCafe.getCountry());
            textFields.get("city").setText(selectedCafe.getCity());
            textFields.get("street").setText(selectedCafe.getStreet());
            isActiveCheckBox.setSelected(selectedCafe.isActive());
        }
        else{
            textFields.get("rating").setText(rating == Double.NaN ? "Not rated yet" : rating + "");
            if(system.loggedIn())
                selectedCafe.getPosts().forEach(post -> postsUser.appendText(post.toString()));
            else
                selectedCafe.getPosts().forEach(post -> posts.appendText(post.toString()));
        }
        selectedCafe.getCoffees().forEach(coffee -> {
            listViews.get("OFOK").getItems().add(coffee.toString());
        });
        selectedCafe.getSpecialOffers().forEach(offer -> {
            listViews.get("special offers").getItems().add(offer.toString());
        });
        stage.setScene(variant);
    }

    /**
     * Switches to the correct home screen depending on the kind of user,
     * who is logged in. Might be requested by user or the application itself.
     */
    private void switchToHomeScreen() {
        if(system.loggedInAsAdmin())
            stage.setScene(homeScreenAdmin);
        else if(system.loggedIn())
            stage.setScene(homeScreenUser);
        else
            stage.setScene(homeScreen);
    }
    
    /**
     * Creates home screen and sets button actions.
     * Home screen looks different when a user is logged in.
     * @return 
     */
    private Scene createHomeScreenUser(){
        BorderPane root = new BorderPane();
        VBox home = new VBox();
        HBox top = new HBox();
        Button  editProfile = new Button("Edit profile"),
                logout = new Button("Log out"),
                search = new Button("Request search results");
        editProfile.setOnAction((ActionEvent) -> {
            editUserTextFields.get("name").setText(system.getLoggedInUser().getName());
            editUserTextFields.get("surname").setText(system.getLoggedInUser().getSurname());
            editUserTextFields.get("e-mail").setText(system.getLoggedInUser().getEmail());
            stage.setScene(editUser);
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
    
    /**
     * Creates home screen and sets button actions.
     * Home screen looks different when an admin is logged in.
     * @return 
     */
    private Scene createHomeScreenAdmin(){
        BorderPane root = new BorderPane();
        VBox home = new VBox();
        HBox top = new HBox();
        Button  editUser = new Button("Edit user"),
                addCafeBtn = new Button("Add cafe"),
                logout = new Button("Log out"),
                search = new Button("Request search results");
        editUser.setOnAction((ActionEvent) -> {
            stage.setScene(findUser);
        });
        logout.setOnAction((ActionEvent) -> {
            system.logout();
            stage.setScene(homeScreen);
            homeScreenAdminTextFields.values().forEach(textField -> {textField.clear();});
        });
        search.setOnAction((ActionEvent) -> {
            search(homeScreenAdminTextFields);
        });
        addCafeBtn.setOnAction((ActionEvent) -> {
            stage.setScene(addCafe);
        });
        top.getChildren().addAll(homeScreenAdminTextFields.get("cafe name"),editUser,addCafeBtn,logout);
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
    
    /**
     * Creates screen that allows the user to input login information
     * and passes that information to the class responsible for application logic.
     * @return 
     */
    private Scene createLoginScreen(){
        BorderPane root = new BorderPane();
        VBox center = new VBox();
        Button  login = new Button("Log in");
        login.setOnAction((ActionEvent) -> {
            if(system.login(loginScreenTextFields.get("email").getText().toLowerCase(Locale.ROOT),
                         loginScreenTextFields.get("password").getText())){
                switchToHomeScreen();
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
    
    /**
     * Creates screen that allows the user to input registration information
     * and passes that to the class handling application logic.
     * @return 
     */
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
                switchToHomeScreen();
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
    
    /**
     * Create screen for displaying cafe information to users,
     * who are not logged in.
     * @return 
     */
    private Scene createCafeDetail(){
        VBox root = new VBox();
        Label cafeDetailLabel = new Label("Cafe detail");
        
        HBox ratingHBox = new HBox(); 
        
        HBox kindsOfCafeHBox = new HBox(); 
        VBox offeredKindsVBox = new VBox();
        VBox specialOffersVBox = new VBox();
        
        root.setSpacing(5);
        
        
        offeredKindsVBox.getChildren().addAll(new Label("Offered kinds of coffee"),cafeDetailListViews.get("OFOK"));
        specialOffersVBox.getChildren().addAll(new Label("Special offers"),cafeDetailListViews.get("special offers"));
        kindsOfCafeHBox.getChildren().addAll(offeredKindsVBox, specialOffersVBox);
        
        HBox postsHBox = new HBox();
        postsHBox.getChildren().addAll(new Label("Posts"));
        postsHBox.setAlignment(Pos.CENTER_LEFT);
        
        
        ratingHBox.getChildren().addAll(new Label("Rating:"),cafeDetailTextFields.get("rating"));
        cafeDetailTextFields.get("rating").setEditable(false);
        ratingHBox.setMaxHeight(10);
        root.getChildren().addAll(cafeDetailLabel,ratingHBox, kindsOfCafeHBox, postsHBox, posts);
        return new Scene(root,640,480);
    }
    
    /**
     * Create screen for displaying cafe information to users,
     * who are logged in.
     * @return 
     */
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
            String ratingS = cafeDetailUserTextFields.get("rating").getText();
            if(!ratingS.isEmpty()){
                double rating = Double.parseDouble(ratingS);
                if(rating >= 0 && rating <= 5)
                    system.rate(system.getLoggedInUser().getId(), selectedCafe.getID(),rating);
                else
                    Utilities.messageBox("Rating must be in range 0-5.", "Rating out of range", "Rating out of range", Alert.AlertType.ERROR);
            }
        });
        
      
        root.setSpacing(5);
        
        
        offeredKindsVBox.getChildren().addAll(new Label("Offered kinds of coffee"),cafeDetailUserListViews.get("OFOK"));
        specialOffersVBox.getChildren().addAll(new Label("Special offers"),cafeDetailUserListViews.get("special offers"));
        kindsOfCafeHBox.getChildren().addAll(offeredKindsVBox, specialOffersVBox);
        
        HBox postsHBox = new HBox();
        postsHBox.getChildren().addAll(new Label("Posts"), addPostButton);
        postsHBox.setAlignment(Pos.CENTER_LEFT);
        postsHBox.setSpacing(300);
        
        
        ratingHBox.getChildren().addAll(new Label("Rating:"),cafeDetailUserTextFields.get("rating"), addRatingButton);
        ratingHBox.setMaxHeight(10);
        ratingHBox.setSpacing(3);
        root.getChildren().addAll(cafeDetailLabel,ratingHBox, kindsOfCafeHBox, postsHBox, postsUser);
        return new Scene(root,640,480);
    }
    
    /**
     * Create screen with input fields necessary for posting comments.
     * @return 
     */
    private Scene createPostComment(){
        VBox root = new VBox();
        Button postCommentButton = new Button("Post");
        postCommentButton.setPrefWidth(250);
        postCommentButton.setOnAction((ActionEvent) -> {
            String comment = postCommentTextFields.get("comment").getText();
            if(!comment.isEmpty()){
                system.post(system.getLoggedInUser().getId(), selectedCafe.getID(), comment);
                switchToHomeScreen();
            }
        });
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.getChildren().addAll(new Label("Post a comment"), postCommentTextFields.get("comment"), postCommentButton);
        return new Scene(root, 640,480);
    }
    
    /**
     * Create a screen with input controls necessary for adding a new cafe.
     * @return 
     */
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
        
        Button registerButton = new Button("Register cafe");
        registerButton.setPrefWidth(620);
        registerButton.setPrefHeight(50);
        
        registerButton.setOnAction((ActionEvent) -> {
            if(!addCafeTextFields.get("name").getText().isEmpty() && 
                    !addCafeTextFields.get("country").getText().isEmpty() &&
                    !addCafeTextFields.get("city").getText().isEmpty() &&
                    !addCafeTextFields.get("city").getText().isEmpty())
            {
                system.createCafe(addCafeTextFields.get("name").getText(),
                   addCafeTextFields.get("country").getText(),
                   addCafeTextFields.get("city").getText(),
                   addCafeTextFields.get("street").getText());
            }
        });
        root.getChildren().addAll(new Label("Add cafe"), nameHBox, new Label("Location"), locationHBox, 
                registerButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(5);
        return new Scene(root, 640,480);
    }
    
    /**
     * Create screen with controls necessary for changing an existing cafe's
     * information.
     * @return 
     */
    private Scene createChangeCafeDetail(){
        VBox root = new VBox();
        
        HBox nameHBox = new HBox();
        nameHBox.getChildren().addAll(new Label ("Name"), changeCafeDetailTextFields.get("name"));
        nameHBox.setSpacing(110);
        isActiveCheckBox.setOnAction((ActionEvent) -> {
            selectedCafe.toggleActive();
        });
        
        HBox locationHBox = new HBox();
        VBox labelsLocationVBox = new VBox();
        labelsLocationVBox.getChildren().addAll(new Label("Country"),new Label("City"), new Label("Street"));
        labelsLocationVBox.setSpacing(10);
        
        VBox textFieldsVBox = new VBox();
        textFieldsVBox.getChildren().addAll(changeCafeDetailTextFields.get("country"),changeCafeDetailTextFields.get("city"), 
                                        changeCafeDetailTextFields.get("street"));
        
        locationHBox.getChildren().addAll(labelsLocationVBox, textFieldsVBox);
        locationHBox.setSpacing(100);
        
        HBox offersHBox = new HBox();
        VBox addKindsVBox = new VBox();
        VBox addSpecialOfVBox = new VBox();
        Button addNewKindButton = new Button("Add new kind of coffee");
        Button addNewSpecialButton = new Button("Add new special offer");
          
        addNewKindButton.setOnAction((ActionEvent) -> {
            if(selectedCafe != null){
                Coffee newCoffee = readNewKindOfCoffee(changeCafeDetailTextFields.get("add new kind").getText(),selectedCafe.getID());
                if(newCoffee != null){
                    selectedCafe.addCoffee(newCoffee);
                    changeCafeDetailListViews.get("OFOK").getItems().add(newCoffee.toString());
                    changeCafeDetailTextFields.get("add new kind").clear();
                }
            }
        });
        addNewSpecialButton.setOnAction((ActionEvent) -> {
            if(selectedCafe != null){
                SpecialOffer newSpecialOffer = readNewSpecialOffer(changeCafeDetailTextFields.get("add new special").getText(),selectedCafe.getID());
                if(newSpecialOffer != null){
                    selectedCafe.addSpecialOffer(newSpecialOffer);
                    changeCafeDetailListViews.get("special offers").getItems().add(newSpecialOffer.toString());
                    changeCafeDetailTextFields.get("add new special").clear();
                }
            }
        });
            
        addKindsVBox.getChildren().addAll(new Label("Offered kinds of coffee"), changeCafeDetailListViews.get("OFOK"),
                new Label("Kind of coffee"), changeCafeDetailTextFields.get("add new kind"), addNewKindButton);
        
        addSpecialOfVBox.getChildren().addAll(new Label("Special offers"), changeCafeDetailListViews.get("special offers"),
                new Label("Special offer"), changeCafeDetailTextFields.get("add new special"), addNewSpecialButton);
       
        offersHBox.getChildren().addAll(addKindsVBox, addSpecialOfVBox);
        offersHBox.setSpacing(150);
        
        Button changeCafeDetailButton = new Button("Change cafe detail");
        changeCafeDetailButton.setPrefWidth(620);
        changeCafeDetailButton.setPrefHeight(50);
        
        changeCafeDetailButton.setOnAction((ActionEvent) -> {
            if(!changeCafeDetailTextFields.get("name").getText().isEmpty() &&
               !changeCafeDetailTextFields.get("country").getText().isEmpty() &&
               !changeCafeDetailTextFields.get("city").getText().isEmpty() &&
               !changeCafeDetailTextFields.get("street").getText().isEmpty()){
                system.changeCafeDetail(changeCafeDetailTextFields.get("name").getText(),
                                        changeCafeDetailTextFields.get("country").getText(),
                                        changeCafeDetailTextFields.get("city").getText(),
                                        changeCafeDetailTextFields.get("street").getText(),
                                        selectedCafe.isActive(), selectedCafe.getID());
                switchToHomeScreen();
            }
        });
        root.getChildren().addAll(new Label("Change cafe detail"), isActiveCheckBox,nameHBox, new Label("Location"), 
                locationHBox, offersHBox, 
                changeCafeDetailButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(5);
        return new Scene(root, 640,480);
    }
    
    /**
     * Handles input of a new kind of coffee.
     * @param userInput
     * @param cafe_id
     * @return 
     */
    private Coffee readNewKindOfCoffee(String userInput,int cafe_id){
        Pattern coffeeFormat = Pattern.compile("^(\\w+):\\s+(\\d+(?:\\.\\d+)?)$");
        Matcher matcher = coffeeFormat.matcher(userInput);
        if(matcher.matches() && matcher.groupCount() == 2)
            return system.createCoffee(cafe_id,matcher.group(1), Double.parseDouble(matcher.group(2)));
        else
            return null;
    }
    
    /**
     * Handles input of a new special offer.
     * @param userInput
     * @param cafe_id
     * @return 
     */
    private SpecialOffer readNewSpecialOffer(String userInput,int cafe_id){
        Pattern coffeeFormat = Pattern.compile("^(\\w+(?:\\s\\w+)*):\\s+(\\d+)\\.(\\d+)\\.(\\d+)-(\\d+)\\.(\\d+)\\.(\\d+)\\s+(\\w+(?:\\s\\w+)*)$");
        Matcher matcher = coffeeFormat.matcher(userInput);
        if(matcher.matches() && matcher.groupCount() == 8){
            Date startDate = new Date(Integer.parseInt(matcher.group(4))-BASE_YEAR, Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(2))),
                 endDate = new Date(Integer.parseInt(matcher.group(7))-BASE_YEAR, Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(5)));
            if(endDate.after(startDate))
                return system.createSpecialOffer(cafe_id,matcher.group(1),startDate,endDate,matcher.group(8));
        }
        return null;
    }
    
    /**
     * Create screen for searching for a user.
     * @return 
     */
    private Scene createFindUser(){
        VBox root = new VBox();
        Button findUserButton = new Button("Find user");
        findUserButton.setPrefWidth(620);
        findUserButton.setPrefHeight(40);
        
        findUserButton.setOnAction((ActionEvent) -> {
            selectedUser = system.searchForUser(findUserTextFields.get("e-mail").getText());
            editUserAdminTextFields.get("e-mail").setText(selectedUser.getEmail());
            editUserAdminTextFields.get("name").setText(selectedUser.getName());
            editUserAdminTextFields.get("surname").setText(selectedUser.getSurname());
            switchToAdminEdit();
        });
        
        HBox detailsHBox = new HBox();
        detailsHBox.setSpacing(100);
                
        VBox textFieldsVBox = new VBox();
        VBox labelsVBox = new VBox();
        labelsVBox.getChildren().addAll(new Label("E-mail address"), new Label("Name"),
                new Label("Surname"));
        labelsVBox.setSpacing(10);
        
        textFieldsVBox.getChildren().add(findUserTextFields.get("e-mail"));
        
        detailsHBox.getChildren().addAll(labelsVBox, textFieldsVBox);
        
        root.getChildren().addAll(new Label("Find user"), detailsHBox, findUserButton);
        root.setSpacing(5);
        return new Scene(root, 640, 200);
    }
    
    private void switchToAdminEdit()
    {
        stage.setScene(editUserAdmin);
    }
    
    /**
     * Create screen for editing a user's own profile.
     * @return 
     */
    private Scene createEditUser(){
        VBox root = new VBox();
        Button editUserButton = new Button("Change your profile information");
        editUserButton.setPrefWidth(620);
        editUserButton.setPrefHeight(40);
        
        editUserButton.setOnAction((ActionEvent) -> {
            if(!editUserTextFields.get("e-mail").getText().isEmpty() &&
               !editUserTextFields.get("name").getText().isEmpty() &&
               !editUserTextFields.get("surname").getText().isEmpty() &&
               !editUserTextFields.get("password").getText().isEmpty()
                    && system.getLoggedInUser() != null){
                system.changeUserDetail(editUserTextFields.get("name").getText(),
                        editUserTextFields.get("surname").getText(),
                        editUserTextFields.get("e-mail").getText(),
                        editUserTextFields.get("password").getText(),
                        system.getLoggedInUser().getId(),
                        false, false);
                switchToHomeScreen();
            }
        });

        HBox detailsHBox = new HBox();
        detailsHBox.setSpacing(100);
                
        VBox textFieldsVBox = new VBox();
        VBox labelsVBox = new VBox();
        labelsVBox.getChildren().addAll(new Label("E-mail"), new Label("Name"),
                new Label("Surname"), new Label("Password"));
        labelsVBox.setSpacing(10);
        
        textFieldsVBox.getChildren().addAll(editUserTextFields.get("e-mail"),editUserTextFields.get("name"),
                editUserTextFields.get("surname"), editUserTextFields.get("password"));
        
        detailsHBox.getChildren().addAll(labelsVBox, textFieldsVBox);
        
        root.getChildren().addAll(new Label("Change your profile information"), detailsHBox, editUserButton);
        root.setSpacing(5);
        return new Scene(root, 640, 200);
    }
    
    /**
     * Create screen for editing user profile by admin.
     * @return 
     */
    private Scene createEditUserAdmin(){
        VBox root = new VBox();
        Button editUserButton = new Button("Change profile information");
        editUserButton.setPrefWidth(620);
        editUserButton.setPrefHeight(40);
        
        CheckBox banUserCheckBox = new CheckBox("Ban user");
        
        
        editUserButton.setOnAction((ActionEvent) -> {
            system.changeUserDetail(editUserAdminTextFields.get("name").getText(),
                    editUserAdminTextFields.get("surname").getText(),
                    editUserAdminTextFields.get("e-mail").getText(), 
                    selectedUser.getPassword(),
                    selectedUser.getId(),
                    false,
                    banUserCheckBox.selectedProperty().get());
            switchToHomeScreen();
        });
        
        HBox detailsHBox = new HBox();
        detailsHBox.setSpacing(100);
                
        VBox textFieldsVBox = new VBox();
        VBox labelsVBox = new VBox();

        
        labelsVBox.getChildren().addAll(new Label("E-mail"), new Label("Name"),
                new Label("Surname"), banUserCheckBox);
        labelsVBox.setSpacing(10);
        
        textFieldsVBox.getChildren().addAll(editUserAdminTextFields.get("e-mail"),editUserAdminTextFields.get("name"),
                editUserAdminTextFields.get("surname"));
        
        detailsHBox.getChildren().addAll(labelsVBox, textFieldsVBox);
        
        root.getChildren().addAll(new Label("Change profile information"), detailsHBox, editUserButton);
        root.setSpacing(5);
        return new Scene(root, 640, 200);
    
    };
    
    
    /**
     * Validate an email address.
     * @param email
     * @return 
     */
    private boolean validEmail(String email){
        return email.contains("@");
    }

    /**
     * Application entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Support method for parsing doubles.
     * @return 
     */
    private double getDouble(String text){
        return !text.isEmpty() ? Double.parseDouble(text) : 0;
    }
}
