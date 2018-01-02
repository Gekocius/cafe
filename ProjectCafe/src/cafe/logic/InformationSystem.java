package cafe.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
    private static final String SQL_LOGIN = "select email,user_name,user_surname,user_password,admin\n"
            + "from " + DB + ".users\n"
            + "where email like ? and user_password like ?;";
    private static final String SQL_SEARCH = "select *\n" +
            "from " + DB + ".offered_coffee\n" +
            "\tfull join " + DB + ".cafe using(cafe_id)\n" +
            "\tleft join " + DB + ".coffee using(coffee_id)\n" +
            "\tleft join " + DB + ".offered_so using(cafe_id)\n" +
            "\tleft join " + DB + ".special_offer using(offer_id)\n"
            + "\tleft join  (select cafe_id, avg(stars) STARS\n" +
              "\t\t\tfrom " + DB + ".rating\n" +
              "\t\t\tgroup by cafe_id) AVERAGE_RATINGS using(cafe_id)"
            + "where cafe_name like ?\n"
            + "\tand country like ?\n"
            + "\tand city like ?\n"
            + "\tand street like ?\n"
            + "\tand active = ?\n"
            + "\tand (coffee_name like ? or coffee_name is null)\n"
            + "\tand (offer_name like ? or offer_name is null)\n"
            + "\tand (STARS >= ? or STARS is null);";
    
    
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
    
    public boolean login(String email,String password){
        Connection connection;
        PreparedStatement statement = null;
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
                if(rs.getBoolean(5))
                    loggedInUser = new Admin(rs.getString("email"),rs.getString("user_name"),rs.getString("user_surname"),rs.getString("user_password"));
                else
                    loggedInUser = new User(rs.getString("email"),rs.getString("user_name"),rs.getString("user_surname"),rs.getString("user_password"));
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
                                   boolean active,String coffee_name,String offer_name,double rating){
        Connection connection;
        PreparedStatement statement = null;
        ResultSet rs = null;
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
            statement.setDouble(i++, rating);
            rs = statement.executeQuery();
            if(rs == null)
                return retrieveCafes();
            else{
                boolean hasNext = rs.next();
                while(hasNext){
                    final int cafeID = rs.getInt("cafe_id");
                    Cafe nextCafe = new Cafe(cafeID,rs.getString("cafe_name"),
                              rs.getString("country"),rs.getString("city"),
                              rs.getString("street"),rs.getBoolean("active"));
                    do{
                        final int offer_id = rs.getInt("offer_id");
                        if(!rs.wasNull())
                            nextCafe.editSpecialOffer(new SpecialOffer(offer_id,
                                    rs.getDate("start_date"),rs.getDate("end_date"),
                                    rs.getString("offer_name"),rs.getString("description")));
                        final int coffee_id = rs.getInt("coffee_id");
                        if(!rs.wasNull())
                            nextCafe.editCoffee(new Coffee(coffee_id,rs.getDouble("price"),
                                    rs.getString("coffee_name")));
                        hasNext = rs.next();
                    }while(hasNext && rs.getInt("cafe_id") == cafeID);
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
