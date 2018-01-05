package cafe.logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InformationSystem {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/?useSSL=false";
    private static final String DB_USER = "4IT115";
    private static final String DB_PASSWORD = "4IT115-project";
    
    private static final String DB = "4it115";
    private static final String SQL_CREATE_USER = "insert into " + DB + ".users\n"
            + "(USER_NAME,USER_SURNAME,EMAIL,USER_PASSWORD,ADMIN)\n"
            + "values (?,?,?,?,?);";
    private static final String SQL_CHECK_ACCOUNT = "select email\n"
            + "from " + DB + ".users\n"
            + "where email like ?;";
    private static final String SQL_CHECK_COFFEE = "select *\n"
            + "from " + DB + ".coffee\n"
            + "where coffee_name like ?\n"
            + "and price = ?;";
    private static final String SQL_CHECK_SPECIAL_OFFER = "select *\n"
            + "from " + DB + ".special_offer\n"
            + "where offer_name like ?\n"
            + "and start_date = ?\n"
            + "and end_date = ?\n"
            + "and description like ?;";
    private static final String SQL_LOGIN = "select user_id,email,user_name,user_surname,user_password,admin\n"
            + "from " + DB + ".users\n"
            + "where email like ? and user_password like ?;";
    private static final String SQL_SEARCH = "select *\n" +
            "from " + DB + ".cafe\n" +
            "left join " + DB + ".admins using(admin_id)\n" +
            "left join " + DB + ".offered_coffee using(cafe_id)\n" +
            "left join " + DB + ".offered_so using(cafe_id)\n" +
            "left join " + DB + ".rating using(cafe_id)\n" +
            "left join " + DB + ".users using(user_id)\n" +
            "left join " + DB + ".coffee using(coffee_id)\n" +
            "left join " + DB + ".special_offer using(offer_id)\n" +
            "where cafe_name like ?\n" +
            "and country like ?\n" +
            "and city like ?\n" +
            "and street like ?\n" +
            "and active = ?\n" +
            "and (coffee_name like ? or coffee_name is null)\n" +
            "and (offer_name like ? or offer_name is null)\n" +
            "order by cafe_id;";
    private static final String SQL_ADD_COFFEE = "insert into " + DB + ".coffee\n" +
            "(COFFEE_NAME,PRICE)\n" +
            "values (?,?);";
    private static final String SQL_ADD_SPECIAL_OFFER = "insert into " + DB + ".special_offer\n" +
            "(OFFER_NAME,START_DATE,END_DATE,DESCRIPTION)\n" +
            "values (?,?,?,?);";
    private static final String SQL_ADD_COFFEE_TO_CAFE = "insert into " + DB + ".offered_coffee\n" +
            "(cafe_id,coffee_id)\n" +
            "values (?,?);";
    private static final String SQL_ADD_SPECIAL_OFFER_TO_CAFE = "insert into " + DB + ".offered_so\n" +
            "(cafe_id,offer_id)\n" +
            "values (?,?);";
    
    
    private User loggedInUser = null;
    
    private final Collection<Cafe> cafes = new ArrayList<>();

    public Collection<Cafe> retrieveCafes() {
            return cafes;
    }

    public boolean createUser(String email,String name,String surname,String password){
        Connection connection;
        PreparedStatement statement = null;
        boolean result = false;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            if(!accountExists(statement, connection, email)){
                statement = connection.prepareStatement(SQL_CREATE_USER);
                int i = 1;
                statement.setString(i++, name);
                statement.setString(i++, surname);
                statement.setString(i++, email);
                statement.setString(i++, password);
                statement.setBoolean(i++, false);
                int updatedRecords = statement.executeUpdate();
                if(updatedRecords == 1)
                    result = true;
            }
            statement.close();
            connection.close();
        }
        catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        finally{
            return result;
        }
    }
    
    private boolean accountExists(PreparedStatement statement, 
                                            Connection connection,
                                            String email) throws SQLException{
            statement = connection.prepareStatement(SQL_CHECK_ACCOUNT);
            statement.setString(1, email);
            return statement.executeQuery().next();
    }

    public Coffee createCoffee(int cafe_id,String coffee_name,double price){
        Connection connection;
        PreparedStatement statement = null;
        Coffee newCoffee = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            newCoffee = getCoffee(statement, connection, coffee_name, price);
            if(newCoffee == null){
                statement = connection.prepareStatement(SQL_ADD_COFFEE);
                int i = 1;
                statement.setString(i++, coffee_name);
                statement.setDouble(i++, price);
                int updatedRecords = statement.executeUpdate();
                if(updatedRecords == 1){
                    newCoffee = getCoffee(statement, connection, coffee_name, price);
                }
            }
            if(newCoffee != null){
                statement = connection.prepareStatement(SQL_ADD_COFFEE_TO_CAFE);
                int i = 1;
                statement.setInt(i++, cafe_id);
                statement.setInt(i++, newCoffee.getID());
                statement.executeUpdate();
            }
            statement.close();
            connection.close();
        }
        catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        finally{
            return newCoffee;
        }
    }

    public SpecialOffer createSpecialOffer(int cafe_id,String offer_name,Date startDate,Date endDate,String description){
        Connection connection;
        PreparedStatement statement = null;
        SpecialOffer newOffer = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            newOffer = getSpecialOffer(statement, connection, offer_name, startDate,endDate,description);
            if(newOffer == null){
                statement = connection.prepareStatement(SQL_ADD_SPECIAL_OFFER);
                int i = 1;
                statement.setString(i++, offer_name);
                statement.setDate(i++, startDate);
                statement.setDate(i++, endDate);
                statement.setString(i++, description);
                int updatedRecords = statement.executeUpdate();
                if(updatedRecords == 1){
                    newOffer = getSpecialOffer(statement, connection, offer_name, startDate,endDate,description);
                }
            }
            if(newOffer != null){
                statement = connection.prepareStatement(SQL_ADD_SPECIAL_OFFER_TO_CAFE);
                int i = 1;
                statement.setInt(i++, cafe_id);
                statement.setInt(i++, newOffer.getID());
                statement.executeUpdate();
            }
            statement.close();
            connection.close();
        }
        catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        finally{
            return newOffer;
        }
    }
    
    private Coffee getCoffee(PreparedStatement statement, 
                             Connection connection,
                             String coffee_name, double price) throws SQLException{
            statement = connection.prepareStatement(SQL_CHECK_COFFEE);
            statement.setString(1, coffee_name);
            statement.setDouble(2, price);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                return new Coffee(rs.getInt("coffee_id"), price, coffee_name);
            else
                return null;
    }
    
    private SpecialOffer getSpecialOffer(PreparedStatement statement, 
                             Connection connection,
                             String offer_name, Date startDate,Date endDate,String description) throws SQLException{
            statement = connection.prepareStatement(SQL_CHECK_SPECIAL_OFFER);
                int i = 1;
                statement.setString(i++, offer_name);
                statement.setDate(i++, startDate);
                statement.setDate(i++, endDate);
                statement.setString(i++, description);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                return new SpecialOffer(rs.getInt("offer_id"),startDate,endDate,offer_name,description);
            else
                return null;
    }
    
    public boolean login(String email,String password){
        Connection connection;
        PreparedStatement statement;
        boolean result = false;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(SQL_LOGIN);
            int i = 1;
            statement.setString(i++, email);
            statement.setString(i++, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                if(rs.getBoolean("admin"))
                    loggedInUser = new Admin(rs.getInt("user_id"),rs.getString("email"),rs.getString("user_name"),rs.getString("user_surname"),rs.getString("user_password"));
                else
                    loggedInUser = new User(rs.getInt("user_id"),rs.getString("email"),rs.getString("user_name"),rs.getString("user_surname"),rs.getString("user_password"));
                result = true;
            }
            statement.close();
            connection.close();
        }
        catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        finally{
            return result;
        }
    }
    
    public Collection<Cafe> search(String cafe_name,String country,String city,String street,
                                   boolean active,String coffee_name,String offer_name,double minimalRating){
        cafes.removeAll(cafes);
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(SQL_SEARCH);
            int i = 1;
            statement.setString(i++, "%" + cafe_name   + "%");
            statement.setString(i++, "%" + country     + "%");
            statement.setString(i++, "%" + city        + "%");
            statement.setString(i++, "%" + street      + "%");
            statement.setBoolean(i++, active);
            statement.setString(i++, "%" + coffee_name + "%");
            statement.setString(i++, "%" + offer_name  + "%");
            rs = statement.executeQuery();
            if(rs == null)
                return retrieveCafes();
            else{
                boolean hasNext = rs.next();
                Set<Integer> offerIDs = new HashSet<>(), coffeeIDs = new HashSet<>(), ratingIDs = new HashSet<>();
                while(hasNext){
                    final int cafeID = rs.getInt("cafe_id");
                    Cafe nextCafe = new Cafe(cafeID,rs.getString("cafe_name"),
                              rs.getString("country"),rs.getString("city"),
                              rs.getString("street"),rs.getBoolean("active"),
                              new Admin(rs.getInt("admin_id"),rs.getString("admin_email"), rs.getString("admin_name"),
                                        rs.getString("admin_surname"), rs.getString("admin_password")));
                    do{
                        final int offer_id = rs.getInt("offer_id");
                        if(!rs.wasNull() && !offerIDs.contains(offer_id)){
                            nextCafe.addSpecialOffer(new SpecialOffer(offer_id,
                                    rs.getDate("start_date"),rs.getDate("end_date"),
                                    rs.getString("offer_name"),rs.getString("description")));
                            offerIDs.add(offer_id);
                        }
                        final int coffee_id = rs.getInt("coffee_id");
                        if(!rs.wasNull() && !coffeeIDs.contains(coffee_id)){
                            nextCafe.addCoffee(new Coffee(coffee_id,rs.getDouble("price"),
                                    rs.getString("coffee_name")));
                            coffeeIDs.add(coffee_id);
                        }
                        final int rating_id = rs.getInt("rating_id");
                        if(!rs.wasNull() && !ratingIDs.contains(rating_id)){
                            nextCafe.addRating(new Rating(rating_id,rs.getDouble("stars"),
                                               new User(rs.getInt("user_id"),rs.getString("email"), rs.getString("user_name"), 
                                                        rs.getString("user_surname"), rs.getString("user_password")),
                                               nextCafe));
                            ratingIDs.add(rating_id);
                        }
                        hasNext = rs.next();
                    }while(hasNext && rs.getInt("cafe_id") == cafeID);
                    if(!nextCafe.hasRatings() || nextCafe.getRating() >= minimalRating)
                        cafes.add(nextCafe);
                }
            }
            statement.close();
            connection.close();
        }
        catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        finally{
            return retrieveCafes();
        }
    }
    
    public boolean loggedIn(){
        return loggedInUser != null;
    }
    
    public boolean loggedInAsAdmin(){
        return loggedInUser != null && loggedInUser instanceof Admin;
    }
    
    public void logout(){
        loggedInUser = null;
    }
}
