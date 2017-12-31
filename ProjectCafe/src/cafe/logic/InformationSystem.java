package cafe.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

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
    
    
    private User loggedInUser = null;
    
    private Collection<Cafe> cafes;

    public List<Cafe> retrieveCafes() {
            return null;
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
                    loggedInUser = new Admin(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
                else
                    loggedInUser = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
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
