package cafe.logic;

public class Rating {

	private final int id;

	private double stars;

	private User user;

	private Cafe cafe;
        
        public Rating(int id,double stars,User user,Cafe cafe){
            this.id = id;
            this.stars = stars;
            this.user = user;
            this.cafe = cafe;
        }

        public double getStars(){
            return stars;
        }
}
