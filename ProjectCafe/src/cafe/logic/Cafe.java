package cafe.logic;

import java.util.ArrayList;
import java.util.List;

public class Cafe {

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

	private int id;
	private String name;
        private String country;
        private String city;
        private String street;
	private List<Rating> ratings = new ArrayList<>();
	private List<Coffee> coffees = new ArrayList<>();
	private List<SpecialOffer> specialOffers = new ArrayList<>();
	private List<Post> posts = new ArrayList<>();
	private boolean active;
	private Admin admin;
        
        public Cafe(int id,String name,String country,String city,String street,
                    boolean active,Admin admin){
            this.name = name;
            this.country = country;
            this.city = city;
            this.street = street;
            this.active = active;
            this.admin = admin;
        }

	public void editCoffee(Coffee coffee) {
            getCoffees().add(coffee);
	}

	public void editSpecialOffer(SpecialOffer offer) {
            getSpecialOffers().add(offer);
	}
        
        public void addPost(Post post){
            posts.add(post);
        }
        
        public void addRating(Rating rating){
            ratings.add(rating);
        }

        public String getName(){
            return name;
        }
        
        public double getRating(){
            double sum = 0;
            for (Rating rating : ratings)
                sum += rating.getStars();
            return sum / ratings.size();
        }

    /**
     * @return the coffees
     */
    public List<Coffee> getCoffees() {
        return coffees;
    }

    /**
     * @return the specialOffers
     */
    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }
}
