package cafe.logic;

import java.util.Collection;

public class Admin extends User {

	private Collection<Cafe> cafes;
        
        public Admin(int id,String email,String name,String surname,String password){
            super(id,email,name,surname,password);
        }

	public void addCafe(Cafe cafe) {
            throw new UnsupportedOperationException();
	}

	public void deactivateCafe(Cafe cafe) {
            throw new UnsupportedOperationException();
	}

	public void ban(User user) {
            throw new UnsupportedOperationException();
	}

}
