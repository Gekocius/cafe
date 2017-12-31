package cafe.logic;

import java.util.Collection;

public class User {
    
        private String email;
        
        private String name;
        
        private String surname;
        
        private String password;

	private int id;

	private Collection<Post> posts;

	private Collection<Rating> ratings;

	private InformationSystem system;
        
        public User(String email,String name,String surname,String password){
            this.email = email;
            this.name = name;
            this.surname = surname;
            this.password = password;
        }

	public void rateCafe() {
            throw new UnsupportedOperationException();
	}

	public void editRating() {
            throw new UnsupportedOperationException();
	}

	public void editPost() {
            throw new UnsupportedOperationException();
	}

	public void postAboutCafe() {
            throw new UnsupportedOperationException();
	}
}
