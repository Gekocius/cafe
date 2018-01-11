package cafe.logic;

import java.util.Collection;

/**
 * Representation of a user
 * @author David Fuchs
 */
public class User {
    
    private final int id;

    private String email;

    private String name;

    private String surname;

    private String password;
    
    private boolean banned;

    private Collection<Post> posts;

    private Collection<Rating> ratings;

    private InformationSystem system;

    /**
     * Creates a new user from information in the database. ID is automatically 
     * generated by the database.
     * 
     * @param id
     * @param email
     * @param name
     * @param surname
     * @param password 
     */
    public User(int id,String email,String name,String surname,String password,boolean banned){
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.banned = banned;
    }

    /**
     * Return user's ID.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Return user's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns user's surname.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * Indicates if a user is banned.
     * @return 
     */
    public boolean isBanned(){
        return banned;
    }
    
    /**
     * Either bans user or removes ban from user.
     */
    public void toggleBan(){
        banned = !banned;
    }

    /**
     * Returns user's email.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user's email.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets user's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets user's surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets user's password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
